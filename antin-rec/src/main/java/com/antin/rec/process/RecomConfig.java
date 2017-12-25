package com.antin.rec.process;

import com.antin.rec.engine.comme.BaseCommeRecom;
import com.antin.rec.engine.comme.impl.DoctorProfessionCommeRecom;
import com.antin.rec.engine.defaults.BaseDefaultsRecom;
import com.antin.rec.engine.defaults.impl.RecordMaxDefaultsRecom;
import com.antin.rec.engine.prendre.BasePrendreRecom;
import com.antin.rec.engine.prendre.impl.RecentePrendreRecom;

/**
 * Created by Administrator on 2017/8/22.
 * 配置推荐引擎的推荐策略=》外观模式类
 */
public class RecomConfig {
    //默认推荐
    private BaseDefaultsRecom baseDefaultsRecom = new RecordMaxDefaultsRecom();
    //预约记录推荐
    private BasePrendreRecom basePrendreRecom = new RecentePrendreRecom();
    //相似推荐
    private BaseCommeRecom baseCommeRecom = new DoctorProfessionCommeRecom();

    public BaseDefaultsRecom getBaseDefaultsRecom() {
        return baseDefaultsRecom;
    }

    public void setBaseDefaultsRecom(BaseDefaultsRecom baseDefaultsRecom) {
        this.baseDefaultsRecom = baseDefaultsRecom;
    }

    public BasePrendreRecom getBasePrendreRecom() {
        return basePrendreRecom;
    }

    public void setBasePrendreRecom(BasePrendreRecom basePrendreRecom) {
        this.basePrendreRecom = basePrendreRecom;
    }

    public BaseCommeRecom getBaseCommeRecom() {
        return baseCommeRecom;
    }

    public void setBaseCommeRecom(BaseCommeRecom baseCommeRecom) {
        this.baseCommeRecom = baseCommeRecom;
    }

}
