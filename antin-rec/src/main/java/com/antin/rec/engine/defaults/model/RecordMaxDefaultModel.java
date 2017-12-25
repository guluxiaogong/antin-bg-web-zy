package com.antin.rec.engine.defaults.model;

import com.antin.rec.entity.DoctorIdenModel;

/**
 * Created by Administrator on 2017/8/17.
 */
public class RecordMaxDefaultModel extends DoctorIdenModel {
    private int counts;

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }
}
