package com.ggxueche.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class VerifyUtil {
    /**
     * Returns true if the string is null or 0-length.
     * 
     * @param str
     *            the string to be examined
     * @return true if str is null or zero length or String null
     */

    public static boolean isEmpty(Object str) {
        return str == null || str.toString().length() == 0 || "".equals(str.toString()) || "null".equals(str.toString());
    }
    
    // 安装apk
    public static void installApk(Activity activity, File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }
    public static long lastClickTime;

    /**
     * 禁止连续点击
     * @return
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 1000) {
            return true;
        }   
        lastClickTime = time;
        return false;
    }
    public static String getAndroidReleaseVersion() {
        String version = "";
        try {
          version = "" + android.os.Build.VERSION.RELEASE;
        } catch (NumberFormatException e) {
        }
        return version;
     }

}
