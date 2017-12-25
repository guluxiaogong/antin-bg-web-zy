package com.antin.por.test;

import com.antin.helper.EsClient;
import org.elasticsearch.action.get.GetResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2017/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml",
        "classpath:spring-hibernate.xml", "classpath:spring-hadoop.xml"})
public class TestEs {

    private static final Logger log = LoggerFactory.getLogger(TestEs.class);

    @Autowired
    private EsClient esClient;

    @Test
    public void getFromEsById() {
        GetResponse response = null;
        try {
            response = esClient.getObject().prepareGet("bank", "account", "25").get();
            log.info("===================================================================");
            System.out.println(response.toString());
            log.info("===================================================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
