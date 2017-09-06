package com.ggxueche.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class PackageUtils {

    public static String getAgent(SoftReference<Context> softReference) {
        String agent = "";
        try {
            String ua = System.getProperty("http.agent");
            String packageName = softReference.get().getPackageName();
            PackageInfo info = softReference.get().getPackageManager().getPackageInfo(packageName, 0);
            agent = ua + ", " + packageName + "/" + info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return agent;
    }

    /**
     * 获取应用版本号 如：2.6.4
     *
     * @param softReference
     * @return
     */
    public static String getVersionName(SoftReference<Context> softReference) {
        String version = "1.0.0";
        PackageManager packageManager = softReference.get().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    softReference.get().getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取应用版本号 如：20
     *
     * @param softReference
     * @return
     */
    public static int getVersionCode(SoftReference<Context> softReference) {
        int version = 1000;
        PackageManager packageManager = softReference.get().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    softReference.get().getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取包名
     *
     * @param softReference
     * @return
     */
    public static String getPackageName(SoftReference<Context> softReference) {
        String packageName = "com.ecar";
        PackageManager packageManager = softReference.get().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    softReference.get().getPackageName(), 0);
            packageName = packageInfo.packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageName;
    }

    /*
     * require    <uses-permission android:name="android.permission.GET_TASKS" />
     */
    public static boolean isMyPackageRunningOnTop(SoftReference<Context> softReference) {
        ActivityManager am = (ActivityManager) softReference.get().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return false;
        }
        List<ActivityManager.RunningTaskInfo> infos = am.getRunningTasks(1);
        if (infos != null && !infos.isEmpty()) {
            ActivityManager.RunningTaskInfo info = infos.get(0);
            ComponentName componentName = info.topActivity;
            if (componentName != null
                    && componentName.getPackageName().equals(softReference.get().getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断手机已安装某程序的方法：
     *
     * @param softReference
     * @param packageName
     * @return 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
     */
    public static boolean isAvilible(SoftReference<Context> softReference, String packageName) {
        final PackageManager packageManager = softReference.get().getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    /**
     * 判断某一个类是否存在任务栈里面
     *
     * @return
     */
    public static boolean isExsitMianActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        ComponentName cmpName = intent.resolveActivity(context.getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }
}
