package com.antin.rec.engine.comme.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.antin.rec.entity.RecomInModel;

/**
 * Created by Administrator on 2017/8/15.
 */
public interface CommeDao {

    // JSONArray queryDoctorsByPatientId(String id);

    JSONObject queryCommeDoctors(RecomInModel rim);

    JSONArray queryHospitalByLatAndLng(double latMin, double latMax, double lngMin, double lngMax);
}
