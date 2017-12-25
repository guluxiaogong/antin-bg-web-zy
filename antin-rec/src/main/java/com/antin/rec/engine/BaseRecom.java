package com.antin.rec.engine;

import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;
import com.antin.rec.util.helper.RecomHelper;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 * 推荐引擎基础类
 */
public abstract class BaseRecom {
    /**
     * 推荐引擎逻辑实现
     *
     * @param rim 参数模型中封装： 医院机构代码->科室编号->医生编号
     * @return 返回值：医生号源时间段列表
     */
    public abstract List<RecomOutModel> recomEngine(RecomInModel rim);


    /**
     * 返回结果集
     *
     * @param list 结果集
     * @return 最终结果
     */
    public List<RecomOutModel> parseResult(RecomInModel rim, List<RecomOutModel> list) {

        ArrayList<RecomOutModel> distincts = new ArrayList<>();
        list.forEach(rm -> {
            if (!distincts.contains(rm)) {
                RecomOutModel recomOutModel = new RecomOutModel();
                BeanUtils.copyProperties(rm, recomOutModel);
                distincts.add(recomOutModel);
            }

        });

        //TODO 对于没有预约记录时间选择如佑处理？
        for (RecomOutModel r : distincts) {
            Long minTime = 24 * 60 * 60 * 1000L;
            Date startTime = rim.getStartTime(),
                    endTime = rim.getEndTime();
            if (r.getRom() == null)
                r.setRom(new ArrayList<>());
            for (RecomOutModel l : list) {
                if (r.equals(l)) {
                    if (startTime != null && endTime != null) {
                        //推荐时间段与历史记录相近
                        long currTime = RecomHelper.calculateTime.calculate(startTime, endTime, l.getStartTime(), l.getEndTime());
                        if (minTime > currTime) {
                            minTime = currTime;
                            r.setStartTime(l.getStartTime());
                            r.setEndTime(l.getEndTime());
                        }
                    }
                    r.getRom().add(l);
                }
            }
        }

        return distincts.isEmpty() ? null : distincts;
    }
}
