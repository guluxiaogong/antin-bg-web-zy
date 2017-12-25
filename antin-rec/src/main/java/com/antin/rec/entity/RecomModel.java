package com.antin.rec.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/14.
 * 推荐引擎参数与返回值模型
 */
public class RecomModel extends DoctorIdenModel {
    //private String id;

    private Map<String, List<String>> timeRange;//号源，<日期，时间段集合>

    public RecomModel() {
    }

    public RecomModel(Map<String, List<String>> timeRange) {
        this.timeRange = timeRange;
    }

    public RecomModel(String orgId, String deptCode) {
        super(orgId, deptCode);
    }

    public RecomModel(String orgId, String deptCode, String doctorCode) {
        super(orgId, deptCode, doctorCode);
    }

    public RecomModel(String orgId, String deptCode, String doctorCode, Map<String, List<String>> timeRange) {
        super(orgId, deptCode, doctorCode);
        this.timeRange = timeRange;
    }

    public Map<String, List<String>> getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(Map<String, List<String>> timeRange) {
        this.timeRange = timeRange;
    }
}



