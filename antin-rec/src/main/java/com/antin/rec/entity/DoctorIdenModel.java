package com.antin.rec.entity;

/**
 * Created by Administrator on 2017/8/16.
 * 医生标识模型
 */
public class DoctorIdenModel {
    protected String orgId;//医院id
    protected String deptCode;//科室编号
    protected String doctorCode;//医生编号

    public DoctorIdenModel() {

    }

    public DoctorIdenModel(String orgId) {
        this.orgId = orgId;
    }

    public DoctorIdenModel(String orgId, String deptCode) {
        this.orgId = orgId;
        this.deptCode = deptCode;
    }

    public DoctorIdenModel(String orgId, String deptCode, String doctorCode) {
        this.orgId = orgId;
        this.deptCode = deptCode;
        this.doctorCode = doctorCode;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctorIdenModel that = (DoctorIdenModel) o;

        if (orgId != null ? !orgId.equals(that.orgId) : that.orgId != null) return false;
        if (deptCode != null ? !deptCode.equals(that.deptCode) : that.deptCode != null) return false;
        return doctorCode != null ? doctorCode.equals(that.doctorCode) : that.doctorCode == null;

    }

    @Override
    public int hashCode() {
        int result = orgId != null ? orgId.hashCode() : 0;
        result = 31 * result + (deptCode != null ? deptCode.hashCode() : 0);
        result = 31 * result + (doctorCode != null ? doctorCode.hashCode() : 0);
        return result;
    }


}
