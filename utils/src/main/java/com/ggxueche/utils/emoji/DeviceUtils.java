package com.ggxueche.utils.emoji;

/**
 * 获取设备参数工具类
 * Created by fcx on 2017/3/3.
 */

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class DeviceUtils {

    public static String getPackageName(Context var0) {
        return var0.getPackageName();
    }

    public static Point getScreenSize(Context var0) {
        Point var1 = new Point();
        DisplayMetrics var2 = var0.getResources().getDisplayMetrics();
        var1.x = var2.widthPixels;
        var1.y = var2.heightPixels;
        return var1;
    }

    public static int dp2px(Context var0, float var1) {
        float var2 = var0.getResources().getDisplayMetrics().density;
        return (int)(var1 * var2 + 0.5F);
    }
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }


}

