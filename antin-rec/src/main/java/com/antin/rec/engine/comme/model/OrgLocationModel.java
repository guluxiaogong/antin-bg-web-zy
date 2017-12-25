package com.antin.rec.engine.comme.model;

import com.antin.rec.entity.DoctorIdenModel;

/**
 * Created by Administrator on 2017/8/15.
 * create table rec_pre_org_location as select code,g_longitude as longitude,g_latitude as latitude   from rec_urp_sehr_org where g_longitude is not null and g_latitude is not null
 */
public class OrgLocationModel extends DoctorIdenModel implements Comparable<OrgLocationModel> {
    //private String code;//医院编码
    private double longitude;//经度
    private double latitude;//纬度
    private Double distance;//与圆点距离
    private String codes;

    @Override
    public int compareTo(OrgLocationModel o) {
        if (distance != null || o.getDistance() != null) {
            double len = this.distance - o.getDistance();
            if (len == 0)
                return getOrgId().compareTo(o.getOrgId());
            return (int) len;
        }
        return 0;
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrgLocationModel that = (OrgLocationModel) o;

        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (distance != null ? !distance.equals(that.distance) : that.distance != null) return false;
        return codes != null ? codes.equals(that.codes) : that.codes == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (codes != null ? codes.hashCode() : 0);
        return result;
    }
}
