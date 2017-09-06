package com.ggxueche.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.ref.SoftReference;

/**
 * Created by yaofc on 2017/5/18.
 */

public class ToastUtil {

    private static Toast mToast;
    private static Context mContext;

    public static void showToast(Context context, Object obj) {
        SoftReference<Context> softReference = new SoftReference<>(context);
        mContext = softReference.get();
        if (mContext!=null){
            String msg = "";
            if (obj instanceof String) {
                msg = obj.toString();
            } else if (obj instanceof Integer){
                msg = mContext.getResources().getString((int)obj);
            }
            if (VerifyUtil.isEmpty(msg)) {
                return;
            }
            if (mToast == null) {
                mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(msg);
            }
            mToast.show();
        }

    }

    public static void showLongToast(Context context, Object obj){
        SoftReference<Context> softReference = new SoftReference<>(context);
        mContext = softReference.get();
        if (mContext!=null){
            String msg = "";
            if (obj instanceof String) {
                msg = obj.toString();
            } else if (obj instanceof Integer){
                msg = mContext.getResources().getString((int)obj);
            }
            if (VerifyUtil.isEmpty(msg)) {
                return;
            }
            if (mToast == null) {
                mToast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
            } else {
                mToast.setText(msg);
            }
            mToast.show();
        }
    }

    public static void showCenterToast(Context context, Object obj){
        SoftReference<Context> softReference = new SoftReference<>(context);
        mContext = softReference.get();
        String msg = "";
        if (obj instanceof String) {
            msg = obj.toString();
        } else if (obj instanceof Integer){
            msg = mContext.getResources().getString((int)obj);
        }
        if (VerifyUtil.isEmpty(msg)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }


}
