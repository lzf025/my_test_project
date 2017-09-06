package com.ggxueche.utils.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.ggxueche.utils.R;

import java.util.ArrayDeque;


/**
 * Created by likunyang on 2017/5/23.
 */

public class DialogManager {
    private static DialogManager instance;
    private static ArrayDeque<NormalAlertDialog> dialogList;
    private ProgressBarDialog progressBarDialog;
    private NormalAlertDialog dialog;
    private OutSideListenerDialog mOusideDialog;
    private OutSideListenerDialog chargeDialog;

    private DialogManager() {
        dialogList = new ArrayDeque<>();
    }

    public static DialogManager getInstance() {
        if (instance == null) {
            synchronized (DialogManager.class) {
                if (instance == null) {
                    instance = new DialogManager();
                }
            }
        }
        return instance;
    }

    public boolean isShowing() {
        return (dialog != null && dialog.isShow()) || (progressBarDialog != null && progressBarDialog.isShowing());
    }

    public void dismissDialog() {
        if (progressBarDialog != null) {
            progressBarDialog.dismiss();
            progressBarDialog = null;
        }
        if (dialog != null && dialog.isShow()) {
            dialog.dismiss();
            dialog = null;
        }
        if (dialogList.size() != 0) {
            dialog = dialogList.pollFirst();
            dialog.show(dialog.getmBuilder());
        }

        if (mOusideDialog != null && mOusideDialog.isShowing()) {
            mOusideDialog.dismiss();
            mOusideDialog = null;
        }
    }

