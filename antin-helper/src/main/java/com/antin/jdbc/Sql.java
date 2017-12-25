package com.antin.jdbc;


import com.alibaba.fastjson.JSONArray;

import java.sql.Connection;

/**
 * Created by Administrator on 2016/8/18.
 */
public interface Sql {
    /**
     * 执行检索操作，并将结果集以JSON数组格式返回。
     *
     * @param sql  SQL。
     * @param args 参数集。
     * @return 数据集。
     */
    JSONArray queryAsJson(String sql, Object[] args);

    Connection getConnection();
}
