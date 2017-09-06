package com.li.food.model;

import com.ggxueche.utils.NetworkUtil;
import com.li.food.global.MyApplication;
import com.li.food.model.inter.IBaseModel;
import com.li.food.network.HttpUtils;
import com.li.food.network.interfaces.HttpServices;

public class BaseModel<T> implements IBaseModel {
    //当做唯一标识
    public final String TAG = this.getClass().getName() + '@' + Integer.toHexString(this.hashCode());
    protected T mPresenter;

    public BaseModel(T presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public boolean isNetWorkAvailable() {
        if (NetworkUtil.isNetworkAvaliable(MyApplication.mContext))
            return true;
        return false;
    }

    @Override
    public void cancelNetRequest() {
        HttpUtils.getInstance().cancelNetRequest(TAG);
    }

    //获取api call
    public HttpServices getApiService(){
        return HttpUtils.getInstance().getHttpServices();
    }

}
