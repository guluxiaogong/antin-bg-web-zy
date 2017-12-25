package com.antin.rec.test.main;

import com.antin.helper.SpringContextHolder;
import com.antin.rec.action.RecomAction;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;
import com.antin.rec.process.RecomAssembleDefault;
import com.antin.rec.process.RecomConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-hibernate.xml",
        "classpath:spring-hadoop.xml"})
public class TestRecom {

    /**
     * 测试推荐系统0.2
     */
    @Test
    public void testRecomEngineAction() {
        RecomAction recomAction = new RecomAction();
        //personId=c7638c0d-ac4a-4c70-811b-c6a2901a86ed
        String personId = "",
                orgId = "",
                deptCode = "",
                doctorCode = "";

        List<RecomOutModel> recomOutModels = recomAction.recomEngineAction(personId, orgId, deptCode, doctorCode);
        recomOutModels.forEach(rom -> {
            System.out.println(rom.toString());
        });

    }

    /**
     * 测试推荐系统0.1
     */
    @Test
    public void testRecomEngine() {
        // 5fc111ba-df5d-4d17-a21f-b048767a0ab8//350211A1001//32800//32800
        String personId = "c7638c0d-ac4a-4c70-811b-c6a2901a86ed",
                orgId = "",
                deptCode = "",
                doctorCode = "";
        // 用户实时位置
        // double longitude=0;//经度
        // double latitude=0;//纬度

        RecomAction recomAction = SpringContextHolder.getBean("recomAction");

        //登录加载
        recomAction.loginLoad(personId);

        //参数设置
        RecomInModel rim = new RecomInModel();
        rim.setPersonId(personId);
        rim.setOrgId(orgId);
        rim.setDeptCode(deptCode);
        rim.setDoctorCode(doctorCode);

        //推荐
        List<RecomOutModel> list = new RecomAssembleDefault().recomEngine(rim);

        if (list != null)
            list.forEach(item ->
                    System.out.println(item.toString())
            );


    }


}
