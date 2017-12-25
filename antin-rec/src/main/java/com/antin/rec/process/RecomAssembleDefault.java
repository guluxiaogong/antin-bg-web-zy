package com.antin.rec.process;

import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 * 默认推荐流程
 */
public class RecomAssembleDefault extends RecomAssemble {


    public List<RecomOutModel> recomEngine(RecomInModel rim) {
        return super.recomEngine(rim);
    }
}
