package com.li.food.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单分类
 * Created by lzf on 2017/8/5.
 */

public class CategoryBean implements Serializable {

    private String classid;
    private String name;
    private String parentid;
    private List<Category3Bean> list;

    public List<Category3Bean> getList() {
        return list;
    }

    public void setList(List<Category3Bean> list) {
        this.list = list;
    }


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


}
