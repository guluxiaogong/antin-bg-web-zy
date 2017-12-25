package com.antin.por.handle;

import com.alibaba.fastjson.JSONArray;
import com.antin.por.entity.HBaseModel;
import com.antin.por.entity.PortraitModel;
import com.antin.por.entity.TagDictModel;
import com.antin.por.entity.TagModel;
import com.antin.por.helper.CacheHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */
public class DefalutHandle implements Handle {
    protected HBaseModel hm;
    protected Result result;

    @Override
    public Handle loadData(Table table, HBaseModel hm) throws IOException {
        this.hm = hm;
        Get get = new Get(Bytes.toBytes(hm.getRowKey()));
        if (!StringUtils.isEmpty(hm.getFamily()))
            get.addFamily(Bytes.toBytes(hm.getFamily()));
        this.result = table.get(get);
        return this;
    }

    @Override
    public List<PortraitModel> handleResult() {
        List<PortraitModel> qualifiers = new ArrayList<>();
        for (Cell cell : result.rawCells()) {
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            if (StringUtils.isBlank(value))
                continue;
            String family = Bytes.toString(CellUtil.cloneFamily(cell)),
                    qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            List<TagModel> json = isJson(value);
            if (json != null) {
                PortraitModel<List<TagModel>> portraitModel = new PortraitModel<>();
                portraitModel.setFamily(family);
                portraitModel.setQualifier(qualifier);
                List<TagModel> tagModels = JSONArray.parseArray(value, TagModel.class);
                if (hm.getCategory()) {//对有parentCode的标签分类
                    String parentCode = tagModels.get(0).getParentCode();
                    if (!StringUtils.isEmpty(parentCode)) {
                        portraitModel.setHasChildren(true);
                        portraitModel.setQualifierName(CacheHelper.classMap.get(parentCode));
                    }
                }

                if (hm.getLatelyDay() > 0) {//是否过滤
                    long time = 24 * 60 * 60 * 1000;
                    List<TagModel> latelyModels = new ArrayList<TagModel>();
                    for (TagModel t : tagModels) {
                        Date endTime = t.getEndTime();
                        if (endTime != null && (new Date().getTime() - t.getEndTime().getTime()) / time < hm.getLatelyDay())
                            latelyModels.add(t);
                    }
                    if (latelyModels.isEmpty())
                        continue;
                    portraitModel.setValue(latelyModels);
                } else {
                    portraitModel.setValue(tagModels);
                }

                portraitModel.setDict(CacheHelper.tagDictMap.get(qualifier));
                qualifiers.add(portraitModel);
            } else {
                PortraitModel<String> portraitModel = new PortraitModel<>();
                portraitModel.setFamily(family);
                portraitModel.setQualifier(qualifier);
                portraitModel.setValue(value);
                portraitModel.setDict(new TagDictModel());
                qualifiers.add(portraitModel);
            }
        }
        return qualifiers;
    }

    private List<TagModel> isJson(String json) {
        try {
            return JSONArray.parseArray(json, TagModel.class);
        } catch (Exception e) {
            //log.error("bad json: " + json);
            return null;
        }
    }
}
