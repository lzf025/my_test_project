package com.ggxueche.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ggxueche.utils.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.BallSpinFadeLoaderIndicator;

/**
 * Created by likunyang on 2017/5/24.
 */

public class ProgressBarDialog {

    private Dialog mDialog;
    private Context mContext;
    private View mDialogView;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private DialogInterface.OnDismissListener listener;

    public ProgressBarDialog(Context ctx) {
        this.mContext = ctx;
        mDialog = new Dialog(mContext, R.style.TransparentMenuDialog);
        mDialogView = LayoutInflater.from(mContext).inflate(R.layout.progress_view, null);
        avLoadingIndicatorView = (AVLoadingIndicatorView) mDialogView.findViewById(R.id.progress_image);
        avLoadingIndicatorView.setIndicator(new BallSpinFadeLoaderIndicator());
        avLoadingIndicatorView.setIndicatorColor(ContextCompat.getColor(ctx, R.color.main_color));
        mDialog.setContentView(mDialogView);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (listener != null) {
                    listener.onDismiss(dialog);
                }
                avLoadingIndicatorView.getIndicator().stop();
            }
        });
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        this.listener = listener;
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }
}
