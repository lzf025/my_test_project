package com.ggxueche.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * Created by yaofc on 2017/6/12.
 */

public class RegExpUtil {

    static boolean flag = false;

    public static boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    /**
     * 验证手机号码 ：以1开头长度为11位
     *
     *
     * @param cellphone
     * @return
     */
    public static boolean checkMobliephone(String cellphone) {
        String regex = "^(1[0-9])\\d{9}$";
        return check(cellphone, regex);
    }
}
