package com.antin.rec.engine.defaults.impl;

import com.antin.rec.engine.defaults.BaseDefaultsRecom;
import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/14.
 * 基于预约记录医生评分最高的【默认推荐引擎】
 */
public class DoctorNoterDefaultsRecom extends BaseDefaultsRecom {

    @Override
    public List<RecomOutModel> recomEngine(RecomInModel rim) {
        return null;

    }
}
