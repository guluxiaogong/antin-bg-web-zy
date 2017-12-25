package com.antin.rec.test.utils.json;

import com.antin.rec.util.json.JSONUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/15 0015.
 */
public class TestJson {

    @Test
    public void test1(String[] args) {
        Map<String, String> value = new JSONUtil().getValue("{\"doctorCode\":\"93610\",\"tRank\":\"14410\",\"orgId\":\"4\",\"deptCode\":\"ZMZJ\"}", new ArrayList() {{
            add("doctorCode");
            add("tRank");

        }});
        if (value != null && value.size() > 0)
            System.out.println(value.toString());

    }
}