package com.antin.rec.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 */
public class RecomOutModel extends DoctorIdenModel implements Comparable<RecomOutModel> {
    private String orgName;
    private String deptName;
    private String doctorName;
    private Date startTime;
    private Date endTime;
    private String process;//测试用，记录推荐过程

    private List<RecomOutModel> rom;

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @Override
    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<RecomOutModel> getRom() {
        return rom;
    }

    public void setRom(List<RecomOutModel> rom) {
        this.rom = rom;
    }

    @Override
    public int compareTo(RecomOutModel o) {
        int oi = this.orgId.compareTo(o.orgId);
        if (oi != 0)
            return oi;
        int dc = this.deptCode.compareTo(o.deptCode);
        if (dc != 0)
            return dc;
        return this.doctorCode.compareTo(o.doctorCode);
    }

    @Override
    public String toString() {
        return "RecomOutModel{" +
                "orgName='" + orgName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", rom=" + rom +
                '}';
    }
}
