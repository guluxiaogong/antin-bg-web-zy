package com.antin.rec.engine.defaults;

import com.antin.rec.engine.BaseRecom;

/**
 * Created by Administrator on 2017/8/14.
 * 默认推荐引擎基类
 */
public abstract class BaseDefaultsRecom extends BaseRecom {

    /**
     * 返回结果集
     *
     * @param list
     * @return
     */
/*    public ArrayList<RecomOutModel> parseResult(List<RecomOutModel> list) {

        ArrayList<RecomOutModel> distincts = new ArrayList<>();
        list.forEach(rm -> {
            if (!distincts.contains(rm)) {
                RecomOutModel recomOutModel = new RecomOutModel();
                BeanUtils.copyProperties(rm, recomOutModel);
                distincts.add(recomOutModel);
            }

        });
        distincts.forEach(r -> {
            if (r.getRom() == null)
                r.setRom(new ArrayList<>());
            list.forEach(l -> {
                if (r.equals(l)) {
                    //TODO 推荐时间段与历史记录相近
                    r.getRom().add(l);
                }
            });
        });
        return distincts;
    }*/
}
