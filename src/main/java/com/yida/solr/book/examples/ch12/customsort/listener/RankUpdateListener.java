package com.yida.solr.book.examples.ch12.customsort.listener;

import com.yida.solr.book.examples.ch12.customsort.Weibo;
import com.yida.solr.book.examples.ch12.customsort.IndexWeibo;
import com.yida.solr.book.examples.utils.GerneralUtils;
import com.yida.solr.book.examples.utils.jdbc.DBUtil;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.BytesRefBuilder;
import org.apache.lucene.util.NumericUtils;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.core.SolrCore;
import org.apache.solr.core.SolrEventListener;
import org.apache.solr.search.SolrIndexSearcher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lanxiaowei
 */
public class RankUpdateListener implements SolrEventListener {
    private static final Integer BASE = 100;

    private static final Map<Integer,Integer> ranks =
            new HashMap<Integer,Integer>();
    private static final Map<Integer,List<Integer>> thumbs =
            new HashMap<Integer,List<Integer>>();

    private SolrCore core;
    private DBUtil dbUtil;

    public RankUpdateListener(SolrCore core) {
        this.core = core;
    }

    //发送Coomit时
    public void postCommit() {
        //do nothing
    }

    //软提交时
    public void postSoftCommit() {
        //do nothing
    }

    //创建IndexSearcher时
    public void newSearcher(SolrIndexSearcher newSearcher, SolrIndexSearcher currentSearcher) {
        try {
            populateRanks(newSearcher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //初始化时
    public void init(NamedList namedList) {
        this.dbUtil = DBUtil.getInstance();
    }

    public static List<Integer> getThumbs(int docId) {
        if (thumbs.containsKey(docId)) {
            return thumbs.get(docId);
        } else {
            return Arrays.asList(0, 0);
        }
    }

    public static Integer getRank(int docId) {
        if (ranks.containsKey(docId)) {
            return ranks.get(docId);
        }
        return BASE;
    }

    //根据Document ID从数据库加载数据,获取点赞数和点踩数，然后计算评分
    private synchronized void populateRanks(SolrIndexSearcher searcher)
            throws IOException, ParseException {
        Map<Integer,Integer> ranksCopy = new HashMap<Integer,Integer>();
        Map<Integer,List<Integer>> thumbsCopy =
                new HashMap<Integer,List<Integer>>();
        Connection con = null;
        List<Weibo> weiboList = null;
        try {
            con = dbUtil.openConn();
            String sql = "select * from weibo";
            //从数据库查询数据
            weiboList = dbUtil.queryBeanList(Weibo.class, IndexWeibo.cloumnMap,con,sql,null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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
        if(GerneralUtils.isEmptyCollection(weiboList)) {
            return;
        }
        for(Weibo weibo : weiboList) {
            int id = weibo.getId();
            int thumbsUp = weibo.getThumbsUp();
            int thumbsDown = weibo.getThumbsDown();
            int totalvotes = weibo.getTotalvotes();

            int tupPct = Math.round((float) (thumbsUp * 100) / (float) totalvotes);
            int tdnPct = Math.round((float) (thumbsDown * 100) / (float) totalvotes);
            //计算评分
            int rank = tupPct - tdnPct + BASE;
            System.out.println("rank:[" + id + "]-->" + rank);
            QueryParser parser = new QueryParser("id", new KeywordAnalyzer());
            BytesRefBuilder builder = new BytesRefBuilder();
            NumericUtils.intToPrefixCoded(id,0,builder);
            Query query = new TermQuery(new Term("id",builder.toBytesRef()));
            ScoreDoc[] hits = searcher.search(query, 1).scoreDocs;
            System.out.println("hits:" + hits.length + ",currentId:" + id);
            for (int i = 0; i < hits.length; i++) {
                int docId = hits[i].doc;
                System.out.println("docId-->" + docId);
                ranksCopy.put(docId, rank);
                thumbsCopy.put(docId, Arrays.asList(tupPct, tdnPct));
            }
        }
        ranks.clear();
        ranks.putAll(ranksCopy);
        thumbs.clear();
        thumbs.putAll(thumbsCopy);
    }
}
