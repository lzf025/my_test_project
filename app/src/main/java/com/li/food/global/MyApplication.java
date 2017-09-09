package com.li.food.global;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ggxueche.utils.log.L;
import com.li.food.network.ErrorHandlerImpl;
import com.li.food.network.HttpUtils;
import com.li.food.network.NetConst;
import com.li.food.network.interceptor.BaseInterceptor;
import com.li.food.network.interceptor.HeaderInterceptor;
import com.li.food.network.interceptor.LoggingInterceptor;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import okhttp3.logging.HttpLoggingInterceptor;


public class MyApplication extends MultiDexApplication {

    public static Context mContext;
    private RefWatcher mRefWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        L.isPrint = !NetConst.RELEASE_SERVER;
        //初始化ARouter
        initARouter();
        initHttpUtils();
        mRefWatcher = LeakCanary.install(this);
    }

    //阿里路由
    private void initARouter() {
        if (!NetConst.RELEASE_SERVER) {
            ARouter.openLog();
            ARouter.openDebug(); // 使用InstantRun的时候，需要打开该开关，上线之后关闭，否则有安全风险
            ARouter.printStackTrace();// 打印日志的时候打印线程堆栈
        }
        ARouter.init(this);
    }

    private void initHttpUtils() {
        HttpUtils.getInstance().addInterceptor(new HeaderInterceptor(this));
        HttpUtils.getInstance().addInterceptor(new BaseInterceptor(this));
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new LoggingInterceptor());
        loggingInterceptor.setLevel(NetConst.RELEASE_SERVER ? HttpLoggingInterceptor.Level.NONE : HttpLoggingInterceptor.Level.BODY);
        HttpUtils.getInstance().addInterceptor(loggingInterceptor);
        HttpUtils.getInstance().setErrorHandler(new ErrorHandlerImpl(this));
    }
}
