package com.ggxueche.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtil {
    public static String sf1 = "yyyy年MM月dd日";
    public static String sf2 = "yyyy-MM-dd";
    public static String sf3 = "MM月dd日";
    public static String sf4 = "yyyy年MM月dd日 HH:mm";
    public static String sf5 = "MM月dd日";
    public static String sf6 = "yyyy-MM-dd HH:mm";
    public static String sf7 = "yyyy-MM-dd HH:mm:ss";
    public static String sf8 = "yyyy/MM/dd HH:mm";
    public static String sf9 = "yyyy.MM.dd";
    public static String sf10 = "MM.dd";
    public static String sf11 = "yyyy.MM.dd HH:mm";
    public static String sf12 = "MM-dd";
    public static String sf13 = "HH";
    public static String sf14 = "yyyy";
    public static String sf15 = "mm";
    public static String sf16 = "yyyy年MM月dd日";
    public static String sf17 = "yyyy-M-d";

    public static String timestampStr(@NonNull Long timestamp, @NonNull String formattStr) {
        if (null == timestamp) {
            timestamp = 0l;
        }
        if (TextUtils.isEmpty(formattStr)) {
            throw new IllegalArgumentException("the second argument formattStr is null ");
        }
        Date timestamp1 = new Date(timestamp);
        return timestampToStr(formattStr, timestamp1);
    }

    public static String timestampStr(Long timestamp) {
        Date timestamp1 = new Date(timestamp);
        return timestampToStr(sf1, timestamp1);
    }

    public static String timestampStr8(Long timestamp) {
        Date timestamp1 = new Date(timestamp);
        return timestampToStr(sf8, timestamp1);
    }

    public static String timestampStr4(Long timestamp) {
        Date timestamp1 = new Date(timestamp);
        return timestampToStr(sf4, timestamp1);
    }
    public static String timestampStr16(Long timestamp) {
        Date timestamp1 = new Date(timestamp);
        return timestampToStr(sf16, timestamp1);
    }

    public static String timestampStr5(Long timestamp) {
        Date timestamp1 = new Date(timestamp);
        return timestampToStr(sf5, timestamp1);
    }

    public static String timestampStr1(Long timestamp) {
        Date timestamp1 = new Date(timestamp);
        String date = getWeekOfDate(timestamp1);
        return timestampToStr(sf1, timestamp1) + date;
    }

    public static String timestampStr2(Long timestamp) {
        Date timestamp1 = new Date(timestamp);
        return timestampToStr(sf2, timestamp1);
    }

    public static String timestampStr6(Long timestamp) {
        Date timestamp1 = new Date(timestamp);
        return timestampToStr(sf6, timestamp1);
    }

    public static String timestampStr7(Long timestamp) {
        Date timestamp1 = new Date(timestamp);
        return timestampToStr(sf7, timestamp1);
    }

    public static String timestampStr3(Long timestamp) {
        Date timestamp1 = new Date(timestamp);
        String date = getWeekOfDate(timestamp1);
        return timestampToStr(sf3, timestamp1) + "[" + date + "]";
    }

    public static String timestampStr9(Long timestamp) {
        try {
            if (null == timestamp) {
                return "";
            }
            Date timestamp1 = new Date(timestamp);
            return timestampToStr(sf9, timestamp1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String timestampStr10(Long timestamp) {
        try {
            if (null == timestamp) {
                return "";
            }
            Date timestamp1 = new Date(timestamp);
            return timestampToStr(sf10, timestamp1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String timestampStr11(Long timestamp) {
        try {
            if (null == timestamp) {
                return "";
            }
            Date timestamp1 = new Date(timestamp);
            return timestampToStr(sf11, timestamp1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String timestampStr12(Long timestamp) {
        try {
            if (null == timestamp) {
                return "";
            }
            Date timestamp1 = new Date(timestamp);
            return timestampToStr(sf12, timestamp1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String timestampStr13(Long timestamp) {
        try {
            if (null == timestamp) {
                return "";
            }
            Date timestamp1 = new Date(timestamp);
            return timestampToStr(sf13, timestamp1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String timestampStr14(Long timestamp) {
        try {
            if (null == timestamp) {
                return "";
            }
            Date timestamp1 = new Date(timestamp);
            return timestampToStr(sf14, timestamp1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String timestampStr15(Long timestamp) {
        try {
            if (null == timestamp) {
                return "";
            }
            Date timestamp1 = new Date(timestamp);
            return timestampToStr(sf15, timestamp1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String timestampToStr(String formate, Date timestamp) {
        DateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(timestamp);
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static int getTeachAge(String beginTime) {
        int year = 0;
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        long nowTimeStamp = System.currentTimeMillis();
        try {
            String todayData = formatDate.format(nowTimeStamp);
            double difMon = compareDate(beginTime, todayData, 2);
            year = (int) difMon;
        } catch (Exception e) {
        }
        return year;

    }

    /**
     * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式
     * @param date2 被比较的时间 为空(null)则为当前时间
     * @param stype 返回值类型 0为多少天，1为多少个月，2为多少年
     * @return
     */
    public static double compareDate(String date1, String date2, int stype) {
        int n = 0;

        String[] u = {"天", "月", "年"};
        String formatStyle = (stype == 1 ? "yyyy-MM" : "yyyy-MM-dd");
        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(date1));
            c2.setTime(df.parse(date2));
        } catch (Exception e3) {
            Logger.i("wrong occured");
        }
        while (!c1.after(c2)) {
            // 循环对比，直到相等，n 就是所要的结果
            n++;
            if (stype == 1) {
                c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
            } else {
                c1.add(Calendar.DATE, 1); // 比较天数，日期+1
            }
        }

        n = n - 1;

        if (stype == 2) {
            //如果不能够一年等于1年
            if (n <= 365) {
                n = 1;
            } else {
                n = n / 365;
            }
        }

        Logger.i(date1 + " -- " + date2 + " 相差多少" + u[stype] + ":"
                + n);
        return n;
    }

    /**
     * 得到当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        return simple.format(date);
    }

}
