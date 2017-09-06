package com.ggxueche.utils.log;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;

/**
 * Created by zhujigao on 17/3/27.
 */

public final class L {

    /**
     * print switch, true will print, false not print
     */
    public static boolean isPrint = true;

    private static String defaultTag = "Log";

    static {
        Logger.init(defaultTag);
    }

    private L() {}

    public static Printer t(String tag) {
        return Logger.t(tag);
    }

    public static void d(Object object) {
        if (isPrint && object != null) {
            Logger.d(object);
        }
    }

    public static void d(String message, Object... args) {
        if (isPrint && message != null) {
            Logger.d(message, args);
        }
    }

    public static void i(String message, Object... args) {
        if (isPrint && message != null) {
            Logger.i(message, args);
        }
    }

    public static void e(String message, Object... args) {
        if (isPrint && message != null) {
            Logger.e(message, args);
        }
    }

    public static void w(String message, Object... args) {
        if (isPrint && message != null) {
            Logger.w(message, args);
        }
    }

    public static void json(String json) {
        if (isPrint && json != null) {
            Logger.json(json);
        }
    }

    public static void xml(String xml) {
        if (isPrint && xml != null) {
            Logger.xml(xml);
        }
    }

}
