package com.antin.rec.engine.prendre.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.antin.helper.SpringContextHolder;
import com.antin.jdbc.Sql;
import com.antin.rec.engine.prendre.BasePrendreRecom;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;
import com.antin.rec.util.helper.RecomHelper;
import com.antin.rec.util.helper.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 * 基于最近预约的【预约记录推荐引擎】
 */
public class RecentePrendreRecom extends BasePrendreRecom {
    private Logger logger = LoggerFactory.getLogger(RecentePrendreRecom.class);
    private Sql sql;

    public RecentePrendreRecom() {
        sql = SpringContextHolder.getBean("sqlImpl");
    }

    @Override
    public List<RecomOutModel> recomEngine(RecomInModel rim) {
        long startTime = System.currentTimeMillis();
        String sqlStr = createRecentePrendSql(rim);
        JSONArray params = RecomHelper.buildParams(rim);
        params.add(0, rim.getPersonId());

        //TODO 如果用户只选择机构要根据用户最的一次科室进行推荐
        //获取用户预约记录信息
        long startTime2 = System.currentTimeMillis();
        JSONArray result = sql.queryAsJson(sqlStr, params.toArray());
        long endTime2 = System.currentTimeMillis();
        logger.info("预约记录推荐查询用户记录耗时：{}s", (endTime2 - startTime2) / 1000);

        if (result.isEmpty())
            return null;
        JSONObject jsonObject = result.getJSONObject(0);
        RecomOutModel rom = JSONObject.parseObject(jsonObject.toJSONString(), RecomOutModel.class);

        //如果号源为空，将该医生信息添加到入参中，给相似推荐引擎用
        rim.setOrgId(rom.getOrgId());
        rim.setDeptCode(rom.getDeptCode());
        rim.setDoctorCode(rom.getDoctorCode());
        //计算与该时间最相似用
        rim.setStartTime(rom.getStartTime());
        rim.setEndTime(rom.getEndTime());

        //根据用户最近一次预约记录，获取该记录医生号源
        long startTime3 = System.currentTimeMillis();
        JSONArray numbers = sql.queryAsJson(RecomHelper.wrapNameSql(SqlHelper.getSql("queryNumerByCondition")), new Object[]{rom.getOrgId(), rom.getDeptCode(), rom.getDoctorCode()});
        long endTime3 = System.currentTimeMillis();
        logger.info("预约记录推关联号源荐耗时：{}s", (endTime3 - startTime3) / 1000);
        if (numbers.isEmpty()) //如果号源为空，
            return null;

        List<RecomOutModel> roms = JSONArray.parseArray(numbers.toJSONString(), RecomOutModel.class);

        long endTime = System.currentTimeMillis();
        logger.info("预约记录推荐耗时：{}s", (endTime - startTime) / 1000);
        //结果集整理
        return super.parseResult(rim, roms);
    }

    private String createRecentePrendSql(RecomInModel rim) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from (")
                .append(RecomHelper.buildSql(recentePrendSql(), rim))
                .append(") where rownum = 1");

        return RecomHelper.wrapNameSql(sb.toString());
    }

    /**
     * 用户最近一条预约记录
     *
     * @return sql
     */
    private String recentePrendSql() {
        return "select * from rec_reservation_history where citizen_id=? order by res_date desc";
    }
}
