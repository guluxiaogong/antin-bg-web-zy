package com.antin.rec.engine.comme.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.antin.helper.SpringContextHolder;
import com.antin.jdbc.Sql;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.util.helper.RecomHelper;
import com.antin.rec.util.helper.SqlHelper;

/**
 * Created by Administrator on 2017/8/15.
 */
public class CommeDaoImpl implements CommeDao {

    private Sql sql;

    public CommeDaoImpl() {
        sql = SpringContextHolder.getBean("sqlImpl");
    }


    /**
     * 查询相似医生
     * TODO  下面语句可通过预处理优化
     *
     * @param rim 医院、科室、医生
     * @return 返回第一条数据
     */
    @Override
    public JSONObject queryCommeDoctors(RecomInModel rim) {
        StringBuilder sb = new StringBuilder();
        String sqlStr = SqlHelper.getSql("queryCommeDoctors");
        sb.append("select t1.*, t2.longitude, t2.latitude from (")
                .append(RecomHelper.buildSql(sqlStr, rim))
                .append(") t1")
                .append(" left join rec_pre_org_location t2")
                .append(" on t1.org_id = t2.org_id");
        JSONArray jsonArray = sql.queryAsJson(sb.toString(), RecomHelper.buildParams(rim).toArray());
        if (jsonArray.isEmpty())
            return null;
        return jsonArray.getJSONObject(0);
    }

    /**
     * 根据坐标范围查
     *
     * @param latMin 纬度下界
     * @param latMax 纬度上界
     * @param lngMin 经度下界
     * @param lngMax 经度上界
     */
    @Override
    public JSONArray queryHospitalByLatAndLng(double latMin, double latMax, double lngMin, double lngMax) {
        return sql.queryAsJson(SqlHelper.getSql("queryHospitalByLatAndLng"),
                new Object[]{latMin, latMax, lngMin, lngMax});
    }


    /**
     * 可以好像可以省了
     *
     * @param id
     * @return
     */
//    public JSONArray queryDoctorsByPatientId(String id) {
//        String sqlStr = "select citizen_id as id,\n" +
//                "       org_id,\n" +
//                "       dept_code,\n" +
//                "       doctor_code,\n" +
//                "       org_id || '-' || dept_code || '-' || doctor_code as code\n" +
//                "  from rec_urp_reservation_history\n" +
//                " where citizen_id = ?";
//        return sql.queryAsJson(sqlStr, new Object[]{id});
//    }
//    select citizen_id as id,
//            org_id,
//            dept_code,
//            doctor_code,
//    org_id || '-' || dept_code || '-' || doctor_code as code
//    from rec_urp_reservation_history
//    where citizen_id = '980498b9-e624-4582-bb14-d8da56ee3694'
}
