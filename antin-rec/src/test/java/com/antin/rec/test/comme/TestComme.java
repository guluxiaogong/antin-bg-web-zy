package com.antin.rec.test.comme;

import com.antin.helper.SpringContextHolder;
import com.antin.rec.engine.comme.model.OrgLocationModel;
import com.antin.rec.engine.comme.impl.DoctorProfessionCommeRecom;
import com.antin.rec.engine.comme.impl.PatientRecordCommeRecom;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Administrator on 2017/8/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-hibernate.xml",
        "classpath:spring-hadoop.xml"})
public class TestComme {

    /**
     * 测试相似推荐引擎
     */
    @Test
    public void testCommeRecomEngine() {
        PatientRecordCommeRecom prcr = SpringContextHolder.getBean("patientRecordCommeRecom");
        RecomInModel rim = new RecomInModel();
        //350211A2001-10051-20798
        rim.setPersonId("12d129c7-fb76-4457-b36f-c2c01f71fe9c");
        rim.setOrgId("350211A1001");
        rim.setDeptCode("123456789");
        rim.setDoctorCode("10001");
        rim.setLatitude(24.465102999999989);//纬度
        rim.setLongitude(118.102663000000007);//经度
        rim.setDistance(5000);//距离
        List<RecomOutModel> roms = prcr.recomEngine(rim);
        roms.forEach(r -> {
            System.out.println(r.toString());
        });
    }


    /**
     * 测试该坐标点N公里内的所有医院
     */
    @Test
    public void TestCalculateNearbyHospital() {
        double latitude = 24.465102999999989,//纬度
                longitude = 118.102663000000007,//经度
                distance = 10000;////距离

        DoctorProfessionCommeRecom dpcr = SpringContextHolder.getBean("doctorProfessionCommeRecom");
        //该点坐标5km范围内所有医院
        List<OrgLocationModel> list = dpcr.calculateNearbyHospital(latitude, longitude, distance);

        list.forEach(x -> {
            System.out.println(x.toString());
        });

        System.out.println("======================================================================");

        //从近到远排序
        TreeSet<OrgLocationModel> olms = dpcr.sortHosital(list, latitude, longitude);
        olms.forEach(o -> {
            System.out.println(o.toString());
        });

    }

    /**
     * 测试附近医院相似医生
     */
    @Test
    public void TestCalculateCommeDoctorsAndNearbyHospital() {

        RecomInModel rim = new RecomInModel();
        rim.setOrgId("350211A2001-10051-20798");
        rim.setLatitude(24.465102999999989);//纬度
        rim.setLongitude(118.102663000000007);//经度
        rim.setDistance(5000);//距离
        DoctorProfessionCommeRecom dpcr = SpringContextHolder.getBean("doctorProfessionCommeRecom");
        LinkedList<String> doctors = dpcr.calculateCommeDoctorsAndNearbyHospital(rim);
        doctors.forEach(d -> {
            System.out.println(d.toLowerCase());
        });
//点：350211A2001-10051-20798
// 相似排序//350211A2001-A4006-11401,350211A1001A-1010010-80411,350211A1049-11100-11100,350211A2001-10051-11095,350211A1001A-40400-75676,350211A1001A-1010010-80355,350211A1001A-1010010-80357,350211A1001A-1010010-80350,350211A1001A-1010010-80432,350211A2001-10051-15670,350211A1049-1080001-83757,350211A2001-10051-11096,350211A2001-A4006-10749,350211A1049-1080001-83420,350211A2001-10051-9961,350211A1001A-32000-77626,350211A2001-A4006-10746,350211A2001-10051-20785,350211A1001A-1010010-80410,350211A2001-10051-20783,350211A2001-10051-10315,350211A2001-A4006-10822,350211A2001-10051-19262,350211A1001A-1010010-75595,350211A2001-10051-9967,350211A2001-10053-10073,350211A1001A-1010010-75591,350211A1001A-1010010-75589,350211A2001-10051-11275,350211A2001-A4006-10815,350211A2001-A4006-10752,350211A2001-A4006-11408,350211A1001A-1010010-75577,350211A2001-10051-20287,350211A2001-10051-19198,350211A1001A-1010010-78570,350211A1049-1080001-82985,350211A2001-10051-19260,350211A2001-10051-20739,350211A2001-A4006-10777,350211A2001-10053-20564,350211A1001A-40400-75658,350211A1049-1080001-83429,350211A1001A-40400-78214,350211A5001-3130200-83152,350211A2001-10051-19858,350211A1001A-1010010-80434,350211A2001-10053-16409,350211A1049-1080001-82982,350211A2001-10051-21059
//最近距离与相似排序
//        350211A1001A-40400-75676
//        350211A1001A-1010010-80355
//        350211A1001A-1010010-80357
//        350211A1001A-1010010-80350
//        350211A1001A-1010010-80432
//        350211A1001A-32000-77626
//        350211A1001A-1010010-80410
//        350211A1001A-1010010-75595
//        350211A1001A-1010010-75591
//        350211A1001A-1010010-75589
//        350211A1001A-1010010-75577
//        350211A1001A-1010010-78570
//        350211A1001A-40400-75658
//        350211A1001A-40400-78214
//        350211A1001A-1010010-80434
//        350211A5001-3130200-83152
//        350211A2001-A4006-11401
//        350211A2001-10051-11095
//        350211A2001-10051-15670
//        350211A2001-10051-11096
//        350211A2001-A4006-10749
//        350211A2001-10051-9961
//        350211A2001-A4006-10746
//        350211A2001-10051-20785
//        350211A2001-10051-20783
//        350211A2001-10051-10315
//        350211A2001-A4006-10822
//        350211A2001-10051-19262
//        350211A2001-10051-9967
//        350211A2001-10053-10073
//        350211A2001-10051-11275
//        350211A2001-A4006-10815
//        350211A2001-A4006-10752
//        350211A2001-A4006-11408
//        350211A2001-10051-20287
//        350211A2001-10051-19198
//        350211A2001-10051-19260
//        350211A2001-10051-20739
//        350211A2001-A4006-10777
//        350211A2001-10053-20564
//        350211A2001-10051-19858
//        350211A2001-10053-16409
//        350211A2001-10051-21059
//        350211A1049-11100-11100
//        350211A1049-1080001-83757
//        350211A1049-1080001-83420
//        350211A1049-1080001-82985
//        350211A1049-1080001-83429
//        350211A1049-1080001-82982

    }
}
