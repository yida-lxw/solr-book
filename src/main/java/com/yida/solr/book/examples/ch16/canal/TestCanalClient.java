package com.yida.solr.book.examples.ch16.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import org.apache.solr.common.SolrInputDocument;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CanalClient测试
 */
public class TestCanalClient {
    public static void main(String args[]) {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress("linux.yida01.com",
                11111), "example", "canal", "canal");
        int batchSize = 1000;
        int emptyCount = 0;
        long batchId = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            int totalEmtryCount = 120;
            while (emptyCount < totalEmtryCount) {
                // 获取指定数量的数据
                Message message = connector.getWithoutAck(batchSize);
                batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }
                // 提交确认
                connector.ack(batchId);
            }
            System.out.println("empty too many times, exit");
        } catch (Exception e) {
            // 处理失败, 回滚数据
            connector.rollback(batchId);
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }
            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }
            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------> before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------> after");
                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }
    private static void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

    /**
     * 创建增量的Solr索引文档对象
     * 然后你需要通过SolrJ调用Solr的原子更新接口更新该增量索引文档对象至Solr Server
     * 关于如何使用SolrJ来实现原子更新请参见随书源码的第13章中的TestAutomicUpdate类
     * @param columns
     */
    private static void createDeltaSolrDocument(List<Column> columns,EventType eventType) {
        SolrInputDocument doc = new SolrInputDocument();
        for (Column column : columns) {
            //当前数据库表字段的数据是否已更新
            boolean updated = column.getUpdated();
            //数据库表的字段名称
            String columnName = column.getName();
            //该字段更新后的值
            String columnVal = column.getValue();
            //如果该字段的值未更新并且该字段不是id主键字段，那么就跳过不处理该字段
            if(!updated && !"id".equals(columnName)) {
                continue;
            }
            //这里假定你的数据库表字段名称与你Solr的schema.xml中定义的域名称是一致的，
            //如果两者不是一致的，那么你还需要使用一个Map结构来对两者进行关系映射
            //同时，Canal实例返回的增量数据都是String类型，因此你还需要根据你schema.xml中定义的域
            //对应的实际域类型来进行适当的数据类型转换，比如你solr中域使用的date域类型，那么这里就需要
            // 将columnVal值转换成java.util.Date类型。

            //如果是删除操作
            if (eventType == EventType.DELETE) {
                Map<String,Object> removeOpt = new HashMap<String,Object>(1);
                removeOpt.put("set","");
                doc.addField(columnName,removeOpt);
            }
            //如果是插入操作
            else if (eventType == EventType.INSERT) {
                doc.addField(columnName,columnVal);
            }
            //如果是更新操作
            else if (eventType == EventType.UPDATE) {
                Map<String,Object> updateOpt = new HashMap<String,Object>(1);
                updateOpt.put("set",columnVal);
                doc.addField(columnName,updateOpt);
            }
            //其他操作暂时忽略
        }
    }
}
