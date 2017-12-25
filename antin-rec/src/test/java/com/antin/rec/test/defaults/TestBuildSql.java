package com.antin.rec.test.defaults;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.antin.helper.SpringContextHolder;
import com.antin.jdbc.Sql;
import com.antin.rec.engine.defaults.impl.RecordMaxDefaultsRecom;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;
import com.antin.rec.util.helper.RecomHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/17.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-hibernate.xml",
//        "classpath:spring-hadoop.xml"})
public class TestBuildSql {

    @Autowired
    private Sql sql;

    /**
     * 测试默认推荐引擎
     */
    @Test
    public void testRecomEngine() {
        RecordMaxDefaultsRecom rmdr = SpringContextHolder.getBean("recordMaxDefaultsRecom");
        RecomInModel rim = new RecomInModel();
//        rim.setOrgId("350211A1001");
//        rim.setDeptCode("32800");
//        rim.setDoctorCode("32800");
        List<RecomOutModel> roms = rmdr.recomEngine(rim);
        roms.forEach(r -> {
            System.out.println(r.toString());
        });
    }

    /**
     * 测试一段时间内的预约记录最多
     * TODO （数据可预处理）
     */
    @Test
    public void testsql() {
        String sqlStr = "select org_id, dept_code, doctor_code, count(*) counts\n" +
                "  from rec_reservation_history\n" +
                " where 1 = 1\n" +
                "   and res_date < to_date('2017-05-27', 'yyyy-mm-dd')\n" +
                "   and res_date > to_date('2017-04-27', 'yyyy-mm-dd')\n" +
                " group by org_id, dept_code, doctor_code\n" +
                " order by counts desc";

        RecordMaxDefaultsRecom rmdr = SpringContextHolder.getBean("recordMaxDefaultsRecom");
        RecomInModel rim = new RecomInModel();
        rim.setOrgId("");
        String s = RecomHelper.buildSql(sqlStr, rim);
        System.out.println("===============================");
        System.out.println(s);
    }

    @Test
    public void testqueryResult() {
        RecordMaxDefaultsRecom rmdr = SpringContextHolder.getBean("recordMaxDefaultsRecom");
        RecomInModel rim = new RecomInModel();
//        rim.setOrgId("350211A1002");
//        rim.setDeptCode("1040000");
//        rim.setDoctorCode("0001");
        rmdr.queryRecordMax(rim);
    }

    /**
     * 每次取5个医生去关联所有可预约的号源
     */
    @Test
    public void testCreateMaxSql() {
        RecordMaxDefaultsRecom rmdr = SpringContextHolder.getBean("recordMaxDefaultsRecom");
        RecomInModel rim = new RecomInModel();
        rim.setOrgId("350211A1001");
        rim.setDeptCode("32800");
        rim.setDoctorCode("32800");

        String sqlStrNew = rmdr.createMaxSql(rim);
        JSONArray params = RecomHelper.buildParams(rim);
        params.add(0);//分页
        params.add(5);

        JSONArray result = sql.queryAsJson(sqlStrNew, params.toArray());

        System.out.println();
        result.forEach(r -> {
            System.out.println(r.toString());
        });
    }

    @Test
    public void mapToJson() {
        Map<String, String> map = new HashMap<>();
        map.put("aa", "111");
        map.put("bb", "222");
        map.put("cc", "333");
        System.out.println(JSONObject.toJSONString(map));

    }
}
