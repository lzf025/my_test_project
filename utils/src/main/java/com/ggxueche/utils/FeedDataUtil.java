package com.ggxueche.utils;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FeedDataUtil {

	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;

	private static final String ONE_SECOND_AGO = "刚刚";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";
	private static String mPostTimeMonth,mPostTimeYear;

    public static String feedCreatTime(long postTime){
        long nowTime = System.currentTimeMillis();
        mPostTimeMonth = DateUtil.timestampStr12(postTime);
        mPostTimeYear = DateUtil.timestampStr2(postTime);
        Date postDate = new Date(postTime);
        Date nowDate = new Date(nowTime);
        return format(postDate,nowDate);
    }

	public static void main() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
		Date date = format.parse("2017-1-3 23:59:59");
        Logger.d("SimpleDateFormat", format(date, new Date()));
        Logger.i(format(date, new Date()));
	}

	/**
	 * 社区时间显示规则 一分钟内 刚刚 一小时内 X分钟前 24小时内 X小时前 1-2天 昨天，前天 3天以上 月 - 日 例：2月15 即 2-15
	 * 跨年份 年 - 月 - 日 例：2016年2月15 即 2016-2-15
	 */

	public static String format(Date postDate, Date nowDate) {
		int day = daysOfTwo(nowDate, postDate);
        long delta = nowDate.getTime() - postDate.getTime();
        if (delta < 1L * ONE_MINUTE) {
            //long seconds = toSeconds(delta);
            return ONE_SECOND_AGO;
        }
        if (delta < 60L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }else if (day != 0) {
			if (day == 1) {
				return "昨天";
			} else if (day > 1 && day < 3) {
				return "前天";
			} else if (day < 365){
				return mPostTimeMonth;
			}else {
                return mPostTimeYear;
            }
		}
		return null;

	}
	public static final int daysOfTwo(Date early, Date late) {

		Calendar calst = Calendar.getInstance();
		Calendar caled = Calendar.getInstance();
		calst.setTime(early);
		caled.setTime(late);
		// 设置时间为0时
		calst.set(Calendar.HOUR_OF_DAY, 0);
		calst.set(Calendar.MINUTE, 0);
		calst.set(Calendar.SECOND, 0);
		caled.set(Calendar.HOUR_OF_DAY, 0);
		caled.set(Calendar.MINUTE, 0);
		caled.set(Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;

		return Math.abs(days);
	}

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	private static long toYears(long date) {
		return toMonths(date) / 365L;
	}

}
