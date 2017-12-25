package com.antin.rec.entity;


import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */
public class RecomInModel extends DoctorIdenModel {
    private String personId;
    private double longitude;//经度
    private double latitude;//纬度
    private double distance = 5000;//距离
    private Date startTime;
    private Date endTime;
    private int number = 3;//获取多省个推荐医生
    private List<RecReservationHistoryModel> rrhm;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<RecReservationHistoryModel> getRrhm() {
        return rrhm;
    }

    public void setRrhm(List<RecReservationHistoryModel> rrhm) {
        this.rrhm = rrhm;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
