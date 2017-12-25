package com.antin.por.entity;

/**
 * Created by jichangjin on 2017/9/21.
 * 标签字典模型
 */
public class TagDictModel {
    private String code;
    private String name;
    private int weight;//权重
    private String color = "#0BE386";//默认颜色

    public TagDictModel() {

    }

    public TagDictModel(String code, String name, int weight) {
        this.code = code;
        this.name = name;
        this.weight = weight;
    }

    public TagDictModel(String code, String name, int weight, String color) {
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.color = color;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }
}
