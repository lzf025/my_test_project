package com.li.food.network;

import android.content.Context;

import com.li.food.network.interfaces.ErrorHandler;

import retrofit2.Call;


/**
 * 网络请求 全局错误监听
 */

public class ErrorHandlerImpl implements ErrorHandler {
    /*
    -1	失败	表示请求执行失败
    401	错误	表示未认证，遇到返回此信息，应调用注册设备接口，获取token
    402	错误	表示未登录，遇到返回此信息，应跳转到用户登录页面，提示用户登录
    403	错误	表示未授权，非法访问该接口，主要由于用户未登录（或者用户不能访问该接口，开发错误）
    406	错误	表示传输的参数非法
    417	错误	表示签名异常
    503	异常	服务器出现异常
    601	错误	APP版本过低
    */
    private static final int ERROR_1 = -1;
    private static final int ERROR_401 = 401;
    private static final int ERROR_402 = 402;
    private static final int ERROR_403 = 403;
    private static final int ERROR_406 = 406;
    private static final int ERROR_417 = 417;
    private static final int ERROR_502 = 502;
    private static final int ERROR_503 = 503;
    private static final int ERROR_601 = 601;
    public static String TAG = ErrorHandlerImpl.class.getSimpleName();
    private Context mContext;

    public ErrorHandlerImpl(Context ctx) {
        this.mContext = ctx;
    }

    @Override
    public void onFailure(Call call, int code, String msg) {
            //TODO 根据code 处理对应事件
    }
}
