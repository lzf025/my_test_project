package com.li.food.network.interceptor;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lzf on 2017/8/5.
 */
public class BaseInterceptor implements Interceptor {
    public static String TAG = HeaderInterceptor.class.getSimpleName();
    private Context mContext;

    public BaseInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalUrl = original.url();
        HttpUrl url = originalUrl.newBuilder()
                .addQueryParameter("appkey","e5d26cca9f5f27c4").build();
        Request.Builder requestBuilder = original.newBuilder().url(url);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }

}