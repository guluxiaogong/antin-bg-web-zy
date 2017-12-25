package com.antin.rec.demo.action;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.antin.helper.SpringContextHolder;
import com.antin.rec.demo.dao.DemoDao;
import com.antin.rec.util.helper.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/8/23.
 */
@Controller
@RequestMapping("recom")
public class DemoIndex {
    @Autowired
    private DemoDao demoDao;

    @RequestMapping("home")
    public String home() {
        return "/home/html/index";
    }

    @RequestMapping("index")
    public String recomIndex() {
        return "/recom/html/index";
    }

    /**
     * 查询用户
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryDemoUser")
    public JSONArray queryDemoUser() {
        return demoDao.queryDemoUser();
    }

    /**
     * 查询机构
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryOrg")
    public JSONArray queryOrg() {
        return demoDao.queryOrg();
    }

    /**
     * 查询科室
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryDeptByOrgId")
    public JSONArray queryDeptByOrgId(String orgId) {
        if (StringUtils.isEmpty(orgId))
            return null;
        return demoDao.queryDeptByOrgId(orgId);
    }

    /**
     * 查询医生
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryDoctorByOrgIdAndDeptCode")
    public JSONArray queryDoctorByOrgIdAndDeptCode(String orgId, String deptCode) {
        if (StringUtils.isEmpty(orgId) || StringUtils.isEmpty(deptCode))
            return null;
        return demoDao.queryDoctorByOrgIdAndDeptCode(orgId, deptCode);
    }

    /**
     * 查询用户历史预约记录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryUserHistory")
    public JSONArray queryUserHistory(String userId) {
        if (StringUtils.isEmpty(userId))
            return null;
        return demoDao.queryUserHistory(userId);
    }

    @ResponseBody
    @RequestMapping("/refresh")
    public String refreshConfig() {
        StringBuffer sb = new StringBuffer();
        try {
            SqlHelper sqlHelper = SpringContextHolder.getBean("sqlHelper");
            sqlHelper.refreshConfig();
            sb.append("refresh success!");
        } catch (Exception e) {
            sb.append(e.getMessage());
            e.printStackTrace();
        }
        return sb.toString();
    }

}
