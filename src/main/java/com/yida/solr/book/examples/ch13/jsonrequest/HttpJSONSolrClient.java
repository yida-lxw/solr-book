package com.yida.solr.book.examples.ch13.jsonrequest;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.solr.client.solrj.ResponseParser;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryResponseParser;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.RequestWriter;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.ContentStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by Lanxiaowei
 * 扩展自HttpSolrClient,使其支持最新的JSON Request API
 */
public class HttpJSONSolrClient extends HttpSolrClient {
    /**是否支持JSON Request API*/
    private boolean jsonRequest;
    /**用于接收JSON Request请求参数*/
    private String jsonParams;
    /**用于设置JSON Request请求参数的默认字符编码*/
    private String jsonParamsEncoding;
    private static final String DEFAULT_PATH = "/select";
    private volatile boolean useMultiPartPost;
    private volatile Set<String> queryParams = Collections.emptySet();
    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private static final String DEFAULT_MIME_TYPE = "application/json";

    public HttpJSONSolrClient(String baseURL) {
        super(baseURL);
        this.jsonRequest = true;
        this.jsonParamsEncoding = UTF_8;
        this.parser = new JSONResponseParser();
    }

    public HttpJSONSolrClient(String baseURL,boolean jsonRequest) {
        super(baseURL);
        this.jsonRequest = jsonRequest;
        this.jsonParamsEncoding = UTF_8;
        this.parser = new JSONResponseParser();
    }

    public HttpJSONSolrClient(String baseURL,boolean jsonRequest,String jsonParamsEncoding) {
        super(baseURL);
        this.jsonRequest = jsonRequest;
        this.jsonParamsEncoding = jsonParamsEncoding;
        this.parser = new JSONResponseParser();
    }

