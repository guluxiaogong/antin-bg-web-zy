package com.antin.rec.dao;

import com.alibaba.fastjson.JSONArray;
import com.antin.helper.SpringContextHolder;
import com.antin.jdbc.Sql;
import com.antin.rec.entity.DoctorIdenModel;
import com.antin.rec.entity.RecReservationHistoryModel;
import com.antin.rec.entity.RecomOutModel;
import com.antin.rec.util.helper.RecomHelper;
import com.antin.rec.util.helper.SqlHelper;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
public class RecomDaoImpl implements RecomDao {

    private Sql sql;

    public RecomDaoImpl() {
        sql = SpringContextHolder.getBean("sqlImpl");
    }

    /**
     * 根据据id获取用户历史预约记录
     *
     * @param persionId
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<RecReservationHistoryModel> queryReservationHistoryById(String persionId) {
        JSONArray jsonArray = sql.queryAsJson(SqlHelper.getSql("queryReservationHistoryById"), new Object[]{persionId});
        return JSONArray.parseArray(jsonArray.toJSONString(), RecReservationHistoryModel.class);
    }

    public List<DoctorIdenModel> queryDoctorOut() {
        JSONArray jsonArray = sql.queryAsJson(SqlHelper.getSql("queryDoctorOut"), null);
        return JSONArray.parseArray(jsonArray.toJSONString(), DoctorIdenModel.class);
    }

    /**
     * 获取号源
     *
     * @param orgId      机构id
     * @param deptCode   科室
     * @param doctorCoed 医生
     * @return
     */
    @Override
    public List<RecomOutModel> getReservationNumber(String orgId, String deptCode,
                                                    String doctorCoed) {
        JSONArray jsonArray = sql.queryAsJson(RecomHelper.wrapNameSql(SqlHelper.getSql("getReservationNumber")),
                new Object[]{orgId, deptCode, doctorCoed});
        return JSONArray.parseArray(jsonArray.toJSONString(), RecomOutModel.class);
    }
}
