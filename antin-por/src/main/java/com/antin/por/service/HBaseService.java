package com.antin.por.service;


import com.antin.por.entity.HBaseModel;
import com.antin.por.entity.PortraitModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by jichangjin on 2017/9/20.
 */
public interface HBaseService {
    List<PortraitModel> findByRowKey(HBaseModel hm) throws IOException;
    List<Map<String, Object>> filterByName(String tableName, String columnFamily, String column, String value);
    List<Map<String, Object>> getEhrXmanGuide(String columnFamily, String XManId, String startDate) throws Exception;
    List<Map<String, Object>> getGeneticDetail(String rowId, String count) throws Exception;
}
