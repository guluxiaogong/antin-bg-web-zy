package com.antin.rec.util.json;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TreeMap;


/**
 * Created by Administrator on 2017/8/15 0015.
 */
public class JSONUtil {
    public static TreeMap<String, String> getValue(String jsonstr, ArrayList<String> field) {
        if (jsonstr == null || jsonstr == "" || field == null) {
            return null;
        }
        JSONObject obj = null;
        try {
            obj = new JSONObject(jsonstr);
            TreeMap<String, String> treeMap = new TreeMap<>();
            for (String str : field) {
                treeMap.put(str, obj.getString(str));
            }
            if (treeMap != null && treeMap.size() > 0) {
                return treeMap;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();//解析json字符串异常
            return null;
        }
    }
}

