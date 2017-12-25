package com.antin.rec.engine.defaults.impl;

import com.alibaba.fastjson.JSONArray;
import com.antin.helper.SpringContextHolder;
import com.antin.jdbc.Sql;
import com.antin.rec.engine.defaults.BaseDefaultsRecom;
import com.antin.rec.entity.DoctorIdenNumModel;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;
import com.antin.rec.util.helper.RecomHelper;
import com.antin.rec.util.helper.SqlHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 * 基于预约记录最多的【默认推荐引擎】==>获取有号源的医生，然后查询医生号源时间
 */
public class RecordMaxDefaultsRecom2 extends BaseDefaultsRecom {

    private Sql sql;

    public RecordMaxDefaultsRecom2() {
        sql = SpringContextHolder.getBean("sqlImpl");
    }

    @Override
    public List<RecomOutModel> recomEngine(RecomInModel rim) {
        //获取预约记录最多的N个医生
        String sqlStr = RecomHelper.buildSql(SqlHelper.getSql("maxRecordDoctor"), rim);
        JSONArray params = RecomHelper.buildParams(rim);
        //返回有号源的N个医生
        List<DoctorIdenNumModel> doctorIdenNumModels = new ArrayList<>();
        strategy(sqlStr, params, doctorIdenNumModels, 0);

        List<RecomOutModel> roms = new ArrayList<>();
        for (DoctorIdenNumModel dinm : doctorIdenNumModels) {
            JSONArray jsonArray = sql.queryAsJson(wrapNameSql(createNumSourceSql()), new Object[]{dinm.getOrgIdSource(), dinm.getDeptCode(), dinm.getDoctorCode()});
            List<RecomOutModel> list = JSONArray.parseArray(jsonArray.toJSONString(), RecomOutModel.class);
            roms.addAll(list);
        }//TODO 最近时间处理？
        return super.parseResult(rim, roms);
    }

    private void strategy(String sqlStr, JSONArray params, List<DoctorIdenNumModel> doctorIdenModels, int num) {
        params.add(0, num);
        params.add(1, num + 100);
        JSONArray result = sql.queryAsJson(sqlStr, params.toArray());
        List<DoctorIdenNumModel> list = JSONArray.parseArray(result.toJSONString(), DoctorIdenNumModel.class);
        list.removeAll(RecomHelper.doctorOutList);
        doctorIdenModels.addAll(list);
        if (doctorIdenModels.size() < 5) {//返回最少10条记录
            params.remove(1);
            params.remove(0);
            num += 100;
            if (num < 2000)//当大于全部医生时也结束
                strategy(sqlStr, params, doctorIdenModels, num);
        }
    }

    private String createNumSourceSql() {
        StringBuilder sb = new StringBuilder();
        String sourceNumSql = SqlHelper.getSql("sourceNumSql");
        sb.append("select * from (")
                .append(sourceNumSql)
                .append(")")
                .append(" where org_id=?")
                .append(" and dept_code=?")
                .append(" and doctor_code=?");
        return sb.toString();
    }

    /**
     * org_id为号源中的机构id
     *
     * @param sqlStr
     * @return
     */
    public static String wrapNameSql(String sqlStr) {
        StringBuilder sb = new StringBuilder();
        sb.append("select f1.*,f2.name as org_name,f3.name as dept_name,f4.name as doctor_name")
                .append(" from (")
                .append(sqlStr)
                .append(") f1 ")
                .append("left join rec_urp_org f2 ")
                .append("on f1.org_id = f2.org_id ")
                .append("left join rec_urp_dept f3 ")
                .append("on f2.org_id = f3.org_id ")
                .append("and f1.dept_code = f3.code ")
                .append("left join rec_urp_doctor f4 ")
                .append("on f3.code = f4.dept_code ")
                .append("and f3.org_id = f4.org_id ")
                .append("and f1.doctor_code = f4.code ");
        return sb.toString();

    }
}
