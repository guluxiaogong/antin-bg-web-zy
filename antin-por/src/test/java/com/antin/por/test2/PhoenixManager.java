package com.antin.por2.test;

import java.sql.*;

/**
 * Created by Administrator on 2017-12-13.
 */
public class PhoenixManager {
    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
            conn = DriverManager.getConnection("jdbc:phoenix:antin-001,antin-003,antin-002:2181");
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from \"jcj:phoenix_user\"");
            while (rs.next()) {
                System.out.println("height: " + rs.getString("height"));
                System.out.print("name: " + rs.getString("name"));
                System.out.print("phone: " + rs.getString("phone"));
                System.out.print("weight: " + rs.getString("weight"));
                System.out.print("age: " + rs.getString("age"));
                System.out.println();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
