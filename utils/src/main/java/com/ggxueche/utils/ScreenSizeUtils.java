package com.ggxueche.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * create by Weavey
 * on date 2016-01-06
 * TODO 单例模式 获取屏幕宽高的帮助类
 */

public class ScreenSizeUtils {

    private static ScreenSizeUtils instance = null;
    private final Context mContext;
    private WindowManager manager;
    private DisplayMetrics dm;
    private int screenWidth, screenHeigth;

    private ScreenSizeUtils(Context mContext) {
        this.mContext = mContext;
        manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);

        screenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
        screenHeigth = dm.heightPixels;

    }

    public static ScreenSizeUtils getInstance(Context mContext) {

        if (instance == null) {
            synchronized (ScreenSizeUtils.class) {

                if (instance == null)
                    instance = new ScreenSizeUtils(mContext);
            }
        }
        return instance;
    }

    //获取屏幕宽度
    public int getScreenWidth() {

        return screenWidth;
    }

    //获取屏幕高度
    public int getScreenHeight() {

        return screenHeigth;
    }

    /**
     * 宽高比 16：9  宽为屏幕宽
     *
     * @return
     */
    public int getW16H9Scale() {
        float h = screenWidth * 9 / 16.0f;
        return (int) (h + 0.5);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = mContext.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

}
