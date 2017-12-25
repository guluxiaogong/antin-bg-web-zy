package com.antin.rec.process;

import com.antin.rec.entity.RecomInModel;
import com.antin.rec.entity.RecomOutModel;
import org.springframework.util.StringUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */
public class RecomAssembleTest extends RecomAssemble {


    /**
     * 默认推荐流程
     *
     * @param rim
     * @return
     */
    public List<RecomOutModel> recomEngine(RecomInModel rim) {
        double startTime, endTime;

        List<RecomOutModel> rom = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        /*
         * 用户打开网站进行默认推荐
         * （用户未登录）
         */
        if (StringUtils.isEmpty(rim.getPersonId())) {
            startTime = System.currentTimeMillis();
            sb.append("开始默认推荐=>");
            List<RecomOutModel> romDefault = recomConfig.getBaseDefaultsRecom().recomEngine(rim); //默认推荐
            endTime = System.currentTimeMillis();
            if (isEnough(rim, rom, romDefault)) {
                sb.append(String.format("默认推荐%s条完成，用时%s s！", rom.size(), useTime(startTime, endTime)));
                RecomOutModel rm = rom.get(0);
                rm.setProcess(sb.toString());
                return rom;
            }
            sb.append(String.format("默认推荐数据为%s条，用时%s s=>", rom.size(), useTime(startTime, endTime)));
        } else {//用户登录
            sb.append("开始预约记录推荐=>");
            startTime = System.currentTimeMillis();
            List<RecomOutModel> romPren = recomConfig.getBasePrendreRecom().recomEngine(rim);//预约记录推荐
            endTime = System.currentTimeMillis();
            if (isEnough(rim, rom, romPren)) {
                sb.append(String.format("预约记录推荐%s条完成，用时%s s！", rom.size(), useTime(startTime, endTime)));
                RecomOutModel rm = rom.get(0);
                rm.setProcess(sb.toString());
                return rom;
            }
            sb.append(String.format("预约记录推荐数据为%s条，用时%s s=>", rom.size(), useTime(startTime, endTime)));
        }
        sb.append("开始相似推荐=>");
        startTime = System.currentTimeMillis();
        List<RecomOutModel> romComme = recomConfig.getBaseCommeRecom().recomEngine(rim); //相似推荐
        endTime = System.currentTimeMillis();
        if (isEnough(rim, rom, romComme)) {
            sb.append(String.format("相似推荐%s条完成，用时%s s！", romComme.size(), useTime(startTime, endTime)));
            RecomOutModel rm = rom.get(0);
            rm.setProcess(sb.toString());
            return rom;
        }
        sb.append(String.format("相似推荐数据为%s条，用时%s s=>", (romComme == null ? 0 : romComme.size()), useTime(startTime, endTime)));
        /*
         * 条件回退默认推荐
         */
        return rollbackConditin(rim, rom, sb);
    }

    /*
    * 条件回退默认推荐
    */
    private List<RecomOutModel> rollbackConditin(RecomInModel rim, List<RecomOutModel> rom, StringBuilder sb) {
        double startTime, endTime;
        if (rim.getDoctorCode() != null) {
            sb.append("开始【机构】【科室】【医生】条件下默认推荐=>");
            startTime = System.currentTimeMillis();
            List<RecomOutModel> romDoc = recomConfig.getBaseDefaultsRecom().recomEngine(rim); //【机构】【科室】【医生】默认推荐
            endTime = System.currentTimeMillis();
            if (isEnough(rim, rom, romDoc)) {
                sb.append(String.format("【机构】【科室】【医生】条件下默认推荐%s条完成，用时%s s！", romDoc.size(), useTime(startTime, endTime)));
                RecomOutModel rm = rom.get(0);
                rm.setProcess(sb.toString());
                return rom;
            }
            rim.setDoctorCode(null);
            sb.append(String.format("【机构】【科室】【医生】条件下默认推荐数据为%s条，用时%s s=>", (romDoc == null ? 0 : romDoc.size()), useTime(startTime, endTime)));
        }
        if (rim.getDeptCode() != null) {
            sb.append("开始【机构】【科室】条件下默认推荐=>");
            startTime = System.currentTimeMillis();
            List<RecomOutModel> romDept = recomConfig.getBaseDefaultsRecom().recomEngine(rim); //【机构】【科室】默认推荐
            endTime = System.currentTimeMillis();
            if (isEnough(rim, rom, romDept)) {
                sb.append(String.format("【机构】【科室】条件下默认推荐%s条完成，用时%s s！", romDept.size(), useTime(startTime, endTime)));
                RecomOutModel rm = rom.get(0);
                rm.setProcess(sb.toString());
                return rom;
            }
            rim.setDeptCode(null);
            sb.append(String.format("【机构】【科室】条件下默认推荐数据为%s条，用时%s s=>", (romDept == null ? 0 : romDept.size()), useTime(startTime, endTime)));
        }
        if (rim.getOrgId() != null) {
            sb.append("开始【机构】条件下默认推荐=>");
            startTime = System.currentTimeMillis();
            List<RecomOutModel> romOrg = recomConfig.getBaseDefaultsRecom().recomEngine(rim); //【机构】条件默认推荐
            endTime = System.currentTimeMillis();
            if (isEnough(rim, rom, romOrg)) {
                sb.append(String.format("【机构】条件下默认推荐%s条完成，用时%s s！", romOrg.size(), useTime(startTime, endTime)));
                RecomOutModel rm = rom.get(0);
                rm.setProcess(sb.toString());
                return rom;
            }
            rim.setOrgId(null);
            sb.append(String.format("【机构】条件下默认推荐数据为%s条，用时%s s=>", (romOrg == null ? 0 : romOrg.size()), useTime(startTime, endTime)));
        }
        sb.append("开始无条件下默认推荐=>");
        startTime = System.currentTimeMillis();
        List<RecomOutModel> romEm = recomConfig.getBaseDefaultsRecom().recomEngine(rim); //无条件默认推荐;
        endTime = System.currentTimeMillis();
        if (romEm != null) {
            rom.addAll(romEm);
            sb.append(String.format("无条件下默认推荐%s条完成，用时%s s！", romEm.size(), useTime(startTime, endTime)));
        }
        if (!rom.isEmpty()) {
            RecomOutModel rm = rom.get(0);
            rm.setProcess(sb.toString());
        }

        return rom;
    }

    private String useTime(double startTime, double endTime) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format((endTime - startTime) / 1000);
    }

    /*
     * 结果集数量是否足够
     */
    private boolean isEnough(RecomInModel rim, List<RecomOutModel> rom, List<RecomOutModel> r) {
        if (r != null) {
            rom.addAll(r);
            if (r.size() >= rim.getNumber())
                return true;
            rim.setNumber(rim.getNumber() - r.size());
        }
        return false;
    }
}
