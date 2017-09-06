package com.li.food.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lzf on 2017/8/5.
 */

public class FoodContentBean implements Serializable {

    List<FoodDetailBean> list;

    public List<FoodDetailBean> getList() {
        return list;
    }

    public void setList(List<FoodDetailBean> list) {
        this.list = list;
    }
}
