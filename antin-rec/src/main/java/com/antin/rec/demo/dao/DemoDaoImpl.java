package com.antin.rec.demo.dao;

import com.alibaba.fastjson.JSONArray;
import com.antin.jdbc.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/8/23.
 */
@Repository
public class DemoDaoImpl implements DemoDao {
    @Autowired
    private Sql sql;

    /**
     * 查询用户
     *
     * @return
     */
    @Override
    public JSONArray queryDemoUser() {
        String sqlStr = "select * from (select rownum as row_num,t.* from rec_demo_user t) where row_num>0 and row_num<=20";
        return sql.queryAsJson(sqlStr, null);
    }

    /**
     * 查询机构
     *
     * @return
     */
    @Override
    public JSONArray queryOrg() {
        String sqlStr = "select * from rec_demo_org";
        return sql.queryAsJson(sqlStr, null);
    }

    /**
     * 查询科室
     *
     * @return
     */
    @Override
    public JSONArray queryDeptByOrgId(String orgId) {
        String sqlStr = "select * from rec_demo_dept where org_id =?";
        return sql.queryAsJson(sqlStr, new Object[]{orgId});
    }

    /**
     * 查询医生
     *
     * @return
     */
    @Override
    public JSONArray queryDoctorByOrgIdAndDeptCode(String orgId, String deptCode) {
        String sqlStr = "select * from rec_demo_doctor where org_id =? and dept_code=?";
        return sql.queryAsJson(sqlStr, new Object[]{orgId, deptCode});
    }

    /**
     * 查询用户历史预约记录
     *
     * @return
     */
    @Override
    public JSONArray queryUserHistory(String userId) {
        String sqlStr = "select t1.citizen_id,\n" +
                "       t1.res_date,\n" +
                "       t2.name       as org_name,\n" +
                "       t3.name       as dept_name,\n" +
                "       t4.name       as doctor_name,\n" +
                "       t1.start_time,\n" +
                "       t1.end_time\n" +
                "  from rec_reservation_history t1\n" +
                "  left join rec_demo_org t2\n" +
                "    on t1.org_id = t2.org_id\n" +
                "  left join rec_demo_dept t3\n" +
                "    on t1.org_id = t3.org_id\n" +
                "   and t1.dept_code = t3.dept_code\n" +
                "  left join rec_demo_doctor t4\n" +
                "    on t1.org_id = t4.org_id\n" +
                "   and t1.doctor_code = t4.doctor_code\n" +
                " where citizen_id = ? order by res_date desc ";
        return sql.queryAsJson(sqlStr, new Object[]{userId});
    }
}
//    select t1.citizen_id,
//        t1.res_date,
//        t2.name       as org_name,
//        t3.name       as dept_name,
//        t4.name       as doctor_name,
//        t1.start_time,
//        t1.end_time
//        from rec_reservation_history t1
//        left join rec_demo_org t2
//        on t1.org_id = t2.org_id
//        left join rec_demo_dept t3
//        on t1.org_id = t3.org_id
//        and t1.dept_code = t3.dept_code
//        left join rec_demo_doctor t4
//        on t1.org_id = t4.org_id
//        and t1.doctor_code = t4.doctor_code
//        where citizen_id = '980498b9-e624-4582-bb14-d8da56ee3694'
//        order by res_date desc

