package com.antin.por2.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-12-01.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-hadoop.xml", "classpath:spring-mvc.xml"})
public class PhoenixTest {
    @Autowired
    JdbcTemplate phoenixJdbcTemplate;

    @Test
    public void queryAsPhoenix() {
        String sql = "select * from \"jcj:phoenix_user\"";
        List<Map<String, Object>> list = phoenixJdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : list) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println("key= " + entry.getKey() + " and value= "
                        + entry.getValue());
            }
            System.out.println("===============================================");
        }
    }
}

