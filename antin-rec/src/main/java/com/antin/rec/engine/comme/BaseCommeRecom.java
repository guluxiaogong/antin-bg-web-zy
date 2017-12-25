package com.antin.rec.engine.comme;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.antin.rec.dao.RecomDao;
import com.antin.rec.dao.RecomDaoImpl;
import com.antin.rec.engine.BaseRecom;
import com.antin.rec.engine.comme.dao.CommeDao;
import com.antin.rec.engine.comme.dao.CommeDaoImpl;
import com.antin.rec.engine.comme.model.OrgLocationModel;
import com.antin.rec.entity.RecReservationHistoryModel;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;
import com.antin.rec.util.location.LatitudeLontitudeUtil;
import com.antin.rec.util.location.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Administrator on 2017/8/14.
 * 相似医生推荐引擎基类
 */
public abstract class BaseCommeRecom extends BaseRecom {

    private Logger logger = LoggerFactory.getLogger(BaseCommeRecom.class);

    protected static CommeDao commeDao = new CommeDaoImpl();

    private static RecomDao recomDao = new RecomDaoImpl();

    @Override
    public List<RecomOutModel> recomEngine(RecomInModel rim) {
        long startTime = System.currentTimeMillis();
        //如果机构、科室、医生都为空，就没法提供相似推荐
        if (StringUtils.isEmpty(rim.getOrgId()))
            return null;
        //计算得相似医生列表
        LinkedList<String> doctorCommes = calculateCommeDoctorsAndNearbyHospital(rim);
        if (doctorCommes == null)//先择某个机构没有号源，得计算出该医院附近医院，然后（相似或默认）
            return null;
        //针对已登录用户的历史就诊过的医生放在前面
        LinkedList<String> doctors = patientHistoryDoctors(rim.getPersonId(), doctorCommes);

        List<RecomOutModel> roms = new ArrayList<>();

        for (String doctor : doctors) {
            String[] codes = doctor.split("-");
            if (codes.length != 5)
                throw new RuntimeException("error doctor code...");

            long startTime2 = System.currentTimeMillis();
            //获取医生号源
            List<RecomOutModel> recomOutModels = recomDao.getReservationNumber(codes[1], codes[2], codes[4]);
            long endTime2 = System.currentTimeMillis();
            logger.info("相似推荐关联号源耗时：{}s", (endTime2 - startTime2) / 1000);
            if (recomOutModels.isEmpty())
                continue;
            roms.addAll(super.parseResult(rim, recomOutModels));

            if (roms.size() >= rim.getNumber())//TODO 每次取多省个医生
                break;
        }

        long endTime = System.currentTimeMillis();
        logger.info("相似推荐耗时：{}s", (endTime - startTime) / 1000);

        return roms;
    }

    /**
     * 用户历史就诊相似医生排前面的列表
     *
     * @param personId     用户历史预约集合
     * @param doctorCommes 相似医生列表
     * @return 返回用户历史就诊相似医生排前面的列表
     */
    public LinkedList<String> patientHistoryDoctors(String personId, LinkedList<String> doctorCommes) {
        if (personId == null)
            return doctorCommes;
        LinkedList<String> doctors = new LinkedList<>();
        //病人历史就诊医生
        List<RecReservationHistoryModel> rrhms = recomDao.queryReservationHistoryById(personId);
        for (int m = 0; m < doctorCommes.size(); m++) {
            for (int n = 0; n < rrhms.size(); n++) {
                RecReservationHistoryModel r = rrhms.get(n);
                if (doctorCommes.get(m).equals(r.getOrgId() + "-" + r.getDeptCode() + "-" + r.getDoctorCode()))
                    doctors.addLast(doctorCommes.get(n));
            }
        }
        doctorCommes.forEach(d -> {
            if (!doctors.contains(d))
                doctors.addLast(d);
        });
        return doctors;
    }

    /**
     * 根据附近医院与医生相似度计算医生列表
     *
     * @param rim codes医疗机构编码+科室编码+医生编码
     * @param rim 纬度
     * @param rim 经度
     * @param rim 距离
     * @return 医生列表
     */
    public LinkedList<String> calculateCommeDoctorsAndNearbyHospital(RecomInModel rim) {

        //获取相似医生
        JSONObject jsonObject = commeDao.queryCommeDoctors(rim);
        if (jsonObject == null || jsonObject.isEmpty())
            return null;
        OrgLocationModel orgLocationModel = JSONObject.parseObject(jsonObject.toJSONString(), OrgLocationModel.class);

        if (rim.getLatitude() != 0L && rim.getLongitude() != 0L) {//如果用户地址不为空就用用户地址，如果用户地址为空就用医院址
            orgLocationModel.setLatitude(rim.getLatitude());
            orgLocationModel.setLongitude(rim.getLongitude());
        }
        //获取附近医院
        List<OrgLocationModel> olms = calculateNearbyHospital(orgLocationModel.getLatitude(), orgLocationModel.getLongitude(), rim.getDistance());
        TreeSet<OrgLocationModel> treeSet = sortHosital(olms, orgLocationModel.getLatitude(), orgLocationModel.getLongitude());

        LinkedList<String> doctors = new LinkedList<>();

        String codes = orgLocationModel.getCodes();
        String[] codesArr = codes.split(",");

        for (OrgLocationModel olm : treeSet) {
            for (String code : codesArr) {
                if (code.startsWith(olm.getOrgId()))
                    doctors.addLast(code);
            }
        }
        //把没在附近距离范围的其他相似医生全部放集合后边// TODO 附近与相似度权重进行排序
        for (String code : codesArr) {
            if (!doctors.contains(code))
                doctors.addLast(code);
        }
        return doctors;
    }

    /**
     * 计算距离范围内的医院
     *
     * @param lat      纬度
     * @param lng      经度
     * @param distance 距离(米)
     * @return 返回医院列表
     */
    public List<OrgLocationModel> calculateNearbyHospital(double lat, double lng, double distance) {

        //坐标附近distance米组成正方形四点坐标
        Location[] rectangle4Point = LatitudeLontitudeUtil.getRectangle4Point(lat, lng, distance == 0 ? 5000 : distance);

        //数据库中查询
        JSONArray jsonArray = commeDao.queryHospitalByLatAndLng(rectangle4Point[3].getLatitude(), rectangle4Point[0].getLatitude(),
                rectangle4Point[0].getLongitude(), rectangle4Point[3].getLongitude());

        return JSONArray.parseArray(jsonArray.toJSONString(), OrgLocationModel.class);
    }

    /**
     * 对医院从近到远排序
     *
     * @param olms 范围内的医院列表
     * @param lat  点的纬度
     * @param lng  点的经度
     * @return 与点距离从近到远排序后的列表
     */
    public TreeSet<OrgLocationModel> sortHosital(List<OrgLocationModel> olms, double lat, double lng) {
        TreeSet<OrgLocationModel> list = new TreeSet<>();
        olms.forEach(olm -> {
            Double distance = LatitudeLontitudeUtil.getDistance(olm.getLatitude(), olm.getLongitude(), lat, lng);
            olm.setDistance(distance);
            list.add(olm);
        });
        return list;
    }
}
