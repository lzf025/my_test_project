package com.li.food.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ggxueche.utils.NetworkUtil;
import com.ggxueche.utils.PermissionUtil;
import com.ggxueche.utils.TUtil;
import com.ggxueche.utils.ToastUtil;
import com.ggxueche.utils.dialog.DialogManager;
import com.ggxueche.utils.dialog.DialogOnClickListener;
import com.ggxueche.utils.dialog.NormalAlertDialog;
import com.ggxueche.utils.log.L;
import com.li.food.R;
import com.li.food.global.Const;
import com.li.food.presenter.IPresenter;
import com.li.food.view.inter.IMvpView;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lzf on 2017/8/5.
 */

public abstract class BaseFragment<P extends IPresenter<V>, V extends IMvpView> extends Fragment implements IMvpView{
    public static final int METHOD_REQUEST_PERMISSION = 0, METHOD_TO_SETTING = 1;
    //public String TAG = this.getClass().getSimpleName();
    public final String TAG = this.getClass().getName() + '@' + Integer.toHexString(this.hashCode());
    public View mView;
    protected P presenter;
    private LayoutInflater mInflater;
    private Unbinder unbinder;
    private String complain;

    protected boolean mIsNeedLoadData = true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter();
        if (presenter != null) {
            presenter.attachView((V) this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        mView = inflater.inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, mView);
        initView();
        initData();
        setListener();
        if (getUserVisibleHint() && mIsNeedLoadData) {
            onFragmentVisibleChange(true);
            mIsNeedLoadData = false;
        }
        return mView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint() -> isVisibleToUser: " + isVisibleToUser);
        if (mView == null) {
            return;
        }

        if (isVisibleToUser && mIsNeedLoadData) {
            onFragmentVisibleChange(true);
            mIsNeedLoadData = false;
        }

    }
    @Override
    public void showLoading() {
        //加载中
        L.t(TAG).i("showLoading: ==== 加载中");
//        DialogManager.getInstance().showProgressBarDialog(getActivity());
    }

    @Override
    public void hideLoading() {
        //加载完成
        L.t(TAG).i("hideLoading: ==== 加载完成");
//        DialogManager.getInstance().dismissDialog();
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public boolean isNetWorkAvailable() {
        if (!NetworkUtil.isNetworkAvaliable(getActivity())) {
            showToast(Const.NET_WORK_TIPS);
            hideLoading();
            return false;
        }

        return true;
    }

    @Override
    public void setListener() {

    }


    protected void onFragmentVisibleChange(boolean isVisible){

    };
    /**
     * 获取没有数据的空白View
     *
     * @param id
     * @param error
     * @return
     */
/*    public View getEmptyViewNoData(int id, String error) {
        View mEmptyView = LayoutInflater.from(getContext()).inflate(
                R.layout.layout_empty_view_no_data, null);
        mEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
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
        View mEmptyView = LayoutInflater.from(getContext()).inflate(
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

/*    @Override
    public void onReloadBtnClick() {
        initData();
    }*/

    @Override
    public void showToast(String msg) {
        ToastUtil.showToast(getActivity(), msg);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
            presenter.onDestroy();
        }

        if (unbinder != null)
            unbinder.unbind();
    }

    protected <T extends View> T findViewById(int id) {
        if (mView == null) {
            return null;
        }

        return (T) mView.findViewById(id);
    }

    private void getPresenter() {
        presenter = TUtil.getType(this, 0);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 检查权限
     *
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {
        return PermissionUtil.hasPermission(getContext(), permissions);
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
                    boolean isAuth = PermissionUtil.hasPermission(getActivity(), permissions[i]);
                    if (!isAuth)
                        list.add(permissions[i]);
                }
                ActivityCompat.requestPermissions(getActivity(), list.toArray(new String[list.size()]), requestCode);
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
     * @param msg  给用户提示为什么需要该权限
     */
    public void applyPermissionsFromSetting(final int requestCode, String msg) {
        try {
            NormalAlertDialog.Builder builder = new NormalAlertDialog.Builder(getContext());
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
                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
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
