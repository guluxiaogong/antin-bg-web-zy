package com.antin.por.entity;

/**
 * Created by Administrator on 2017/7/6.
 * hbase入参模型
 */
public class HBaseModel extends BaseModel {
    private String table;
    private boolean category;//是否分类
    private int latelyDay;//过滤近期几天的数据；为小于等0时为全部数据


    public HBaseModel() {
        super();
    }

    public HBaseModel(String table) {
        this.table = table;
    }

    public HBaseModel(String table, String rowKey) {
        super(rowKey);
        this.table = table;
    }

    public HBaseModel(String table, String rowKey, String family) {
        super(rowKey, family);
        this.table = table;
    }

    public HBaseModel(String table, String rowKey, String family, String qualifier) {
        super(rowKey, family, qualifier);
        this.table = table;
    }


    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public boolean getCategory() {
        return category;
    }

    public void setCategory(boolean category) {
        this.category = category;
    }

    public int getLatelyDay() {
        return latelyDay;
    }

    public void setLatelyDay(int latelyDay) {
        this.latelyDay = latelyDay;
    }
}
