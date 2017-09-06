package com.li.food.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;

public class GsonService {

    private static final Pattern REG_UNICODE = Pattern.compile("[0-9A-Fa-f]{4}");

    public static <T> T parseJsonToObject(String jsonString, Class<T> clazz) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, clazz);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println("解析json失败");
        }
        return t;

    }

    /**
     * jsonArray 转换成list
     * @param jsonArray
     * @param clazz
     */
    public static <T> ArrayList<T> parseJsonArrayToList(JSONArray jsonArray , Class<T> clazz){
        ArrayList<T> datas = new ArrayList<>();
        if (null != jsonArray){
            String jsonString = jsonArray.toString();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jarray = parser.parse(jsonString).getAsJsonArray();
            for(JsonElement obj : jarray ){
                datas.add(gson.fromJson(obj , clazz));
            }
            return datas;
        }
        return datas;

    }
    public static String parseObjectToJson(Object object) {
        try {
            Gson gson = new Gson();
            String jsonString = gson.toJson(object);
            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String unicode2String(String str) {
        StringBuilder unicodeStr = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char unicodeChar1 = str.charAt(i);
            if (unicodeChar1 == '\\' && i < len - 1) {
                char unicodeChar2 = str.charAt(++i);
                if (unicodeChar2 == 'u' && i <= len - 5) {
                    String tmp = str.substring(i + 1, i + 5);
                    Matcher matcher = REG_UNICODE.matcher(tmp);
                    if (matcher.find()) {
                        unicodeStr.append((char) Integer.parseInt(tmp, 16));
                        i = i + 4;
                    } else {
                        unicodeStr.append(unicodeChar1).append(unicodeChar2);
                    }
                } else {
                    unicodeStr.append(unicodeChar1).append(unicodeChar2);
                }
            } else {
                unicodeStr.append(unicodeChar1);
            }
        }
        return unicodeStr.toString();
    }
}
