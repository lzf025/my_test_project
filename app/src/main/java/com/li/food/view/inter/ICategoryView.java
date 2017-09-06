package com.li.food.view.inter;

import com.li.food.bean.CategoryBean;
import com.li.food.bean.FoodDetailBean;

import java.util.List;

import retrofit2.Call;

/**
 * Created by lzf on 2017/7/20.
 */

public interface ICategoryView extends IMvpView {

    void addCatrgoty(List<CategoryBean>  data);
    void onFaile(Call call, Throwable t, int code, String errorMsg);

}
