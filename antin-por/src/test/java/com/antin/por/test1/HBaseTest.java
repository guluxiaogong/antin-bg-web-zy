package com.antin.por.test;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by Administrator on 2017/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-hibernate.xml",
        "classpath:spring-hadoop.xml", "classpath:spring-mvc.xml"})
public class HBaseTest {
    private static final Logger log = LoggerFactory.getLogger(HBaseTest.class);

    @Autowired
    HbaseTemplate hbaseTemplate;

    @Test
    public void rowCount() {
        long rowCount = 0;
        try {
            HTable table = new HTable(hbaseTemplate.getConfiguration(), "performance_test:sehr_xman_ehr");
            //HTable table = new HTable(hbaseTemplate.getConfiguration(), "jcj_test");
            Scan scan = new Scan();
            scan.setFilter(new FirstKeyOnlyFilter());
            ResultScanner resultScanner = table.getScanner(scan);
            for (Result result : resultScanner) {
                rowCount += result.size();
            }
        } catch (IOException e) {
            log.info(e.getMessage(), e);
        }
        log.info("=====================> " + rowCount);
    }

//    public static void addTableCoprocessor(String tableName, String coprocessorClassName) {
//        try {
//            admin.disableTable(tableName);
//            HTableDescriptor htd = admin.getTableDescriptor(Bytes.toBytes(tableName));
//            htd.addCoprocessor(coprocessorClassName);
//            admin.modifyTable(Bytes.toBytes(tableName), htd);
//            admin.enableTable(tableName);
//        } catch (IOException e) {
//            log.info(e.getMessage(), e);
//        }
//    }
//
//    public static long rowCount(String tableName, String family) {
//        AggregationClient ac = new AggregationClient(configuration);
//        Scan scan = new Scan();
//        scan.addFamily(Bytes.toBytes(family));
//        long rowCount = 0;
//        try {
//            rowCount = ac.rowCount(Bytes.toBytes(tableName), new LongColumnInterpreter(), scan);
//        } catch (Throwable e) {
//            log.info(e.getMessage(), e);
//        }
//        return rowCount;
//    }
//
//    @Test
//    public void testTableRowCount() {
//        String coprocessorClassName = "org.apache.hadoop.hbase.coprocessor.AggregateImplementation";
//        HBaseUtils.addTableCoprocessor("user", coprocessorClassName);
//        long rowCount = HBaseUtils.rowCount("user", "basic");
//        System.out.println("rowCount: " + rowCount);
//    }
}
