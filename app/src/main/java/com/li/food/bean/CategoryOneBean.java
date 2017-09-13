package com.li.food.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单一级分类
 * Created by lzf on 2017/8/5.
 */

public class CategoryOneBean implements Serializable {

    private String classid;
    private String name;
    private String parentid;
    private List<CategoryTwoBean> list;

    public List<CategoryTwoBean> getList() {
        return list;
    }

    public void setList(List<CategoryTwoBean> list) {
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
