package com.yida.solr.book.examples.ch13.delta;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Lanxiaowei
 * Solr数据导入工具类
 */
public class SolrDIHClient {
    private static final Logger log = Logger.getLogger(SolrDIHClient.class);
    private static final String ID_FILED_NAME = "id";
    private HttpSolrClient solrClient;

    private static final int BATCH_SIZE = 500;

    private String defaultCore;

    private static Lock lock = new ReentrantLock();

    public SolrDIHClient(String solrURL, String defaultCore) {
        if (null == solrURL || "".equals(solrURL)) {
            throw new RuntimeException("solrURL MUST be specified.");
        }
        this.solrClient = new HttpSolrClient(solrURL);
        this.defaultCore = defaultCore;
    }

    /**
     * 添加索引文档至默认Core
     *
     * @param doc
     */
    public void addDoc(SolrInputDocument doc) {
        if (!isBlank(this.defaultCore)) {
            this.addDoc(this.defaultCore, doc);
        } else {
            log.error("default defaultCore should not be null");
        }
    }

    /**
     * 添加索引文档至指定Core
     *
     * @param core
     * @param doc
     */
    public void addDoc(String core, SolrInputDocument doc) {
        try {
            solrClient.add(core, doc);
        } catch (Exception e) {
            log.error("add Doc occurs an error,core:" + core + ",doc_id:" + doc.getFieldValue(ID_FILED_NAME), e);
        }
    }

    /**
     * 批量添加索引文档到默认Core
     *
     * @param docs
     */
    public void addDocs(List<SolrInputDocument> docs) {
        if (!isBlank(this.defaultCore)) {
            this.addDocs(defaultCore, docs);
        } else {
            log.error("default core should not be null");
        }
    }

    /**
     * 批量添加索引文档至指定Core
     *
     * @param core
     * @param docs
     */
    public void addDocs(String core, List<SolrInputDocument> docs) {
        if (docs != null && docs.size() > 0) {
            int size = docs.size();
            if (size <= BATCH_SIZE) {
                try {
                    solrClient.add(core, docs);
                } catch (Exception e) {
                    log.error("add Docs occurs an error,core:" + core, e);
                }
            } else {
                int end = size > BATCH_SIZE ? BATCH_SIZE : size;
                int start = 0;
                while (true) {
                    List<SolrInputDocument> subList = docs.subList(start, end);
                    try {
                        solrClient.add(core, subList);
                    } catch (Exception e) {
                        log.error("add Docs occurs an error,core:" + core, e);
                    }
                    if (end == size) {
                        break;
                    }
                    start = start + BATCH_SIZE;
                    end = (end + BATCH_SIZE);
                    if (end > size) {
                        end = size;
                    }
                }
            }
        }
    }

    public void deleteDocByIds(List<String> ids) {
        if (!isBlank(this.defaultCore)) {
            this.deleteDocByIds(defaultCore, ids);
        }
    }

    public void deleteDocByIds(String core, List<String> ids) {
        try {
            solrClient.deleteById(core, ids);
        } catch (Exception e) {
            log.error("delete Docs occurs an error,core:" + core, e);
        }
    }

    public void deleteDocById(String core, String id) {
        try {
            solrClient.deleteById(core, id);
        } catch (Exception e) {
            log.error("delete Doc occurs an error,core:" + core + ",doc_id:" + id, e);
        }
    }

    public void deleteDocById(String id) {
        if (!isBlank(this.defaultCore)) {
            this.deleteDocById(defaultCore, id);
        } else {
            log.error("default core should not be null");
        }
    }

    public void addBean(String core, Object bean) {
        try {
            solrClient.addBean(core, bean);
        } catch (Exception e) {
            String id = null;
            try {
                Field idFiled = bean.getClass().getDeclaredField(ID_FILED_NAME);
                if (idFiled != null) {
                    idFiled.setAccessible(true);
                    Object idFiledValue = idFiled.get(bean);
                    id = idFiledValue != null ? idFiledValue.toString() : "";
                }
            } catch (Exception e1) {
                log.error("get id field occurs an error", e1);
            }
            log.error("add bean occurs an error,core:" + core + ",bean_id:" + id, e);
        }
    }

    public void addBean(Object bean) throws SolrServerException, IOException {
        if (!isBlank(this.defaultCore)) {
            this.addBean(this.defaultCore, bean);
        } else {
            log.error("default core should not be null");
        }

    }

