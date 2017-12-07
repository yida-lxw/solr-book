package com.yida.solr.book.examples.ch12.customsort.component;

import com.yida.solr.book.examples.ch12.customsort.listener.RankUpdateListener;
import com.yida.solr.book.examples.utils.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexableField;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.handler.component.ResponseBuilder;
import org.apache.solr.handler.component.SearchComponent;
import org.apache.solr.response.ResultContext;
import org.apache.solr.schema.IndexSchema;
import org.apache.solr.schema.SchemaField;
import org.apache.solr.search.DocIterator;
import org.apache.solr.search.DocList;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lanxiaowei
 */
public class RankExtractComponent extends SearchComponent {
    public void prepare(ResponseBuilder responseBuilder) throws IOException {

    }

    public void process(ResponseBuilder responseBuilder) throws IOException {
        Set<String> returnFields = getReturnFields(responseBuilder);
        if (returnFields.contains("rank")) {
            DocList slice = ((ResultContext) responseBuilder.rsp.getValues()
                    .get("response")).docs;
            DirectoryReader reader = responseBuilder.req.getSearcher().getIndexReader();
            SolrDocumentList rl = new SolrDocumentList();
            for (DocIterator it = slice.iterator(); it.hasNext(); ) {
                int docId = it.nextDoc();
                Document doc = reader.document(docId);
                SolrDocument sdoc = new SolrDocument();
                List<IndexableField> fields = doc.getFields();
                for (IndexableField field : fields) {
                    String fn = field.name();
                    if (returnFields.contains(fn)) {
                        sdoc.addField(fn, doc.get(fn));
                    }
                }
                if (returnFields.contains("score")) {
                    sdoc.addField("score", it.score());
                }
                if (returnFields.contains("rank")) {
                    List<Integer> thumbs = RankUpdateListener.getThumbs(docId);
                    sdoc.addField("thumbs_up", thumbs.get(0));
                    sdoc.addField("thumbs_down", thumbs.get(1));
                    System.out.println("-->docId:" + docId);
                    sdoc.addField("rank", RankUpdateListener.getRank(docId));
                }
                rl.add(sdoc);
            }
            rl.setMaxScore(slice.maxScore());
            rl.setNumFound(slice.matches());
            responseBuilder.rsp.getValues().remove("response");
            responseBuilder.rsp.add("response", rl);
        }
    }

    private Set<String> getReturnFields(ResponseBuilder responseBuilder) {
        Set<String> fields = new HashSet<String>();
        String flp = responseBuilder.req.getParams().get(CommonParams.FL);
        if (StringUtils.isEmpty(flp)) {
            return fields;
        }
        String[] fls = flp.split(",");
        IndexSchema schema = responseBuilder.req.getSchema();
        for (String fl : fls) {
            if ("*".equals(fl)) {
                Map<String,SchemaField> fm = schema.getFields();
                for (String fieldname : fm.keySet()) {
                    SchemaField sf = fm.get(fieldname);
                    if (sf.stored() && (! "content".equals(fieldname))) {
                        fields.add(fieldname);
                    }
                }
            } else if ("id".equals(fl)) {
                SchemaField usf = schema.getUniqueKeyField();
                fields.add(usf.getName());
            } else {
                fields.add(fl);
            }
        }
        return fields;
    }

    public String getDescription() {
        return "Rank Extraction Component";
    }
}
