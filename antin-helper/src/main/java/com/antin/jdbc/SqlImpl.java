package com.antin.jdbc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/8/18.
 */
@Component
public class SqlImpl implements Sql {

    @Autowired
    Converter converter;

    @Autowired
    DataSource dataSource;

    @Autowired
    protected Validator validator;

    private static Logger log = LoggerFactory.getLogger(SqlImpl.class);

    protected Map<String, String> labels = new ConcurrentHashMap<>();

    @Override
    public JSONArray queryAsJson(String sql, Object[] args) {

        if (log.isDebugEnabled())
            log.debug("执行SQL[{" + sql + "}:{" + converter.toString(args) + "}]检索操作。");
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            setArgs(pstmt, args);
            JSONArray array = queryAsJson(pstmt.executeQuery());
            pstmt.close();

            return array;
        } catch (SQLException e) {
            log.warn(e + "执行SQL[{" + sql + "}:{" + converter.toString(args) + "}]检索时发生异常！");

            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException var10) {

                }
            }
        }

    }

    /**
     * 将结果集转成JSONArray
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    protected JSONArray queryAsJson(ResultSet rs) throws SQLException {
        JSONArray array = new JSONArray();
        List<String> names = new ArrayList<>();
        int column = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= column; i++)
            names.add(rs.getMetaData().getColumnLabel(i));
        for (; rs.next(); ) {
            JSONObject object = new JSONObject();
            for (String name : names)
                object.put(formatColumnLabel(name), converter.toString(rs.getObject(name)));
            array.add(object);
        }
        rs.close();

        return array;
    }

    protected String formatColumnLabel(String label) {
        if (validator.isEmpty(label))
            return label;

        String string = labels.get(label);
        if (string != null)
            return string;

        StringBuffer sb = new StringBuffer();
        boolean line = false;
        for (char ch : label.toLowerCase().toCharArray()) {
            if (ch == '_') {
                line = true;

                continue;
            }

            if (line) {
                line = false;
                sb.append((char) (ch >= 'a' && ch <= 'z' ? (ch - 'a' + 'A') : ch));

                continue;
            }

            sb.append(ch);
        }
        string = sb.toString();
        labels.put(label, string);

        return string;
    }

    /**
     * 设置参数集。
     *
     * @param pstmt PreparedStatement实例。
     * @param args  参数集。
     * @throws SQLException
     */
    protected void setArgs(PreparedStatement pstmt, Object[] args) throws SQLException {
        if (validator.isEmpty(args))
            return;

        for (int i = 0; i < args.length; i++)
            pstmt.setObject(i + 1, args[i]);
    }

    public Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
            if (connection == null) {
                throw new IllegalStateException("Connection returned by DataSource [" + dataSource + "] was null");
            }
            return connection;
        } catch (SQLException var11) {
            throw new RuntimeException("Could not get database url", var11);
        }

    }
}
