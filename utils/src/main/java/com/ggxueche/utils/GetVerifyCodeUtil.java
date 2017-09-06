package com.ggxueche.utils;

import android.os.CountDownTimer;

import java.lang.ref.SoftReference;

/**
 * 获取登录验证码倒计时工具类
 * Created by yaofc on 2017/6/12.
 */

public class GetVerifyCodeUtil {

    private static GetVerifyCodeUtil mGetVerifyCodeUtil = new GetVerifyCodeUtil();
    private Getcode getcode;
    private static CountDownTimerListener countDownTimerListener;

    public static GetVerifyCodeUtil getInstance(){
        return mGetVerifyCodeUtil;
    }


    /**
     * 开始计时
     * @param totalTime  总时间
     * @param intervalTime  间隔时间
     * @param countDownTimerListener 倒计时监听
     */
    public void start(int totalTime,int intervalTime,CountDownTimerListener countDownTimerListener) {
        this.countDownTimerListener = new SoftReference<>(countDownTimerListener).get();
        getcode = new Getcode(totalTime,intervalTime);
        getcode.start();
    }

    public void cancel() {
        if (null != getcode) {
            getcode.cancel();
            getcode = null;
        }
    }

     static class Getcode extends CountDownTimer {

        public Getcode(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            countDownTimerListener.onFinish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            countDownTimerListener.onTick(millisUntilFinished);
        }
    }


    public interface CountDownTimerListener{
        void onFinish();

        void onTick(long millisUntilFinished);
    }

}
