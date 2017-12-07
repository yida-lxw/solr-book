package com.yida.solr.book.examples.ch16.authentication.solrj;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

/**
 * Created by Lanxiaowei
 * 当你开启了Solr的安全认证时，你需要使用此类来构建SolrClient对象
 */
public class InsecureHttpClient implements HttpClient {

    private HttpClient httpClient;

    /**
     * This class allows you to access the Solr servers that are running under
     * SSL certificates and using BASIC authentication. However, the SSL
     * certificates are self signed, and therefore will generate a
     * "peer cannot be verified" error. This works around it. Additionally, to
     * make BASIC auth all work on a single request you need a Preemptive
     * Authenticator.
     * <p>
     * The equivalent in curl is curl --user admin:password --insecure "https://localhost:443/solr/update/?commit=true"
     *
     * @param httpClient The client all calls are delegated to
     * @param username
     * @param password
     */
    public InsecureHttpClient(HttpClient httpClient, String username, String password) throws Exception {

        super();

        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {

            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
            }

            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        X509HostnameVerifier verifier = new X509HostnameVerifier() {

            public void verify(String string, SSLSocket ssls) throws IOException {
            }

            public void verify(String string, X509Certificate xc) throws SSLException {
            }

            public void verify(String string, String[] strings, String[] strings1) throws SSLException {
            }

            public boolean verify(String string, SSLSession ssls) {
                return true;
            }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory ssf = new SSLSocketFactory(ctx);
        ssf.setHostnameVerifier(verifier);
        ClientConnectionManager ccm = httpClient.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", ssf, 443));
        DefaultHttpClient wrappedClient = new DefaultHttpClient(ccm, httpClient.getParams());

        if (username != null && username.length() > 0) {
            wrappedClient.addRequestInterceptor(new PreEmptiveBasicAuthenticator(username, password));
        }

        this.httpClient = wrappedClient;

    }

    public HttpResponse execute(HttpHost arg0, HttpRequest arg1, HttpContext arg2) throws IOException, ClientProtocolException {
        return httpClient.execute(arg0, arg1, arg2);
    }

    public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2, HttpContext arg3) throws IOException, ClientProtocolException {
        return httpClient.execute(arg0, arg1, arg2, arg3);
    }

    public <T> T execute(HttpHost arg0, HttpRequest arg1, ResponseHandler<? extends T> arg2) throws IOException, ClientProtocolException {
        return httpClient.execute(arg0, arg1, arg2);
    }

    public HttpResponse execute(HttpHost arg0, HttpRequest arg1) throws IOException, ClientProtocolException {
        return httpClient.execute(arg0, arg1);
    }

    public HttpResponse execute(HttpUriRequest arg0, HttpContext arg1) throws IOException, ClientProtocolException {
        return httpClient.execute(arg0, arg1);
    }

    public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1, HttpContext arg2) throws IOException, ClientProtocolException {
        return httpClient.execute(arg0, arg1, arg2);
    }

    public <T> T execute(HttpUriRequest arg0, ResponseHandler<? extends T> arg1) throws IOException, ClientProtocolException {
        return httpClient.execute(arg0, arg1);
    }

    public HttpResponse execute(HttpUriRequest arg0) throws IOException, ClientProtocolException {
        return httpClient.execute(arg0);
    }

    public ClientConnectionManager getConnectionManager() {
        return httpClient.getConnectionManager();
    }

    public HttpParams getParams() {
        return httpClient.getParams();
    }

    public class PreEmptiveBasicAuthenticator implements HttpRequestInterceptor {
        private final UsernamePasswordCredentials credentials;

        public PreEmptiveBasicAuthenticator(String user, String pass) {
            credentials = new UsernamePasswordCredentials(user, pass);
        }

        public void process(HttpRequest request, HttpContext context)
                throws HttpException, IOException {
            request.addHeader(BasicScheme.authenticate(credentials, "US-ASCII", false));
        }
    }
}