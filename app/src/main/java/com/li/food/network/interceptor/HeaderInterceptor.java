package com.li.food.network.interceptor;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 请求拦截器
 * Created by lzf on 2017/5/17.
 */

public class HeaderInterceptor implements Interceptor {
    public static String TAG = HeaderInterceptor.class.getSimpleName();
    private Map<String, String> headers;
    private Context mContext;

    public HeaderInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        setUpRequestHeaders();
        Request.Builder builder = chain.request().newBuilder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                builder.removeHeader(key);
                builder.addHeader(key, value);
            }
        }
        Request request = builder.build();
        return chain.proceed(request);
    }

    private void setUpRequestHeaders() {
        if (headers == null) {
            headers = new HashMap<>();
            headers.put("appkey", "e5d26cca9f5f27c4");
        }
    }
}
