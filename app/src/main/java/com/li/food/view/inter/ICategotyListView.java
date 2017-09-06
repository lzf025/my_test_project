package com.li.food.view.inter;

import com.li.food.bean.FoodDetailBean;

import java.util.List;

/**
 * Created by lzf on 2017/8/11.
 */

public interface ICategotyListView extends IMvpView{
    void addFoodDetail(List<FoodDetailBean> data);
}
