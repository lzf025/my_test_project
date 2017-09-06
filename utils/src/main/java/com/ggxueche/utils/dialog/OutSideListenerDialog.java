package com.ggxueche.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

/**
 * 监听触摸外部的Dialog
 * Created by yaofc on 2017/6/15.
 */

public class OutSideListenerDialog extends Dialog{

    private Context context;
    private Window mWindow;
    private OutOfBoundsListener mTouchOutSideListener;

    public OutSideListenerDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }



    public OutSideListenerDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    public OutSideListenerDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        mWindow = getWindow();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        /* 触摸外部弹窗 */
        if (isOutOfBounds(getContext(), event)) {
            if (mTouchOutSideListener != null) {
                mTouchOutSideListener.onTouchOutSide();
            }
        }
        return super.onTouchEvent(event);
    }

    private boolean isOutOfBounds(Context context, MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView = getWindow().getDecorView();
        return (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop))
                || (y > (decorView.getHeight() + slop));
    }

    public void setGravity(int gravity) {
        mWindow.setGravity(gravity);
    }



    public void setBackgroundDrawable(Drawable drawable) {
        mWindow.setBackgroundDrawable(drawable);
    }

    public void setWindowAnimations(int popMenuAnimation) {
        mWindow.setWindowAnimations(popMenuAnimation);
    }

    public void setDialogLayoutParams() {
        WindowManager.LayoutParams mParams = mWindow.getAttributes();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        mParams.width = (int) (display.getWidth() * 1.0);
        mWindow.setAttributes(mParams);
    }

    /**
     * Dialog外部点击事件监听
     * @param outOfBoundsListener
     */
    public void setOutOfBoundsListener(OutOfBoundsListener outOfBoundsListener) {
        this.mTouchOutSideListener = outOfBoundsListener;
    }

    public interface OutOfBoundsListener{
        void onTouchOutSide();
    }
}
