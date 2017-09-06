package com.li.food.bean;

import java.io.Serializable;

/**
 * 菜单子分类
 * Created by lzf on 2017/8/5.
 */

public class Category3Bean implements Serializable {

    private String classid;
    private String name;
    private String parentid;

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Override
    public String toString() {
        return "Category3Bean{" +
                "classid='" + classid + '\'' +
                ", name='" + name + '\'' +
                ", parentid='" + parentid + '\'' +
                '}';
    }
}
