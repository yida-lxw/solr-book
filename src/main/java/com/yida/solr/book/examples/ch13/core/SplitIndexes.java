package com.yida.solr.book.examples.ch13.core;

import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.params.CoreAdminParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;

import java.util.List;

/**
 * Created by Lanxiaowei
 * Core分裂或索引目录分裂
 */
public class SplitIndexes extends CoreAdminRequest {
    protected List<String> paths;
    protected List<String> targetCores;

    public static final String PATH_PARAM_NAME = "path";
    public SplitIndexes() {
        action = CoreAdminParams.CoreAdminAction.SPLIT;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public List<String> getPaths() {
        return paths;
    }

    public List<String> getTargetCores() {
        return this.targetCores;
    }

    public void setTargetCores(List<String> targetCores) {
        this.targetCores = targetCores;
    }

    @Override
    public SolrParams getParams() {
        if (action == null) {
            throw new RuntimeException("no action specified!");
        }
        ModifiableSolrParams params = new ModifiableSolrParams();
        params.set(CoreAdminParams.ACTION, action.toString());
        params.set(CoreAdminParams.CORE, core);
        if (paths != null)  {
            for (String path : paths) {
                params.add(PATH_PARAM_NAME, path);
            }
        }
        if (targetCores != null) {
            for (String targetCore : targetCores) {
                params.add(CoreAdminParams.TARGET_CORE, targetCore);
            }
        }
        return params;
    }
}
