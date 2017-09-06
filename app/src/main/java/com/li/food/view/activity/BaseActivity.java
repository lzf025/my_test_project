package com.li.food.view.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ggxueche.utils.NetworkUtil;
import com.ggxueche.utils.PermissionUtil;
import com.ggxueche.utils.TUtil;
import com.ggxueche.utils.ToastUtil;
import com.ggxueche.utils.baseapp.AppManager;
import com.ggxueche.utils.dialog.DialogManager;
import com.ggxueche.utils.dialog.DialogOnClickListener;
import com.ggxueche.utils.dialog.NormalAlertDialog;
import com.ggxueche.utils.log.L;
import com.ggxueche.utils.ui.StatusBarCompat;
import com.li.food.R;
import com.li.food.global.Const;
import com.li.food.global.MyApplication;
import com.li.food.presenter.IPresenter;
import com.li.food.view.inter.IMvpView;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Activity 基类
 * Created by lzf on 2017/8/5.
 */

public abstract class BaseActivity<P extends IPresenter<V>, V extends IMvpView> extends AppCompatActivity implements IMvpView {
    public static final int METHOD_REQUEST_PERMISSION = 0, METHOD_TO_SETTING = 1;
    public String TAG = this.getClass().getSimpleName();
    protected Activity mActivity = this;
    protected P presenter;
    private String complain;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ARouter.getInstance().inject(this);
        beforeSetcontentView();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        getSupportActionBar().hide();
        unbinder = ButterKnife.bind(this);
        getPresenter();
        if (presenter != null) {
            presenter.attachView((V) this);
        }
        initView(savedInstanceState);
        initData();
        setListener();

    }

    private void beforeSetcontentView() {
        // 把actvity放到application栈中管理
        AppManager.getAppManager().addActivity(this);
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        StatusBarCompat.setStatusBarColor(this, this.getResources().getColor(R.color.main_color));
//        StatusBarCompat.translucentStatusBar(this);

    }

    @Override
    public void showLoading() {
        //加载中
        L.t(TAG).i("showLoading: ==== 加载中");
        DialogManager.getInstance().showProgressBarDialog(this);
    }

    @Override
    public void hideLoading() {
        //加载完成
        L.t(TAG).i("hideLoading: ==== 加载完成");
        DialogManager.getInstance().dismissDialog();
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null)
            presenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
            presenter.detachView();
            presenter = null;
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        AppManager.getAppManager().removeActivity(this);
        MyApplication.getRefWatcher(this).watch(this);
    }

    private void getPresenter() {
        presenter = TUtil.getType(this, 0);
    }

    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    @TargetApi(Build.VERSION_CODES.KITKAT)
    /**
     * 获取没有数据的空白View
     * @param id
     * @param error
     * @return
     */
/*    public View getEmptyViewNoData(int id, String error) {
        View mEmptyView = LayoutInflater.from(this).inflate(
                R.layout.layout_empty_view_no_data, null);
        mEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        TextView mEmptyTextView = (TextView) mEmptyView.findViewById(R.id.tv_error_text);
        ImageView mEmptyImageView = (ImageView) mEmptyView
                .findViewById(R.id.iv_error_icon);
        if (id != 0) mEmptyImageView.setImageResource(id);
        if (!TextUtils.isEmpty(error)) mEmptyTextView.setText(error);
        return mEmptyView;
    }*/

    /**
     * 获取没有网 或 网络请求失败空布局
     *
     * @return
     */
/*    public View getEmptyViewError(final EmptyViewClickListener clickListener) {
        View mEmptyView = LayoutInflater.from(this).inflate(
                R.layout.layout_empty_view_error, null);
        mEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v);
            }
        });
        return mEmptyView;
    }*/

    @Override
    public boolean isNetWorkAvailable() {
        if (!NetworkUtil.isNetworkAvaliable(this)) {
            showToast(Const.NET_WORK_TIPS);
            hideLoading();
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (PackageManager.PERMISSION_GRANTED != grantResults[i]) {
                permissionList.add(permissions[i]);
            }
        }
        permissions = permissionList.toArray(new String[permissionList.size()]);
        if (permissionList.isEmpty()) {
            permissionsAllGranted();
        } else if (!PermissionUtil.hasPermission(mActivity, permissions)) {
            applyPermission(requestCode, METHOD_TO_SETTING, complain, permissions);
            L.t(TAG).i("权限被拒绝");
        }
    }

    /**
     * 检查权限
     *
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {
        return PermissionUtil.hasPermission(mActivity, permissions);
    }

    /**
     * 申请权限
     *
     * @param requestCode 申请权限对应码
     * @param methodCode  执行对应逻辑索引 申请权限 设置设置
     * @param msg         给用户提示为什么需要该权限
     * @param permissions
     */
    public void applyPermission(int requestCode, int methodCode, String msg, String... permissions) {
        complain = msg;
        switch (methodCode) {
            case METHOD_REQUEST_PERMISSION:
                List<String> list = new ArrayList<>();
                for (int i = 0; i < permissions.length; i++) {
                    boolean isAuth = PermissionUtil.hasPermission(mActivity, permissions[i]);
                    if (!isAuth)
                        list.add(permissions[i]);
                }
                ActivityCompat.requestPermissions(mActivity, list.toArray(new String[list.size()]), requestCode);
                break;
            case METHOD_TO_SETTING:
                applyPermissionsFromSetting(requestCode, msg);
                break;
        }
    }

    /**
     * 申请权限提示
     *
     * @param requestCode
     * @param msg         给用户提示为什么需要该权限
     */
    public void applyPermissionsFromSetting(final int requestCode, String msg) {
        try {
            NormalAlertDialog.Builder builder = new NormalAlertDialog.Builder(this);
            builder.setRightButtonText(getResources().getString(R.string.setting));
            builder.setLeftButtonText(getResources().getString(R.string.later_setting));
            builder.setContentText(String.format(getResources().getString(R.string.premission_tip), msg));
            DialogManager.getInstance().showAlertDialog(builder, false, new DialogOnClickListener() {
                @Override
                public void clickLeftButton(View view) {
                    DialogManager.getInstance().dismissDialog();
                    permissionsDenied();
                }

                @Override
                public void clickRightButton(View view) {
                    DialogManager.getInstance().dismissDialog();
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, requestCode);
                }
            });
            //DialogManager.getInstance().showAlertDialog(this,);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 所申请的权限全部允许后执行
     * 在子类中可复写次方法
     */
    public void permissionsAllGranted() {
        //do something
    }

    /**
     * 所申请的权限被拒绝后执行
     * 在子类中可复写次方法
     */
    public void permissionsDenied() {

    }

    public interface EmptyViewClickListener {
        void onClick(View v);
    }


}
