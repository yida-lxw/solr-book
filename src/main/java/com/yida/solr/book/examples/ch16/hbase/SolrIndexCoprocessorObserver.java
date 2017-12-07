package com.yida.solr.book.examples.ch16.hbase;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by Lanxiaowei
 * Craated on 2016/11/17 16:59
 */
public class SolrIndexCoprocessorObserver extends BaseRegionObserver {
    private static Logger log = Logger.getLogger(SolrIndexCoprocessorObserver.class);

    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> context, Put put,
                        WALEdit edit, Durability durability) throws IOException {
        String rowKey = Bytes.toString(put.getRow());
        try {
            Cell cellInnerCode = put.get(Bytes.toBytes("data"), Bytes.toBytes("inner_code")).get(0);
            String innerCode = new String(CellUtil.cloneValue(cellInnerCode));

            Cell cellNodeId = put.get(Bytes.toBytes("data"), Bytes.toBytes("node_id")).get(0);
            String nodeId = new String(CellUtil.cloneValue(cellNodeId));

            Cell cellPayType = put.get(Bytes.toBytes("data"), Bytes.toBytes("pay_type")).get(0);
            String payType = new String(CellUtil.cloneValue(cellPayType));

            Cell cellCts = put.get(Bytes.toBytes("data"), Bytes.toBytes("cts")).get(0);
            String cts = new String(CellUtil.cloneValue(cellCts));

            Cell cellTraSeq = put.get(Bytes.toBytes("data"), Bytes.toBytes("tra_seq")).get(0);
            String traSeq = new String(CellUtil.cloneValue(cellTraSeq));

            cts=cts.replace("-","");
            cts=cts.replace(" ","");
            cts=cts.replace(":","");

            //获取HBase数据，然后索引至Solr


        } catch (Exception ex){
            log.info("write "+rowKey+" to solr fail:"+ex.getMessage());
            ex.printStackTrace();
        }
    }
}
