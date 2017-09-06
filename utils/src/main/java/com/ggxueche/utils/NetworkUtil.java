package com.ggxueche.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关工具类
 * Created by yaofc on 2017/5/19.
 */

public class NetworkUtil {

    public static final int NETWORK_TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE;
    public static final int NETWORK_TYPE_WIFI = ConnectivityManager.TYPE_WIFI;

    /**
     * 检查网络是否可用
     *
     * @param ctx
     * @return
     */
    public static boolean isNetworkAvaliable(Context ctx) {
        if (ctx == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            final NetworkInfo net = connectivityManager.getActiveNetworkInfo();
            return net != null && net.isAvailable() && net.isConnected();
        } else {
            return false;
        }
    }

    public static int getNetworkType(Context con) {
        ConnectivityManager cm = (ConnectivityManager) con
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return NETWORK_TYPE_MOBILE;
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isAvailable()) {
            if (netinfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_TYPE_WIFI;
            } else {
                return NETWORK_TYPE_MOBILE;
            }
        }
        return NETWORK_TYPE_MOBILE;
    }

}
