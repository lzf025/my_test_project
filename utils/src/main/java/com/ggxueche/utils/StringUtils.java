package com.ggxueche.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;

import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by likunyang on 2017/6/12.
 */

public class StringUtils {
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_TELPHONE = "^1\\d{10}$";

    /**
     * 测算距离
     *
     * @param distance 距离
     * @return
     */
    public static String formatDistance(Double distance) {
        if (distance < 1000) {
            return String.format("%.0fm", distance);
        } else if (distance >= 1000) {
            double a = distance / 1000;//8889.1 / 1000 = 8.8891
            return String.format("%.1fkm", a);
        } else {
            throw new IllegalArgumentException("The distance can't limit zero");
        }
    }

    /**
     * 关键字变色
     *
     * @param color        要变的颜色
     * @param totalString  完整字符串
     * @param targetString 要变色的字符串 可传多个
     * @return
     */
    public static SpannableString changeKeyWordColor(int color, String totalString, String... targetString) {

        return changeKeyWordColor(color, null, totalString, targetString);
    }

    /**
     * 关键字变色
     *
     * @param color        要变的颜色
     * @param totalString  完整字符串
     * @param textSize     字体比例
     * @param targetString 要变色的字符串 可传多个
     * @return
     */
    public static SpannableString changeKeyWordColor(int color, float[] textSize, String totalString, String... targetString) {
        SpannableString spannableString = new SpannableString(totalString);
        if (textSize != null && textSize.length != targetString.length)
            throw new IllegalArgumentException("textSize length should equals to targetString length");
        for (int i = 0; i < targetString.length; i++) {
            String str = targetString[i];
            int start = totalString.indexOf(str);
            int end = start + str.length();
            if (color != 0) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
                spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            if (textSize != null) {
                RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(textSize[i]);
                spannableString.setSpan(relativeSizeSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return spannableString;
    }

    /**
     * 文本加下划线
     *
     * @param totalString  完整的字符串
     * @param targetString 要加下划线的字符串 可传多个
     * @return
     */
    public static SpannableString addUnderLineToKeyWord(String totalString, String... targetString) {
        SpannableString spannableString = new SpannableString(totalString);
        for (int i = 0; i < targetString.length; i++) {
            UnderlineSpan colorSpan = new UnderlineSpan();
            String str = targetString[i];
            int start = totalString.indexOf(str);
            int end = start + str.length();
            spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_TELPHONE, mobile);
    }

    /**
     * 替换手机号
     * @param target
     * @return
     */
    public static String replacePhone(String target) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < target.length(); i++) {
            if (i < 3 || i > 6) {
                sb.append(target.charAt(i));
            } else {
                sb.append("*");
            }
        }
        return sb.toString();
    }

    /**
     * 替换名字
     * @param target
     * @return
     */
    public static String replaceName(String target) {
        StringBuilder sb = new StringBuilder();
        int length = target.length();
        String sub = target.substring(length - 1, length);
        for (int i = 0; i < length - 1; i++) {
            sb.append("*");
        }
        sb.append(sub);
        return sb.toString();
    }

    /**
     * 替换身份证号
     * @param target
     * @return
     */
    public static String replaceCardCode(String target) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < target.length(); i++) {
            if (i < 1 || i > target.length() - 2) {
                sb.append(target.charAt(i));
            } else {
                sb.append("*");
            }
        }
        return sb.toString();
    }
}
