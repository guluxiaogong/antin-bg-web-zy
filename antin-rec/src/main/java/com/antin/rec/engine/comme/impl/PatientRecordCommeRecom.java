package com.antin.rec.engine.comme.impl;

import com.antin.rec.engine.comme.BaseCommeRecom;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 * 基于病人历史就诊相关的【相似医生推荐引擎】
 */
public class PatientRecordCommeRecom extends BaseCommeRecom {

    //TODO 目前这个推荐和DoctorProfessionCommeRecom一样
    @Override
    public List<RecomOutModel> recomEngine(RecomInModel rim) {
        //rm.setId(id);
        return super.recomEngine(rim);

    }
}
