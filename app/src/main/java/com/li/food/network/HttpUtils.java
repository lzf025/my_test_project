package com.li.food.network;

import android.support.annotation.NonNull;

import com.li.food.bean.BaseBean;
import com.li.food.network.interfaces.ErrorHandler;
import com.li.food.network.interfaces.HttpServices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * gears接口
 * Created by lzf on 2017/5/16.
 */

public class HttpUtils {
    private static HttpUtils httpUtils = null;
    private HttpServices httpServices;
    private HttpManager httpManager;
    private ErrorHandler errorHandler;
    private static Map<Call, String> callMap;

    private HttpUtils() {
        httpManager = new HttpManager(NetConst.RELEASE_SERVER ? NetConst.NEW_SERVER_NAME : NetConst.NEW_TEST_SERVER_NAME);
        callMap = new HashMap<>();
    }

    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    public HttpManager getHttpManager() {

        return httpManager;
    }

    public void addInterceptor(@NonNull Interceptor interceptor) {
        OkHttpClient.Builder builder = getHttpManager().getBuilder();
        builder.addInterceptor(interceptor);
        getHttpManager().setBuilder(builder);
    }

    public void addNetworkInterceptor(Interceptor interceptor) {
        OkHttpClient.Builder builder = getHttpManager().getBuilder();
        builder.addNetworkInterceptor(interceptor);
        getHttpManager().setBuilder(builder);
    }

    public <T> T getServices(@NonNull Class<T> clazz) {
        if (clazz == null) throw new IllegalArgumentException("获取服务接口，未设置服务class");
        return getHttpManager().getRetrofit().create(clazz);
    }

    public HttpServices getHttpServices() {
        httpServices = getServices(HttpServices.class);
        return httpServices;
    }

    /**
     * @return 回去全局回调监听
     */
    public ErrorHandler getErrorHandler() {
        if (errorHandler == null) throw new IllegalArgumentException("没有设置全局错误监听");
        return errorHandler;
    }

    /**
     * 设置全局回调
     *
     * @param errorHandler
     */
    public void setErrorHandler(@NonNull ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void removeCall(Call call) {
        if (HttpUtils.getInstance().getCallMap().containsKey(call)) {
            HttpUtils.getInstance().getCallMap().remove(call);
        }
    }

    /**
     * @return 返回应用内所有正在请求中的map集合
     */
    public static Map<Call, String> getCallMap() {
        return callMap;
    }

    /**
     * 普通请求
     *
     * @param urlPath    请求地址
     * @param paramsJson 请求参数
     * @param callback   请求结果回调
     * @param className
     */
    public void getGuaGuaCall(@NonNull String urlPath, String paramsJson, Callback callback, String className) {
        Call<BaseBean> baseBeanCall = getHttpServices().newApi(urlPath, paramsJson);
        baseBeanCall.enqueue(callback);
        callMap.put(baseBeanCall, className);
    }

    public void getCallFood(Callback callback, String className) {
        Call<BaseBean> baseBeanCall = getHttpServices().getFoodClass();
        baseBeanCall.enqueue(callback);
        callMap.put(baseBeanCall, className);
    }

    //极速聚合api通用发起请求
    public void getEnqueueFood(Call<BaseBean> call, Callback callback, String className) {
        call.enqueue(callback);
        callMap.put(call, className);
    }

    /**
     * @param className 当前类里所有请求
     * @Date 2017/8/11
     * 取消请求
     */
    public void cancelNetRequest(String className) {
        for (Map.Entry entry : callMap.entrySet()) {
            if (className.equals(entry.getValue())) {
                Call<BaseBean> call = (Call<BaseBean>) entry.getKey();
                boolean isCanceled = call.isCanceled();
                if (!isCanceled) {
                    call.cancel();
                }
            }
        }
    }

}
