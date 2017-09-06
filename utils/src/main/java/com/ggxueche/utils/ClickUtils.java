package com.ggxueche.utils;

/**
 * 防连续点击
 * Created by likunyang on 2017/7/8.
 */
public class ClickUtils {
    private final static int SPACE_TIME = 500;
    private final static int EXIT_TIME = 2000;
    private static long lastClickTime = 0;

    public synchronized static void isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > SPACE_TIME) {
            lastClickTime = currentTime;
        } else {
            lastClickTime = 0;
            return;
        }
    }

    public synchronized static boolean exitBy2Click() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime <= EXIT_TIME) {
            lastClickTime = 0;
            return true;
        } else {
            lastClickTime = currentTime;
            return false;
        }
    }
}
