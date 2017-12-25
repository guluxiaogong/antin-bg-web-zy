package com.antin.por.entity;

/**
 * Created by jichangjin on 2017/9/20.
 * 画像标签返回值模型
 */
public class PortraitModel<T> extends BaseModel {
    private String qualifierName;//列名（类别名）
    private boolean hasChildren; //是否有子类别
    private TagDictModel dict;//权重等其他信息

    private T value;//通常人口属性为字符串//健康标签为json对象

    public String getQualifierName() {
        return qualifierName;
    }

    public void setQualifierName(String qualifierName) {
        this.qualifierName = qualifierName;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public TagDictModel getDict() {
        return dict;
    }

    public void setDict(TagDictModel dict) {
        this.dict = dict;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