    public void showProgressBarDialog(Context mContext) {

        if (dialog != null && dialog.isShow()) {
            dialog.dismiss();
            dialog = null;
        }
        if (progressBarDialog != null && progressBarDialog.isShowing()) {
            return;
        }
        if (progressBarDialog == null) {
            progressBarDialog = new ProgressBarDialog(mContext);
            progressBarDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    progressBarDialog = null;
                }
            });
        }
        if (!progressBarDialog.isShowing()) {
            progressBarDialog.show();
        }

    }

    /**
     * @param alertContent 提示内容
     * @param isCancel     触摸屏幕是否隐藏Dialog false:不隐藏，true:隐藏
     * @param listener     按钮监听
     */
    public void showAlertDialog(Context ctx, String alertContent, boolean isCancel, DialogOnClickListener listener) {
        showAlertDialog(ctx, alertContent, isCancel, listener, null);
    }

    public void showAlertDialog(Context ctx, String contentResId, boolean isCancel, DialogOnClickListener listener, DialogInterface.OnCancelListener cancelListener) {
        NormalAlertDialog.Builder builder = new NormalAlertDialog.Builder(ctx);
        builder.setCanceledOnTouchOutside(isCancel);
        builder.setContentText(contentResId);
        builder.setTitleVisible(true);
        builder.setOnclickListener(listener);
        builder.setOnCancelListener(cancelListener);
        if (dialog == null || (dialog != null && !dialog.isShow())) {
            dialog = new NormalAlertDialog(builder);
            dialog.show(builder);
        } else {
            NormalAlertDialog dialog = new NormalAlertDialog(builder);
            dialogList.add(dialog);
        }
    }

    /**
     * @param contentResId 提示内容String Id
     * @param isCancel     触摸屏幕是否隐藏Dialog false:不隐藏，true:隐藏
     * @param listener     按钮监听
     */
    public void showAlertDialog(Context ctx, int contentResId, boolean isCancel, DialogOnClickListener listener) {
        showAlertDialog(ctx
                , ctx.getResources().getString(contentResId)
                , isCancel
                , listener);
    }

    public void showAlertDialog(NormalAlertDialog.Builder builder, boolean isCancel, DialogOnClickListener listener) {
        builder.setCanceledOnTouchOutside(isCancel);
        builder.setTitleVisible(true);
        builder.setOnclickListener(listener);
        if (dialog == null || (dialog != null && !dialog.isShow())) {
            dialog = builder.build();
            dialog.show(builder);
        } else {
            NormalAlertDialog dialog = new NormalAlertDialog(builder);
            dialogList.add(dialog);
        }
    }

    public void showOneBtnDialog(Context ctx, String alertContent, String btnText, boolean isCancel, View.OnClickListener listener) {
        NormalAlertDialog.Builder builder = new NormalAlertDialog.Builder(ctx);
        builder.setSingleMode(true);
        builder.setContentText(alertContent);
        builder.setTitleVisible(true);
        builder.setSingleButtonText(btnText);
        builder.setSingleListener(listener);
        if (dialog == null || (dialog != null && !dialog.isShow())) {
            dialog = new NormalAlertDialog(builder);
            dialog.show(builder);
        } else {
            NormalAlertDialog dialog = new NormalAlertDialog(builder);
            dialogList.add(dialog);
        }
    }


    public void showOneBtnDialog(NormalAlertDialog.Builder builder, boolean isCancel, View.OnClickListener listener) {
        builder.setTitleVisible(true);
        builder.setSingleMode(true);
        builder.setSingleListener(listener);
        if (dialog == null || (dialog != null && !dialog.isShow())) {
            dialog = new NormalAlertDialog(builder);
            dialog.show(builder);
        } else {
            NormalAlertDialog dialog = new NormalAlertDialog(builder);
            dialogList.add(dialog);
        }
    }

    /**
     * 发帖选择图片
     *
     * @param context
     * @param view
     * @param gravity
     * @param backGround
     */
    public void displayDialog(Context context, View view, int gravity, int backGround) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        dismissDialog();
        mOusideDialog = new OutSideListenerDialog(context, R.style.OaMenuDialog);
        mOusideDialog.setContentView(view);
        mOusideDialog.setCanceledOnTouchOutside(false);

        mOusideDialog.setGravity(gravity);
        mOusideDialog.setBackgroundDrawable(new ColorDrawable(backGround));
        mOusideDialog.setWindowAnimations(R.style.PopMenuAnimation);

        mOusideDialog.setDialogLayoutParams();
        mOusideDialog.show();
    }

    /**
     * 充值的Dialog
     * @param context
     * @param view
     * @param gravity
     * @param backGround
     */
    public void displayChargeDialog(Context context, View view, int gravity, int backGround) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        chargeDialog = new OutSideListenerDialog(context, R.style.OaMenuDialog);
        chargeDialog.setContentView(view);
        chargeDialog.setCanceledOnTouchOutside(false);

        chargeDialog.setGravity(gravity);
        chargeDialog.setBackgroundDrawable(new ColorDrawable(backGround));
        chargeDialog.setWindowAnimations(R.style.PopMenuAnimation);

        chargeDialog.setDialogLayoutParams();
        chargeDialog.show();
    }

    public void dismissChargeDialog() {
        if (chargeDialog != null && chargeDialog.isShowing()) {
            chargeDialog.dismiss();
            chargeDialog = null;
        }
    }

    /**
     * 帖子详情页 更多
     *
     * @param context
     * @param view
     * @param gravity
     * @param backGround
     */
    public void displayDialogNoneAnim(Context context, View view, int gravity, int backGround) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        dismissDialog();
        mOusideDialog = new OutSideListenerDialog(context, R.style.OaMenuDialog);
        mOusideDialog.setContentView(view);
        mOusideDialog.setCanceledOnTouchOutside(true);
        mOusideDialog.setCancelable(true);

        mOusideDialog.setGravity(gravity);
        mOusideDialog.setBackgroundDrawable(new ColorDrawable(backGround));
        mOusideDialog.setDialogLayoutParams();

        mOusideDialog.show();
    }

    /**
     * 帖子详情评论Dialog
     *
     * @param context
     * @param view
     * @param gravity
     * @param backGround
     */
    public void commentFeedDialog(final Context context, View view, int gravity, int backGround) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        dismissDialog();
        mOusideDialog = new OutSideListenerDialog(context, R.style.OaMenuDialog);
        mOusideDialog.setContentView(view);
        mOusideDialog.setCanceledOnTouchOutside(true);

        mOusideDialog.setGravity(gravity);
        mOusideDialog.setBackgroundDrawable(new ColorDrawable(backGround));
        mOusideDialog.setDialogLayoutParams();
        mOusideDialog.show();
    }

    public void examTipDialog(final Context context, View view, int gravity, int backGround) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        dismissDialog();

        mOusideDialog = new OutSideListenerDialog(context, R.style.OaMenuDialog);
        mOusideDialog.setContentView(view);
        mOusideDialog.setCanceledOnTouchOutside(true);
        mOusideDialog.setWindowAnimations(R.style.PopMenuAnimation);

        mOusideDialog.setGravity(gravity);
        mOusideDialog.setBackgroundDrawable(new ColorDrawable(backGround));
        mOusideDialog.setDialogLayoutParams();
        mOusideDialog.show();
    }

}
