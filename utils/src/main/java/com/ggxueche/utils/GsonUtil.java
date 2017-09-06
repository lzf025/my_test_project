package com.ggxueche.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

/**
 * Created by likunyang on 2017/5/19.
 */

public class GsonUtil {

    public static String Map2Json(Map<String, Object> map) {
        Gson gson = new Gson();
        String json = gson.toJson(map, new TypeToken<Map<String, Object>>() {
        }.getType());
        return json;
    }

}
