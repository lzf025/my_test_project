package com.li.food.view.inter;

import retrofit2.Call;

/**
 * 通用View
 * Created by likunyang on 2017/6/20.
 */

public interface ICurrencyView<T> extends IMvpView{
    void onBindView(T t);
    void onFaile(Call call, Throwable t, int code, String errorMsg);
}
