package com.li.food.network;

import android.accounts.NetworkErrorException;

import com.ggxueche.utils.TUtil;
import com.ggxueche.utils.ToastUtil;
import com.ggxueche.utils.dialog.DialogManager;
import com.google.gson.Gson;
import com.li.food.bean.BaseBean;
import com.li.food.global.MyApplication;

import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 请求回调
 * Created by lzf on 2017/8/8.
 */

public abstract class RequestCallBack<T, E> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.body() != null && response.body() instanceof BaseBean) {
            BaseBean body = (BaseBean) response.body();
            if (body.getStatus() != null && NetConst.SUCESS_CODE.equals(body.getStatus())) {
                HttpUtils.getInstance().removeCall(call);
                E e = new Gson().fromJson(body.getResult(), (Type) TUtil.getT(this, 1));
                onSucess(e);

            } else {
                //如果不是api成功码
                onRequestFailure(call, new NetworkErrorException(body.getMsg()), Integer.valueOf(body.getStatus()), body.getMsg());
                //返回全局监听hander
                HttpUtils.getInstance().getErrorHandler().onFailure(call, Integer.valueOf(body.getStatus()), body.getMsg());
            }
        } else if (response.errorBody() != null) {
            onRequestFailure(call, new NetworkErrorException(response.raw().message()), response.raw().code(), response.raw().message());
            HttpUtils.getInstance().getErrorHandler().onFailure(call, response.raw().code(), response.raw().message());
        }
        DialogManager.getInstance().dismissDialog();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        //TODO 测试使用提示
        if (call.isCanceled()){
            ToastUtil.showToast(MyApplication.mContext, "请求取消了");
        } else {
            if (t instanceof SocketTimeoutException) {
                ToastUtil.showToast(MyApplication.mContext, "请求超时");
            }
        }

        DialogManager.getInstance().dismissDialog();
        HttpUtils.getInstance().removeCall(call);
        onRequestFailure(call, t, -1, t.getMessage());
    }

    protected abstract void onSucess(E data);

    protected abstract void onRequestFailure(Call<T> call, Throwable t, int code, String errorMsg);
}
