package com.antin.rec.engine.comme.impl;

import com.antin.rec.engine.comme.BaseCommeRecom;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 * 基于医生专业相似度的【相似医生推荐引擎】
 */
public class DoctorProfessionCommeRecom extends BaseCommeRecom {

    @Override
    public List<RecomOutModel> recomEngine(RecomInModel rim) {
        return super.recomEngine(rim);
    }

}
