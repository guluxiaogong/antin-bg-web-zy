package com.antin.por.action;

import com.antin.por.entity.HBaseModel;
import com.antin.por.service.HBaseService;
import com.antin.por.entity.PortraitModel;
import com.antin.por.entity.UserModel;
import com.antin.por.helper.CacheHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jichangjin on 2017/9/21.
 * portrait2画像采用新的hbase表结构
 */
@Controller
@RequestMapping("/hbase")
public class HBaseAction {

    @Autowired
    private HBaseService hBaseService;

    @ResponseBody
    @RequestMapping("findByRowKeyTest")
    public List<PortraitModel> findByRowKeyTest(HBaseModel hBaseModel, String rowKeyTest) throws IOException {
        if (StringUtils.isEmpty(hBaseModel.getRowKey()) && StringUtils.isEmpty(rowKeyTest))
            return null;

        if (rowKeyTest.length() == 36)
            hBaseModel.setRowKey(rowKeyTest);

        // HBaseModel hBaseModel = new HBaseModel();
        hBaseModel.setTable("antin:ehr_r");
        // hBaseModel.setRowKey(rowKey);

        List<PortraitModel> list = hBaseService.findByRowKey(hBaseModel);
        return list;
    }

    @ResponseBody
    @RequestMapping("findByRowKey")
    public List<PortraitModel> findByRowKey(HBaseModel hBaseModel) throws IOException {
        if (StringUtils.isEmpty(hBaseModel.getRowKey()))
            return null;
        // HBaseModel hBaseModel = new HBaseModel();
        hBaseModel.setTable("antin:ehr_r");
        // hBaseModel.setRowKey(rowKey);

        List<PortraitModel> list = hBaseService.findByRowKey(hBaseModel);
        return list;
    }

    /**
     * 获取检验、药品项目列表
     *
     * @param XManId 病人卡号
     *               columnFamily 类别，   检验：c   药品：p  检查：e
     *               startDate 开始时间
     * @return List<Map<String, Object>>
     */
    @ResponseBody
    @RequestMapping("getEhrXmanGuide")
    public List<Map<String, Object>> getEhrXmanGuide(String columnFamily, String XManId, String startDate) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        if (!StringUtils.isEmpty(startDate)) {
            list = hBaseService.getEhrXmanGuide(columnFamily, XManId, startDate);
        } else {
            list = hBaseService.filterByName("antin:sehr_xman_ehr_guide", columnFamily, "XMAN_ID", XManId);
        }
        return list;
    }

    /**
     * 获取检验、药品指导注意事项
     *
     * @param commCode 项目编码
     * @return List<Map<String, Object>>
     */
    @ResponseBody
    @RequestMapping("getGuideAnnount")
    public List<Map<String, Object>> getGuideAnnount(String commCode) throws IOException {
        String tableName = "antin:comm_guide";
        List<Map<String, Object>> list = hBaseService.filterByName(tableName, "i", "COMM_CODE", commCode);
        return list;
    }

    /**
     * 获取遗传病细表
     *
     * @param rowId count
     * @return List<Map<String, Object>>
     */
    @ResponseBody
    @RequestMapping("getGeneticDetail")
    public List<Map<String, Object>> getGeneticDetail(String rowId, String count) throws Exception {
        List<Map<String, Object>> list = hBaseService.getGeneticDetail(rowId, count);
        return list;
    }

    /**
     * 获取遗传病主表
     *
     * @param sickTipCode
     * @return List<Map<String, Object>>
     */
    @ResponseBody
    @RequestMapping("getGeneticMaster")
    public List<Map<String, Object>> filterByName(String sickTipCode) throws IOException {
        String tableName = "antin:genetic_dict_master";
        List<Map<String, Object>> list = hBaseService.filterByName(tableName, "i", "GENETIC_CODE", sickTipCode);
        return list;
    }

    /**
     * 加载测试用户
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/listDemoUser")
    public List<UserModel> listDemoUser() {
        return CacheHelper.userList;
    }
}