    public void addBeans(List<Object> beans) throws SolrServerException, IOException {
        if (!isBlank(this.defaultCore)) {
            this.addBean(this.defaultCore, beans);
        } else {
            log.error("default core should not be null");
        }
    }


    public void addBeans(String core, List<Object> beans) {
        if (beans != null && beans.size() > 0) {
            int size = beans.size();
            if (size <= BATCH_SIZE) {
                try {
                    solrClient.addBeans(core, beans);
                } catch (Exception e) {
                    log.error("addBeans occurs an error,core:" + core, e);
                }
            } else {
                int end = size > BATCH_SIZE ? BATCH_SIZE : size;
                int start = 0;
                while (true) {
                    List<Object> subList = beans.subList(start, end);
                    try {
                        solrClient.addBeans(core, subList);
                    } catch (Exception e) {
                        log.error("addBeans occurs an error,core:" + core, e);
                    }
                    if (end == size) {
                        break;
                    }
                    start = start + BATCH_SIZE;
                    end = (end + BATCH_SIZE);
                    if (end > size) {
                        end = size;
                    }
                }
            }
        }
    }

    public void commit() throws SolrServerException, IOException {
        this.commit(this.defaultCore,true,true,false);
    }

    public void commit(String core) throws SolrServerException, IOException {
        this.commit(core,true,true,false);
    }

    public void commit(String core,boolean waitFlush) throws SolrServerException, IOException {
        this.commit(core,waitFlush,true,false);
    }

    /**
     * 默认硬提交
     * @param core
     * @param waitFlush
     * @param waitNewSearcher
     * @throws SolrServerException
     * @throws IOException
     */
    public void commit(String core,boolean waitFlush,boolean waitNewSearcher) throws SolrServerException, IOException {
        this.commit(core,waitFlush,waitNewSearcher,false);
    }

    public void commit(String core,boolean waitFlush,boolean waitNewSearcher,boolean softCommit) throws SolrServerException, IOException {
        solrClient.commit(core,waitFlush,waitNewSearcher,softCommit);
    }

    public void clean(int commitWithInMS) throws SolrServerException, IOException {
        clean(this.defaultCore,commitWithInMS);
    }

    /**
     * 清空所有索引数据，默认为硬提交
     * @param core
     * @throws SolrServerException
     * @throws IOException
     */
    public void clean(String core) throws SolrServerException, IOException {
        solrClient.deleteByQuery(core,"*:*");
    }

    public void clean(String core, int commitWithInMS) throws SolrServerException, IOException {
        solrClient.deleteByQuery(core,"*:*",commitWithInMS);
    }

    /**
     * 增量导入
     */
    public void deltaImport(String entity) {
        importData(this.defaultCore,entity,true);
    }

    /**
     * 增量导入
     * @param core     目标Core名称
     */
    public void deltaImport(String core,String entity) {
        importData(core,entity,true);
    }

    /**
     * 全量导入
     */
    public void fullImport(String entity) {
        importData(this.defaultCore,entity,false);
    }

    /**
     * 全量导入
     * @param core     目标Core名称
     */
    public void fullImport(String core,String entity) {
        importData(core,entity,false);
    }

    /**
     * 全量/增量导入数据
     * @param delta    是否增量导入
     */
    public void importData(String entity,boolean delta) {
        importData(this.defaultCore,entity,delta);
    }

    /**
     * 全量/增量导入数据
     * @param core     目标Core名称
     * @param entity   目标实体
     * @param delta    是否增量导入
     */
    public void importData(String core,String entity,boolean delta) {
        SolrQuery query = new SolrQuery();
        // 指定RequestHandler，默认使用/select
        query.setRequestHandler("/dataimport");
        String command = delta ? "delta-import" : "full-import";
        String clean = delta ? "false" : "true";
        String optimize = delta ? "false" : "true";
        query.setParam("command", command)
                .setParam("clean", clean)
                .setParam("commit", "true")
                .setParam("entity", entity)
                .setParam("optimize", optimize);
        try {
            solrClient.query(core,query, SolrRequest.METHOD.GET);
        }  catch (Exception e) {
            log.error(command + "occurs an exception[core:" + core + "]:", e);
        }
    }

    public void close() throws IOException {
        if (solrClient != null) {
            solrClient.close();
        }
    }

    private static boolean isBlank(String str) {
        return null == str || "".equals(str);
    }
}
