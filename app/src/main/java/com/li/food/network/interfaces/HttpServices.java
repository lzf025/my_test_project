package com.li.food.network.interfaces;

import com.li.food.bean.BaseBean;
import com.li.food.network.NetConst;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 请求接口
 * Created by lzf on 2017/8/17.
 */

public interface HttpServices {
    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("{url}")
    Call<BaseBean> newApi(@Path(value = "url", encoded = true) String path, @Body String paramsJson);

    @POST("/sys/upload/uploadImg/{type}")
    Call<BaseBean> uploadImage(@Path("type") String type, @Body RequestBody Body);

    @POST("class")
    Call<BaseBean> getFoodClass();

    @GET("byclass")
    Call<BaseBean> getClassById(@Query("classid") String classid , @Query("start") String start , @Query("num") String num );


    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String url);

}
