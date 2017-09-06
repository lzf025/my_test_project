package com.li.food.model.bean;

/**
 * Created by lzf on 2017/9/5.
 */

public class DateBean {

    private String date;
    private String week;

    public DateBean(String date, String week) {
        this.date = date;
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
