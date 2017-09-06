package com.ggxueche.utils;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 将打开的Activity记录在栈里面，用于后期回退到某一固定页的需求
 * Created by GL on 2017/7/14.
 */

public class StorageActivityListUtil {
    private static Map<String, Activity> destoryMap = new HashMap<>();



    /**
     * 添加到销毁队列
     * @param activity 要销毁的activity
     */
    public static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName, activity);
    }

    /**
     * 删除指定的activity
     * @param activityName 要销毁的activity
     */
    public static void deleteDestoryActivity(String activityName) {
        destoryMap.remove(activityName);
    }

    /**
     * 销毁一系列Activity
     */
    public static void destoryActivity(String activityName) {
        Set<String> keySet = destoryMap.keySet();
        for (String key : keySet) {
            destoryMap.get(key).finish();
        }
    }

}
