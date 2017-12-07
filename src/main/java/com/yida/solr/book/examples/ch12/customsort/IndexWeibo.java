package com.yida.solr.book.examples.ch12.customsort;

import com.yida.solr.book.examples.utils.GerneralUtils;
import com.yida.solr.book.examples.utils.jdbc.DBUtil;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lanxiaowei
 * 导入自定义排序章节示例需要的测试数据
 */
public class IndexWeibo {
    private static final String SOLR_INSTANT_CORE = "http://localhost:8080/solr/weibo";

    private static final String TABLE_NAME = "weibo";

    private static DBUtil dbUtil = DBUtil.getInstance();

    public static Map<String,String> cloumnMap = new HashMap<String,String>();
    static {

        cloumnMap.put("id","id");
        cloumnMap.put("title","title");
        cloumnMap.put("thumbs_up","thumbsUp");
        cloumnMap.put("thumbs_down","thumbsDown");
        cloumnMap.put("total_votes","totalVotes");
    }

    public static void main(String[] args) {
        indexData();
    }

    private static void indexData() {
        Connection con = null;
        try {
            con = dbUtil.openConn();
            String sql = "select * from weibo";
            List<Weibo> weiboList = dbUtil.queryBeanList(Weibo.class,cloumnMap,con,sql,null);
            if(GerneralUtils.isEmptyCollection(weiboList)) {
                return;
            }
            ConcurrentUpdateSolrClient solrClient =
                    new ConcurrentUpdateSolrClient(SOLR_INSTANT_CORE,1000,10);
            for(Weibo weibo : weiboList) {
                System.out.println(weibo.getId());
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("id",weibo.getId());
                doc.addField("title",weibo.getTitle());
                solrClient.add(doc);
            }
            //solrClient.addBeans(weiboList);
            solrClient.commit();
            solrClient.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != con) {
                    dbUtil.closeConn(con);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
