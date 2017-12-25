package com.antin.por.helper;

import com.antin.por.entity.TagDictModel;
import com.antin.por.entity.UserModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jichangjin on 2017/9/20.
 *
 */
public class CacheHelper {

    /**
     * 标签类别
     */
    public static Map<String, String> classMap = new HashMap<String, String>();

    static {
        classMap.put("chronic", "慢性病类型");
        classMap.put("ageGroup", "年龄段");

    }

    /**
     * 标签字典
     */
    public static Map<String, TagDictModel> tagDictMap = new HashMap<String, TagDictModel>();

//    static {
//        tagDictMap.put("diabetes", new TagDictModel("E14.900", "糖尿病", 5, "#6DE335"));
//        tagDictMap.put("hypertension", new TagDictModel("I10.x00", "高血压", 6, "#CFE32E"));
//        tagDictMap.put("aerzihaimozheng", new TagDictModel("F03.x01", "阿尔茨海默症", 7, "#E39CCB"));
//        tagDictMap.put("pregnant", new TagDictModel("pregnant", "孕产妇", 7, "#E39CCB"));
//        tagDictMap.put("babyMF", new TagDictModel("babyM", "宝妈/宝爸", 8, "#35E0E3"));
//        //tagDictMap.put("babyF", new TagDictModel("babyF", "宝爸", 9, "#1DA1E3"));
//        tagDictMap.put("ageGroup", new TagDictModel("ageGroup", "年龄段", 9, "#E3D344"));
//    }
static {
    tagDictMap.put("DIABETES", new TagDictModel("E14.900", "糖尿病", 5, "#6DE335"));
    tagDictMap.put("HYPERTENSION", new TagDictModel("I10.x00", "高血压", 6, "#CFE32E"));
    tagDictMap.put("AERZIHAIMOZHENG", new TagDictModel("F03.x01", "阿尔茨海默症", 7, "#E39CCB"));
    tagDictMap.put("PREGNANT", new TagDictModel("pregnant", "孕产妇", 7, "#E39CCB"));
    tagDictMap.put("BABY_MF", new TagDictModel("babyM", "宝妈/宝爸", 8, "#35E0E3"));
    //tagDictMap.put("babyF", new TagDictModel("babyF", "宝爸", 9, "#1DA1E3"));
    tagDictMap.put("AGE_GROUP", new TagDictModel("ageGroup", "年龄段", 9, "#E3D344"));
}

    /**
     * 测试用户数据
     */
    public static final List<UserModel> userList = new ArrayList<>();

    static {

        //基本信息，年龄段
        userList.add(new UserModel("00008844-b4d2-4351-96c3-f724d1117449", "郑玉芳", "女"));
        userList.add(new UserModel("0000cb86-89a8-4e22-ae70-6dc95f41444e", "陈枝官", "男"));

        //孕产妇
        userList.add(new UserModel("fy_3395072", "陈小小", "女"));

        //高血压
        userList.add(new UserModel("*350211194101184522*", "林万全", "男"));
        userList.add(new UserModel("*350211194101184522*", "蔡敏敏", "女"));

        //糖尿病
        userList.add(new UserModel("3502034410073021", "刘小能", "男"));
        userList.add(new UserModel("350203440924301", "李佳丽", "女"));

        //宝爸
        userList.add(new UserModel("510722198705067057", "侯勇好", "男"));
        userList.add(new UserModel("410482198502063810", "叶保国", "男"));
        //宝妈
        userList.add(new UserModel("350623198311126625", "苏妮", "女"));
        userList.add(new UserModel("37250119820329202X", "张桐雨", "女"));
    }
}
