package com.antin.rec.engine.defaults.impl;

import com.alibaba.fastjson.JSONArray;
import com.antin.helper.SpringContextHolder;
import com.antin.jdbc.Sql;
import com.antin.rec.engine.defaults.BaseDefaultsRecom;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;
import com.antin.rec.util.helper.RecomHelper;
import com.antin.rec.util.helper.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 * 基于预约记录最多的【默认推荐引擎】==》医生号源一起关联查询
 */
public class RecordMaxDefaultsRecom extends BaseDefaultsRecom {

    private Logger logger = LoggerFactory.getLogger(RecordMaxDefaultsRecom.class);

    private Sql sql;

    private final static int STEP_NUM = 10;//每次增加10倍的医生去和号源关联

    public RecordMaxDefaultsRecom() {
        sql = SpringContextHolder.getBean("sqlImpl");
    }

    @Override
    public List<RecomOutModel> recomEngine(RecomInModel rim) {
        long startTime = System.currentTimeMillis();
        List<RecomOutModel> roms = new ArrayList<>();
        String sqlStrNew = null;
        JSONArray params = null;
        int start = 0, end = 20;

        do {
            if (sqlStrNew == null)
                sqlStrNew = createMaxSql(rim);
            if (params == null)
                params = RecomHelper.buildParams(rim);
            params.add(0, start);//分页
            params.add(1, end);
            long startTime2 = System.currentTimeMillis();
            JSONArray result = sql.queryAsJson(sqlStrNew, params.toArray());
            long endTime2 = System.currentTimeMillis();
            logger.info("默认推荐关联号源耗时：{}s", (endTime2 - startTime2) / 1000);
            if (!result.isEmpty())//结果集整理
                roms.addAll(super.parseResult(rim, JSONArray.parseArray(result.toJSONString(), RecomOutModel.class)));
            params.remove(1);//清除数据
            params.remove(0);
            int temp = end;
            start = temp;
            end = end * STEP_NUM;
        } while (roms.size() < rim.getNumber() && end <= 20000);//最多尝试20000次，根据当前值计算最多取4次结束循环，TODO 优化次数
        long endTime = System.currentTimeMillis();
        logger.info("默认推荐耗时：{}s", (endTime - startTime) / 1000);
        return roms.isEmpty() ? null : roms;
    }


    /**
     * 每次取5个医生去关联所有可预约的号源
     * 统计最近两个月预约最多的医生
     *
     * @param rim 医院，科室，医生条件
     * @return 返回数组
     */
    public String createMaxSql(RecomInModel rim) {
        //最多的5个医生
        StringBuilder sb = new StringBuilder();
        sb.append("select t1.org_id,\n" +
                "       t1.dept_code,\n" +
                "       t1.doctor_code,\n" +
                "       t2.res_date,\n" +
                "       t2.start_time,\n" +
                "       t2.end_time\n" +
                "  from (")
                .append(RecomHelper.buildSql(SqlHelper.getSql("doctorSortSql"), rim)).append(") t1 ")
                .append(RecomHelper.innerJionSourceNumSql());
        return RecomHelper.wrapNameSql(sb.toString());
    }


    /**
     * 仅是获取一段时间内最多预约次数的医生排序
     * 目前这个方法没用到（我们用直接关联号源表的方式）//TODO
     *
     * @param rim
     */
    public void queryRecordMax(RecomInModel rim) {
        String sqlStr = "select org_id,\n" +
                "                       org_id_source,\n" +
                "                       dept_code,\n" +
                "                       doctor_code,\n" +
                "                       count(*) counts\n" +
                "                  from rec_reservation_history\n" +
                "                 where res_date <= to_date(?, 'yyyy-mm-dd')\n" +
                "                   and res_date > to_date(?, 'yyyy-mm-dd')\n" +
                "                 group by org_id, org_id_source, dept_code, doctor_code\n" +
                "                 order by counts desc";

        String sqlStrNew = RecomHelper.buildSql(sqlStr, rim);

        JSONArray params = RecomHelper.buildParams(rim);
        params.add("2017-05-27");
        params.add("2017-04-27");

        JSONArray result = sql.queryAsJson(sqlStrNew, params.toArray());

        System.out.println();
        result.forEach(r -> {
            System.out.println(r.toString());
        });
    }
}
