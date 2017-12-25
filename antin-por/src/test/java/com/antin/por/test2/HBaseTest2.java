package com.antin.por.test2;

import com.antin.por.entity.HBaseModel;
import com.antin.por.entity.PortraitModel;
import com.antin.por.extension.HBaseResultBuilder;
import com.antin.por.service.HBaseService;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.HbaseUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jichangjin on 2017/9/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-hibernate.xml",
        "classpath:spring-hadoop.xml", "classpath:spring-mvc.xml"})
public class HBaseTest2 {

    @Autowired
    HbaseTemplate hbaseTemplate;

    @Autowired
    private HBaseService hBaseService;

    @Test
    public void findByRowKey() throws IOException {
        String tableName = "hpor:ehr_r",
                rowName = "cf254d9c-1b1f-47cc-9b7c-a95e3828164a",
                familyName = "health";
       HBaseModel hm = new HBaseModel(tableName, rowName, familyName);
//        List<Map<String, Object>> list = findByRowKey(hm);
        List<PortraitModel> list = hBaseService.findByRowKey(hm);
        list.forEach(m -> {
            System.out.println(m.toString());
        });

    }

    @Test
    public void getUserInfo() {//TODO  未测试
        String tableName = "hpor:ehr_r",
                rowName = "cf254d9c-1b1f-47cc-9b7c-a95e3828164a",
                familyName = "health";

        BabyMFModel babyMFModel = (BabyMFModel) hbaseTemplate.get(tableName, rowName, familyName,
                (result, i) -> new HBaseResultBuilder<>(familyName, result, BabyMFModel.class).build("babyMF").fetch());
        System.out.println(babyMFModel);
    }

    private List<Map<String, Object>> findByRowKey(HBaseModel hm) throws IOException {
        /*
         * 方法一：
         */
        Table hTableInterface = HbaseUtils.getHTable(hm.getTable(), hbaseTemplate.getConfiguration(), hbaseTemplate.getCharset(), hbaseTemplate.getTableFactory());
        Get get = new Get(Bytes.toBytes(hm.getRowKey()));
        get.addFamily(Bytes.toBytes("population"));
        get.addFamily(Bytes.toBytes("health"));
        Result result = hTableInterface.get(get);

        List<Map<String, Object>> qualifiers = new ArrayList<>();
        for (Cell cell : result.rawCells()) {
            Map<String, Object> cellMap = new HashMap<>();
            cellMap.put("family", Bytes.toString(CellUtil.cloneFamily(cell)));
            cellMap.put("qualifier", Bytes.toString(CellUtil.cloneQualifier(cell)));
            cellMap.put("value", Bytes.toString(CellUtil.cloneValue(cell)));
            cellMap.put("timestamp", cell.getTimestamp());
            qualifiers.add(cellMap);
        }
        return qualifiers;

        /*
         * 方法二：
         */
//        return hbaseTemplate.execute(hm.getTable(), new TableCallback<List<Map<String, Object>>>() {
//            @Override
//            public List<Map<String, Object>> doInTable(HTableInterface table) throws Throwable {
//                Get get = new Get(Bytes.toBytes(hm.getRowKey()));
//                get.addFamily(Bytes.toBytes("population"));
//                get.addFamily(Bytes.toBytes("health"));
//                Result result = table.get(get);
//                List<Map<String, Object>> qualifiers = new ArrayList<>();
//                for (Cell cell : result.rawCells()) {
//                    Map<String, Object> cellMap = new HashMap<>();
//                    cellMap.put("family", Bytes.toString(CellUtil.cloneFamily(cell)));
//                    cellMap.put("qualifier", Bytes.toString(CellUtil.cloneQualifier(cell)));
//                    cellMap.put("value", Bytes.toString(CellUtil.cloneValue(cell)));
//                    qualifiers.add(cellMap);
//                }
//                return qualifiers;
//            }
//        });
    }
}
