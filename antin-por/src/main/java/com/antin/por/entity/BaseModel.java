package com.antin.por.entity;

/**
 * Created by jichangjin on 2017/9/20.
 * hbase表模型
 */
public class BaseModel {
    protected String rowKey;
    protected String family;
    protected String qualifier;
    protected long timestamp;

    protected BaseModel() {

    }

    protected BaseModel(String rowKey) {
        this.rowKey = rowKey;
    }

    protected BaseModel(String rowKey, String family) {
        this.rowKey = rowKey;
        this.family = family;
    }

    protected BaseModel(String rowKey, String family, String qualifier) {
        this.rowKey = rowKey;
        this.family = family;
        this.qualifier = qualifier;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
