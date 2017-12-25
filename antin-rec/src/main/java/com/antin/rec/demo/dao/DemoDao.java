package com.antin.rec.demo.dao;

import com.alibaba.fastjson.JSONArray;

/**
 * Created by Administrator on 2017/8/23.
 */
public interface DemoDao {
    JSONArray queryDemoUser();

    JSONArray queryOrg();

    JSONArray queryDeptByOrgId(String orgId);

    JSONArray queryDoctorByOrgIdAndDeptCode(String orgId, String deptCode);

    JSONArray queryUserHistory(String userId);
}
