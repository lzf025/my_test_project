package com.li.food.view.inter;

import com.li.food.bean.CategoryOneBean;

import java.util.List;

import retrofit2.Call;

/**
 * Created by lzf on 2017/7/20.
 */

public interface ICategoryView extends IMvpView {

    void addCatrgoty(List<CategoryOneBean>  data);
    void onFaile(Call call, Throwable t, int code, String errorMsg);

}
