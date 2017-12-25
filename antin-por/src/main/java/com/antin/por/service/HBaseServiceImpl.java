package com.antin.por.service;

import com.antin.por.entity.HBaseModel;
import com.antin.por.entity.PortraitModel;
import com.antin.por.handle.HandleFactory;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.HbaseUtils;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jichangjin on 2017/9/20.
 */
@Service
public class HBaseServiceImpl implements HBaseService {

    private final Logger log = Logger.getLogger(HBaseServiceImpl.class);
    @Autowired
    HbaseTemplate hbaseTemplate;

    /**
     * 根据rowKey和family查询数据
     *
     * @param hm
     * @return
     */
    @Override
    public List<PortraitModel> findByRowKey(HBaseModel hm) throws IOException {
        return hbaseTemplate.execute(hm.getTable(), new TableCallback<List<PortraitModel>>() {
            @Override
            public List<PortraitModel> doInTable(HTableInterface table) throws Throwable {
                return HandleFactory.handle().loadData(table, hm).handleResult();

            }
        });
    }

    /**
     *
     * @param tableName
     * @param columnFamily
     * @param column
     * @param value
     * @return
     */
    @Override
    public List<Map<String, Object>> filterByName(String tableName, String columnFamily, String column, String value) {
        log.info("Entering testSingleColumnValueFilter.");

        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        // Set the filter criteria.
        SingleColumnValueFilter filter = new SingleColumnValueFilter(
                Bytes.toBytes(columnFamily), Bytes.toBytes(column), CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(value));
        scan.setFilter(filter);

        List<Map<String, Object>> list = hbaseTemplate.find(tableName, scan, new RowMapper<Map<String, Object>>() {
            public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {

                List<Cell> ceList = result.listCells();
                Map<String, Object> map = new HashMap<String, Object>();
                String row = "";
                if (ceList != null && ceList.size() > 0) {
                    for (Cell cell : ceList) {
                        row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
                        String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                                cell.getValueLength());
                        String quali = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                                cell.getQualifierLength());
                        map.put(quali, value);
                    }
                    map.put("rowKey", row);
                }
                return map;
            }
        });

        //得到rowKey后再
        List<Map<String, Object>> resultList = new ArrayList<>();
        list.forEach(map -> {
            String rowKey = (String) map.get("rowKey");
            Map<String, Object> cellMap = new HashMap<String, Object>();
            Result result = hbaseTemplate.execute(tableName, new TableCallback<Result>() {
                @Override
                public Result doInTable(HTableInterface table) throws Throwable {
                    Get get = new Get(Bytes.toBytes(rowKey));

                    return table.get(get);
                }
            });
            for (Cell cell : result.rawCells()) {
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                String data = Bytes.toString(CellUtil.cloneValue(cell));
                cellMap.put(qualifier, data);
            }
            cellMap.put("rowId", rowKey);
            resultList.add(cellMap);
        });
        return resultList;
    }


    /**
     * 获取检验、药品项目列表
     * @param XManId 病人卡号
     *        columnFamily 类别，   检验：c   药品：p  检查：e
     *        startDate 开始时间
     * @return List<EhrXmanGuide>
     */
    @Override
    public List<Map<String, Object>> getEhrXmanGuide(String columnFamily, String XManId, String startDate) throws Exception{
        String tableName = "antin:sehr_xman_ehr_guide";
        Scan scan = new Scan();
        List<Filter> filters = new ArrayList<Filter>();
        if (columnFamily.equals("p")){
            scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("CUSTOME_CODE"));
            scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("CUSTOME_NAME"));
            scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("START_DATE"));
            SingleColumnValueFilter filter2 = new SingleColumnValueFilter(
                    Bytes.toBytes(columnFamily), Bytes.toBytes("START_DATE"), CompareFilter.CompareOp.GREATER_OR_EQUAL,
                    Bytes.toBytes(startDate));
            filters.add(filter2);
        }else if (columnFamily.equals("c")){
            scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("CHECKOUT_CODE"));
            scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("CHECKOUT_NAME"));
            scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("TIME"));
            SingleColumnValueFilter filter2 = new SingleColumnValueFilter(
                    Bytes.toBytes(columnFamily), Bytes.toBytes("TIME"), CompareFilter.CompareOp.GREATER_OR_EQUAL,
                    Bytes.toBytes(startDate));
            filters.add(filter2);
        }
        scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("XMAN_ID"));
        // Set the filter criteria.

        SingleColumnValueFilter filter = new SingleColumnValueFilter(
                Bytes.toBytes(columnFamily), Bytes.toBytes("XMAN_ID"), CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(XManId));
        filters.add(filter);
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL,filters);
        Table hTableInterface = HbaseUtils.getHTable(tableName, hbaseTemplate.getConfiguration(), hbaseTemplate.getCharset(), hbaseTemplate.getTableFactory());
        scan.setFilter(filterList);
        ResultScanner scanner = hTableInterface.getScanner(scan);

        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Result rs : scanner) {
           // String row ="";
            Map<String, Object> cellMap = new HashMap<String, Object>();
            for (Cell cell : rs.rawCells()) {
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                String data = Bytes.toString(CellUtil.cloneValue(cell));
                if(!"START_DATE".equals(qualifier)&&!"TIME".equals(qualifier))
                cellMap.put(qualifier, data);
            }
            resultList.add(cellMap);
        }
        return resultList;
    }

    //遗传病细表
    @Override
    public List<Map<String, Object>> getGeneticDetail(String rowId, String count) throws Exception{
        String tableName = "antin:genetic_dict_detial";
        String columnFamily = "i";
        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("SCIKEN_PROBABILITY"));
        scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("GENETIC_ID"));
        scan.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("SCIKEN_FLAG"));
        // Set the filter criteria.
        List<Filter> filters = new ArrayList<Filter>();
        SingleColumnValueFilter filter = new SingleColumnValueFilter(
                Bytes.toBytes(columnFamily), Bytes.toBytes("GENETIC_ID"), CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(rowId));
        filters.add(filter);
        SingleColumnValueFilter filter2 = new SingleColumnValueFilter(
                Bytes.toBytes(columnFamily), Bytes.toBytes("SCIKEN_FLAG"), CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(count));
        filters.add(filter2);
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL,filters);
        Table hTableInterface = HbaseUtils.getHTable(tableName, hbaseTemplate.getConfiguration(), hbaseTemplate.getCharset(), hbaseTemplate.getTableFactory());
        scan.setFilter(filterList);
        ResultScanner scanner = hTableInterface.getScanner(scan);

        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Result rs : scanner) {
            String row ="";
            Map<String, Object> cellMap = new HashMap<String, Object>();
            for (Cell cell : rs.rawCells()) {
                row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                String data = Bytes.toString(CellUtil.cloneValue(cell));
                cellMap.put(qualifier, data);
            }
            cellMap.put("rowId", row);
            resultList.add(cellMap);
        }
        return resultList;
    }



}
