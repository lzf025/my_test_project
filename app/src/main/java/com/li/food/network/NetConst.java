package com.li.food.network;

/**
 * 请求地址
 * Created by likunyang on 2017/8/4.
 */

public class NetConst {
    public static final boolean RELEASE_SERVER = false;

    //极速聚合菜谱 api
    public static final String NEW_SERVER_NAME = "http://api.jisuapi.com/recipe/";

    //测试服务器
    public static final String NEW_TEST_SERVER_NAME = "http://api.jisuapi.com/recipe/";
    //public static final String NEW_TEST_SERVER_NAME = "http://192.168.0.18:10005";

    public static final String SUCESS_CODE = "0";

    //urlhttp://api.jisuapi.com/recipe/class 菜谱分类
    //http://api.jisuapi.com/recipe/byclass?classid=2&start=0&num=10 按分类检索
    //http://api.jisuapi.com/recipe/detail?id=5 根据id查详情
    public static final String CLASS = "class";
    public static final String BYCLASS = "byclass";
    public static final String DETAIL = "detail";

}
