package com.antin.rec.util.helper;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.antin.rec.engine.defaults.emum.RecordMaxDefaultEmum;
import com.antin.rec.entity.DoctorIdenModel;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.process.CalculateTime;
import com.antin.rec.process.CalculateTimeMid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */
public class RecomHelper {

    //号源时间匹配规则
    public static CalculateTime calculateTime = new CalculateTimeMid();
    /**
     * 加载没有号源的医生
     * //TODO 当医生新开放号源记录更新list
     */
    public static List<DoctorIdenModel> doctorOutList = new ArrayList<>();

    /**
     * 构造sql参数
     *
     * @param rim 医院，科室，医生条件
     * @return 返回数组
     */
    public static JSONArray buildParams(RecomInModel rim) {
        JSONArray params = new JSONArray();
        if (!StringUtils.isEmpty(rim.getOrgId()))
            params.add(rim.getOrgId());
        if (!StringUtils.isEmpty(rim.getDeptCode()))
            params.add(rim.getDeptCode());
        if (!StringUtils.isEmpty(rim.getDoctorCode()))
            params.add(rim.getDoctorCode());
        return params;
    }

    public static String buildSql(String sqlStr, RecomInModel rim) {
        if (StringUtils.isEmpty(rim.getOrgId()))
            return sqlStr;
        StringBuilder sb = new StringBuilder();
        sb.append("select * from (")
                .append(sqlStr)
                .append(") where 1=1");
        //添加机构
        sb = appendSB(sb, RecordMaxDefaultEmum.OrgId.getName());
        if (!StringUtils.isEmpty(rim.getDeptCode())) //添加科室
            sb = appendSB(sb, RecordMaxDefaultEmum.DeptCode.getName());
        if (!StringUtils.isEmpty(rim.getDoctorCode())) //添加医生
            sb = appendSB(sb, RecordMaxDefaultEmum.DoctorCode.getName());
        //添加机构、科室、医生
        return sb.toString();
    }

    private static StringBuilder appendSB(StringBuilder sb, String column) {
        sb.append(" ")
                .append(RecordMaxDefaultEmum.And.getName())
                .append(" ")
                .append(column)
                .append(RecordMaxDefaultEmum.Equal.getName())
                .append(RecordMaxDefaultEmum.Question.getName())
                .append(" ");
        return sb;
    }


    /**
     * 关联号源表
     *
     * @return
     */
    public static String innerJionSourceNumSql() {
        StringBuilder sb = new StringBuilder();
        sb.append(" inner join (")
                .append(SqlHelper.getSql("sourceNumSql"))
                .append(") t2")
                .append(" on t1.org_id = t2.org_id")
                .append(" and t1.dept_code = t2.dept_code")
                .append(" and t1.doctor_code = t2.doctor_code");
        return sb.toString();
    }


    /**
     * sql关联名称
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
                .append("left join urp_dept f3 ")
                .append("on f2.org_id = f3.org_id ")
                .append("and f1.dept_code = f3.code ")
                .append("left join urp_doctor f4 ")
                .append("on f3.code = f4.dept_code ")
                .append("and f3.org_id = f4.org_id ")
                .append("and f1.doctor_code = f4.code ");
        return sb.toString();

    }
}
