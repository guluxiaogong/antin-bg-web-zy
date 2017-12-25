package com.antin.rec.engine.defaults.emum;

/**
 * Created by Administrator on 2017/8/17.
 */
public enum RecordMaxDefaultEmum {

    OrgId("org_id"), DeptCode("dept_code"), DoctorCode("doctor_code"), Counts("counts"), Where("where"), And("and"),Equal("="),Question("?");

    RecordMaxDefaultEmum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
