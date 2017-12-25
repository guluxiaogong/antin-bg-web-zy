package com.antin.rec.process;

import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 * 推荐引擎流程组装
 */
public abstract class RecomAssemble {

    protected RecomConfig recomConfig = new RecomConfig();

    public void setRecomConfig(RecomConfig recomConfig) {
        this.recomConfig = recomConfig;
    }

    /**
     * 默认推荐流程
     *
     * @param rim
     * @return
     */
    public List<RecomOutModel> recomEngine(RecomInModel rim) {

        List<RecomOutModel> rom = new ArrayList<>();
        /*
         * 用户打开网站进行默认推荐
         * （用户未登录）
         */
        if (StringUtils.isEmpty(rim.getPersonId())) {
            List<RecomOutModel> romDefault = recomConfig.getBaseDefaultsRecom().recomEngine(rim); //默认推荐
            if (isEnough(rim, rom, romDefault))
                return rom;
        } else {//用户登录
            List<RecomOutModel> romPren = recomConfig.getBasePrendreRecom().recomEngine(rim);//预约记录推荐
            if (isEnough(rim, rom, romPren))
                return rom;
        }

        List<RecomOutModel> romComme = recomConfig.getBaseCommeRecom().recomEngine(rim); //相似推荐
        if (isEnough(rim, rom, romComme))
            return rom;
        /*
         * 条件回退默认推荐
         */
        return rollbackConditin(rim, rom);
    }

    /*
    * 条件回退默认推荐
    */
    private List<RecomOutModel> rollbackConditin(RecomInModel rim, List<RecomOutModel> rom) {

        if (rim.getDoctorCode() != null) {
            List<RecomOutModel> romDoc = recomConfig.getBaseDefaultsRecom().recomEngine(rim); //【机构】【科室】【医生】默认推荐
            if (isEnough(rim, rom, romDoc))
                return rom;
            rim.setDoctorCode(null);
        }
        if (rim.getDeptCode() != null) {
            List<RecomOutModel> romDept = recomConfig.getBaseDefaultsRecom().recomEngine(rim); //【机构】【科室】默认推荐
            if (isEnough(rim, rom, romDept))
                return rom;
            rim.setDeptCode(null);
        }
        if (rim.getOrgId() != null) {
            List<RecomOutModel> romOrg = recomConfig.getBaseDefaultsRecom().recomEngine(rim); //【机构】条件默认推荐
            if (isEnough(rim, rom, romOrg))
                return rom;
            rim.setOrgId(null);
        }
        List<RecomOutModel> romEm = recomConfig.getBaseDefaultsRecom().recomEngine(rim); //无条件默认推荐;
        if (romEm != null)
            rom.addAll(romEm);
        return rom; //无条件默认推荐;
    }

    /*
     * 结果集数量是否足够
     */
    private boolean isEnough(RecomInModel rim, List<RecomOutModel> rom, List<RecomOutModel> r) {
        if (r != null) {
            rom.addAll(r);
            if (r.size() > rim.getNumber())
                return true;
            rim.setNumber(rim.getNumber() - r.size());
        }
        return false;
    }
}
