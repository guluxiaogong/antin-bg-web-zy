package com.antin.rec.action;

import com.antin.helper.SpringContextHolder;
import com.antin.rec.dao.RecomDao;
import com.antin.rec.dao.RecomDaoImpl;
import com.antin.rec.engine.comme.impl.DoctorProfessionCommeRecom;
import com.antin.rec.engine.defaults.impl.RecordMaxDefaultsRecom;
import com.antin.rec.engine.prendre.impl.RecentePrendreRecom;
import com.antin.rec.entity.DoctorIdenModel;
import com.antin.rec.entity.RecReservationHistoryModel;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;
import com.antin.rec.process.RecomAssemble;
import com.antin.rec.process.RecomAssembleDefault;
import com.antin.rec.process.RecomAssembleTest;
import com.antin.rec.process.RecomConfig;
import com.antin.rec.util.helper.RecomHelper;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Administrator on 2017/8/14.
 */
@Controller
@RequestMapping("recom")
public class RecomAction {

    /**
     * 推荐主方法
     *
     * @param personId   用户id
     * @param orgId      机构编码
     * @param deptCode   科室编码
     * @param doctorCode 医生编码
     * @return
     */
    @ResponseBody
    @RequestMapping("/recomEngine")
    public List<RecomOutModel> recomEngineAction(String personId, String orgId, String deptCode, String doctorCode) {

        RecomInModel rim = new RecomInModel();

        rim.setPersonId(personId);
        rim.setOrgId(orgId);
        rim.setDeptCode(deptCode);
        rim.setDoctorCode(doctorCode);

        RecomConfig rc = new RecomConfig();
        rc.setBaseDefaultsRecom(new RecordMaxDefaultsRecom());//默认推荐
        rc.setBasePrendreRecom(new RecentePrendreRecom());//预约记录推荐
        rc.setBaseCommeRecom(new DoctorProfessionCommeRecom());//相似推荐

        //推荐流程
        RecomAssemble recomAssemble = new RecomAssembleTest();
        recomAssemble.setRecomConfig(rc);

        return recomAssemble.recomEngine(rim);
    }

    /**
     * 加载没有号源的医生
     */
    @PostConstruct
    public void doctorOutLoad() {
        RecomDao recomDao = new RecomDaoImpl();
        List<DoctorIdenModel> list = recomDao.queryDoctorOut();
        RecomHelper.doctorOutList.addAll(list);
    }

    /**
     * 存放每位用户历史预约信息，容量大时放在缓存数据库中//TODO
     */
    public static HashMap<String, TreeSet<RecReservationHistoryModel>> hashMap = new HashMap<>();

    /**
     * 用户登录时加载信息
     *
     * @param personId
     */
    public void loginLoad(String personId) {
        if (StringUtils.isEmpty(personId))
            throw new RuntimeException("login id is null");
        if (hashMap.get(personId) == null) {
            //默认推荐
            RecomDao recomDao = SpringContextHolder.getBean("recomDaoImpl");
            List<RecReservationHistoryModel> rrhm = recomDao.queryReservationHistoryById(personId);

            if (rrhm != null && rrhm.size() != 0) {
                TreeSet<RecReservationHistoryModel> set = new TreeSet<RecReservationHistoryModel>(rrhm);
                hashMap.put(personId, set);
            }
        }
    }
}
