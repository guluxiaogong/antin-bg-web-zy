package com.antin.rec.entity;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/16.
 */
public class RecReservationHistoryModel /*implements Comparable<RecReservationHistoryModel>*/ {
    private String citizenId;
    private Date resDate;
    private String orgId;//号源的orgId
    private String deptCode;
    private String doctorCode;
    private Date startTime;
    private Date endTime;
    private double pLongitude;
    private double pLatitude;
    private double hLongitude;
    private double hLatitude;

    public RecReservationHistoryModel() {

    }

    public RecReservationHistoryModel(String orgId, String deptCode, String doctorCode) {
        this.orgId = orgId;
        this.deptCode = deptCode;
        this.doctorCode = doctorCode;
    }
//    @Override
//    public int compareTo(RecReservationHistoryModel o) {
//        return (int) (this.getResDate().getTime() - o.getResDate().getTime());
//    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public Date getResDate() {
        return resDate;
    }

    public void setResDate(Date resDate) {
        this.resDate = resDate;
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

    public double getpLongitude() {
        return pLongitude;
    }

    public void setpLongitude(double pLongitude) {
        this.pLongitude = pLongitude;
    }

    public double getpLatitude() {
        return pLatitude;
    }

    public void setpLatitude(double pLatitude) {
        this.pLatitude = pLatitude;
    }

    public double gethLongitude() {
        return hLongitude;
    }

    public void sethLongitude(double hLongitude) {
        this.hLongitude = hLongitude;
    }

    public double gethLatitude() {
        return hLatitude;
    }

    public void sethLatitude(double hLatitude) {
        this.hLatitude = hLatitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecReservationHistoryModel that = (RecReservationHistoryModel) o;

        if (Double.compare(that.pLongitude, pLongitude) != 0) return false;
        if (Double.compare(that.pLatitude, pLatitude) != 0) return false;
        if (Double.compare(that.hLongitude, hLongitude) != 0) return false;
        if (Double.compare(that.hLatitude, hLatitude) != 0) return false;
        if (citizenId != null ? !citizenId.equals(that.citizenId) : that.citizenId != null) return false;
        if (resDate != null ? !resDate.equals(that.resDate) : that.resDate != null) return false;
        if (orgId != null ? !orgId.equals(that.orgId) : that.orgId != null) return false;
        if (deptCode != null ? !deptCode.equals(that.deptCode) : that.deptCode != null) return false;
        if (doctorCode != null ? !doctorCode.equals(that.doctorCode) : that.doctorCode != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        return endTime != null ? endTime.equals(that.endTime) : that.endTime == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = citizenId != null ? citizenId.hashCode() : 0;
        result = 31 * result + (resDate != null ? resDate.hashCode() : 0);
        result = 31 * result + (orgId != null ? orgId.hashCode() : 0);
        result = 31 * result + (deptCode != null ? deptCode.hashCode() : 0);
        result = 31 * result + (doctorCode != null ? doctorCode.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        temp = Double.doubleToLongBits(pLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pLatitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(hLongitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(hLatitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
//    create table rec_reservation_history as
//    select t1.citizen_id,
//        t1.res_date,
//        t2.code        as org_id,
//        t1.org_id      as org_id_source,
//        t1.dept_code,
//        t1.doctor_code,
//        t1.start_time,
//        end_time
//        from rec_urp_reservation_history t1
//        left join rec_urp_org t2
//        on t1.org_id = t2.org_id
//        where t1.citizen_id is not null
//        and t2.code is not null
//        and t1.dept_code is not null
//        and t1.doctor_code is not null



