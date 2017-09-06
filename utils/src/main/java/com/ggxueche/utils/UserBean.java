package com.ggxueche.utils;

/**
 * 该类在添加@好友文字变色用,该类只用于@好友变色工具类SpanUtils
 * Created by yaofc on 2017/3/18.
 */
public class UserBean {
    private int id;

    private String name;

    public UserBean() {
    }

    public UserBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