    @Override
    protected HttpRequestBase createMethod(SolrRequest request, String collection) throws IOException, SolrServerException {
        SolrParams params = request.getParams();
        Collection<ContentStream> streams = requestWriter.getContentStreams(request);
        String path = requestWriter.getPath(request);
        if (path == null || !path.startsWith("/")) {
            path = DEFAULT_PATH;
        }

        ResponseParser parser = request.getResponseParser();
        if (parser == null) {
            parser = this.parser;
        }

        // The parser 'wt=' and 'version=' params are used instead of the original
        // params
        ModifiableSolrParams wparams = new ModifiableSolrParams(params);
        if (parser != null) {
            wparams.set(CommonParams.WT, parser.getWriterType());
            wparams.set(CommonParams.VERSION, parser.getVersion());
        }
        if (invariantParams != null) {
            wparams.add(invariantParams);
        }

        String basePath = baseUrl;
        if (collection != null) {
            basePath += "/" + collection;
        }

        if (SolrRequest.METHOD.GET == request.getMethod()) {
            if (streams != null) {
                throw new SolrException(SolrException.ErrorCode.BAD_REQUEST, "GET can't send streams!");
            }
            return new HttpGet(basePath + path + ClientUtils.toQueryString(wparams, false));
        }

        if (SolrRequest.METHOD.POST == request.getMethod() || SolrRequest.METHOD.PUT == request.getMethod()) {
            String url = basePath + path;
            boolean hasNullStreamName = false;
            if (streams != null) {
                for (ContentStream cs : streams) {
                    if (cs.getName() == null) {
                        hasNullStreamName = true;
                        break;
                    }
                }
            }
            boolean isMultipart = ((this.useMultiPartPost && SolrRequest.METHOD.POST == request.getMethod())
                    || (streams != null && streams.size() > 1)) && !hasNullStreamName;

            LinkedList<NameValuePair> postOrPutParams = new LinkedList<NameValuePair>();
            if (streams == null || isMultipart) {
                // send server list and request list as query string params
                ModifiableSolrParams queryParams = calculateQueryParams(this.queryParams, wparams);
                queryParams.add(calculateQueryParams(request.getQueryParams(), wparams));
                String fullQueryUrl = url + ClientUtils.toQueryString(queryParams, false);
                HttpEntityEnclosingRequestBase postOrPut = SolrRequest.METHOD.POST == request.getMethod() ?
                        new HttpPost(fullQueryUrl) : new HttpPut(fullQueryUrl);
                if (!isMultipart) {
                    if(jsonRequest) {
                        postOrPut.addHeader("Content-Type",
                                "application/json; charset=UTF-8");
                    } else {
                        postOrPut.addHeader("Content-Type",
                                "application/x-www-form-urlencoded; charset=UTF-8");
                    }
                }

                List<FormBodyPart> parts = new LinkedList<FormBodyPart>();
                Iterator<String> iter = wparams.getParameterNamesIterator();
                while (iter.hasNext()) {
                    String p = iter.next();
                    String[] vals = wparams.getParams(p);
                    if (vals != null) {
                        for (String v : vals) {
                            if (isMultipart) {
                                parts.add(new FormBodyPart(p, new StringBody(v, StandardCharsets.UTF_8)));
                            } else {
                                postOrPutParams.add(new BasicNameValuePair(p, v));
                            }
                        }
                    }
                }

                if (isMultipart && streams != null) {
                    for (ContentStream content : streams) {
                        String contentType = content.getContentType();
                        if (contentType == null) {
                            contentType = BinaryResponseParser.BINARY_CONTENT_TYPE; // default
                        }
                        String name = content.getName();
                        if (name == null) {
                            name = "";
                        }
                        parts.add(new FormBodyPart(name,
                                new InputStreamBody(
                                        content.getStream(),
                                        contentType,
                                        content.getName())));
                    }
                }

                if (parts.size() > 0) {
                    MultipartEntity entity = new MultipartEntity(HttpMultipartMode.STRICT);
                    for (FormBodyPart p : parts) {
                        entity.addPart(p);
                    }
                    postOrPut.setEntity(entity);
                } else {
                    if(jsonRequest && jsonParamsNotEmpty()) {
                        postOrPut.setEntity(new StringEntity(getJsonParams(),DEFAULT_MIME_TYPE,getJsonParamsEncoding()));
                    } else {
                        postOrPut.setEntity(new UrlEncodedFormEntity(postOrPutParams, StandardCharsets.UTF_8));
                    }
                }

                return postOrPut;
            }
            // It is has one stream, it is the post body, put the params in the URL
            else {
                String pstr = ClientUtils.toQueryString(wparams, false);
                HttpEntityEnclosingRequestBase postOrPut = SolrRequest.METHOD.POST == request.getMethod() ?
                        new HttpPost(url + pstr) : new HttpPut(url + pstr);

                // Single stream as body
                // Using a loop just to get the first one
                final ContentStream[] contentStream = new ContentStream[1];
                for (ContentStream content : streams) {
                    contentStream[0] = content;
                    break;
                }
                if (contentStream[0] instanceof RequestWriter.LazyContentStream) {
                    postOrPut.setEntity(new InputStreamEntity(contentStream[0].getStream(), -1) {
                        @Override
                        public Header getContentType() {
                            return new BasicHeader("Content-Type", contentStream[0].getContentType());
                        }

                        @Override
                        public boolean isRepeatable() {
                            return false;
                        }

                    });
                } else {
                    postOrPut.setEntity(new InputStreamEntity(contentStream[0].getStream(), -1) {
                        @Override
                        public Header getContentType() {
                            return new BasicHeader("Content-Type", contentStream[0].getContentType());
                        }

                        @Override
                        public boolean isRepeatable() {
                            return false;
                        }
                    });
                }
                return postOrPut;
            }
        }
        throw new SolrServerException("Unsupported method: " + request.getMethod());
    }

    public String getJsonParams() {
        return jsonParams;
    }

    public void setJsonParams(String jsonParams) {
        this.jsonParams = jsonParams;
    }

    public boolean isJsonRequest() {
        return jsonRequest;
    }

    public void setJsonRequest(boolean jsonRequest) {
        this.jsonRequest = jsonRequest;
    }

    public String getJsonParamsEncoding() {
        if(null == this.jsonParamsEncoding || "".equals(this.jsonParamsEncoding)) {
            this.jsonParamsEncoding = UTF_8;
        }
        return this.jsonParamsEncoding;
    }

    public void setJsonParamsEncoding(String jsonParamsEncoding) {
        this.jsonParamsEncoding = jsonParamsEncoding;
    }

    private boolean jsonParamsNotEmpty() {
        return null != this.jsonParams && !"".equals(this.jsonParams);
    }
}
