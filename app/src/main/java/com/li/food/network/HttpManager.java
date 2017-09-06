package com.li.food.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by likunyang on 2017/5/16.
 */

public class HttpManager {
    private static final int TIMEOUT = 30;
    private volatile static HttpManager httpManager;
    public String HOST = "";
    private OkHttpClient.Builder builder;
    private Retrofit.Builder rBuilder;
    private Retrofit retrofit;

    public HttpManager(String hostName) {
        this.HOST = hostName;
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "text/html; charset=UTF-8")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .header("Cache-Control", String.format("public, max-age=%d", 60))
                        .build();
                return chain.proceed(request);
            }
        });


        rBuilder = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())      //此处顺序不能和上面对调，否则不能同时兼容普通字符串和Json格式字符串
                .baseUrl(getHost());
        retrofit = rBuilder.build();
    }

    public static int getTIMEOUT() {
        return TIMEOUT;
    }

    /**
     * 如果有不同的请求HOST可继承此类并Override
     *
     * @return
     */
    protected String getHost() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

    public void setBuilder(OkHttpClient.Builder builder) {
        this.builder = builder;
        this.rBuilder.client(builder.build());
        this.retrofit = rBuilder.build();
    }


    public Retrofit.Builder getrBuilder() {
        return rBuilder;
    }

    public void setrBuilder(Retrofit.Builder rBuilder) {
        this.rBuilder = rBuilder;
        this.retrofit = rBuilder.build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }
}

