package com.antin.rec.test.comme;

import com.antin.jdbc.Sql;
import com.antin.rec.engine.defaults.impl.DoctorNoterDefaultsRecom;
import com.antin.rec.engine.defaults.impl.RecordMaxDefaultsRecom;
import com.antin.rec.engine.defaults.impl.SourcesIdelDefaultsRecom;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-hibernate.xml",
        "classpath:spring-hadoop.xml"})
public class TestSql {

    @Autowired
    private Sql sql;
//    @Autowired
//    private DoctorNoterDefaultsRecom doctorNoterDefaultsRecom;
//    @Autowired
//    private DoctorReservationInfo doctorReservationInfo;
//    @Autowired
//    private SourcesIdelDefaultsRecom sourcesIdelDefaultsRecom;
//    @Autowired
//    private RecordMaxDefaultsRecom recordMaxDefaultsRecom;

    @Test
    public void TestSqlQuery() {
//        JSONArray jsonArray = sql.queryAsJson("select * from A_TEST t", null);
//        jsonArray.forEach(x -> {
//            System.out.println(x.toString());
//        });


        //测试获取医生号源
//        Map<String, List<String>> result = doctorReservationInfo.getReservationNumber("2", "1070110", "11015");
//        if (result.size() > 0) System.out.println(result.toString());
        //测试医生获取
//        List<RecomOutModel> recomModels = recordMaxDefaultsRecom.recomEngine(new RecomInModel());
//        for (RecomOutModel r : recomModels) {
//            System.out.println(r.toString());
//        }


    }
}
