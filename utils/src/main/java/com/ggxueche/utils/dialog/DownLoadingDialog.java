package com.ggxueche.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ggxueche.utils.R;
import com.ggxueche.utils.ScreenSizeUtils;


/**
 * 下载进度dialog
 * Created by likunyang on 2017/6/26.
 */

public class DownLoadingDialog {
    private final Dialog mDialog;
    TextView dialogTitle;
    ProgressBar progressLoading;
    TextView tvLoadingPercent;
    TextView tvProgressTotal;
    Button btnCancel;

    public DownLoadingDialog(@NonNull Context context) {
        mDialog = new Dialog(context, R.style.NormalDialogStyle);
        mDialog.setContentView(R.layout.dialog_downloading);
        dialogTitle = (TextView) mDialog.findViewById(R.id.dialog_title);
        progressLoading = (ProgressBar) mDialog.findViewById(R.id.progress_loading);
        tvLoadingPercent = (TextView) mDialog.findViewById(R.id.tv_loading_percent);
        tvProgressTotal = (TextView) mDialog.findViewById(R.id.tv_progress_total);
        btnCancel = (Button) mDialog.findViewById(R.id.btn_cancel);
        btnCancel.setTag(Status.LOADING);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (ScreenSizeUtils.getInstance(context).getScreenWidth() * 0.65);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);

    }


    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    /**
     * @param title 提示框title
     */
    public void setDialogTitle(String title) {
        dialogTitle.setText(title);
    }

    /**
     * @param progress 设置进度条进度
     */
    public void setProgress(int progress) {
        progressLoading.setProgress(progress);
    }

    /**
     * @param text 设置底部按钮文字
     */
    public void setBtnText(String text) {
        btnCancel.setText(text);
    }

    /**
     * @param percent 设置进度百分比
     */
    public void setTvLoadingPercent(int percent) {
        tvLoadingPercent.setText(percent + "%");
    }

    /**
     * @param progress 当前已完成进度
     * @param total    总进度
     */

    public void setTvProgressTotal(long progress, long total) {
        tvProgressTotal.setText(String.format("%d/%d", progress, total));
    }

    /**
     * @param listener 设置按钮点击监听
     */

    public void setOnProgressDialogBtnClickListener(final OnProgressDialogBtnClickListener listener) {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    Status status = (Status) v.getTag();
                    listener.onProgressClick(mDialog, v, status);
                }
            }
        });
    }

    /**
     * 设置提示框状态
     *
     * @param status
     */
    public void setStatus(Status status) {
        switch (status) {
            case LOADING:
                dialogTitle.setText(R.string.downloading);
                btnCancel.setText(R.string.cancel);
                btnCancel.setTag(status);
                break;
            case SUCESS:
                dialogTitle.setText(R.string.loading_sucess);
                btnCancel.setText(R.string.comfire);
                btnCancel.setTag(status);
                break;
            case FAILE:
                dialogTitle.setText(R.string.load_error);
                btnCancel.setText(R.string.reloading);
                btnCancel.setTag(status);
                break;
        }
    }

    public enum Status {
        LOADING, SUCESS, FAILE
    }

    public interface OnProgressDialogBtnClickListener {
        void onProgressClick(Dialog dialog, View v, Status status);
    }

}
