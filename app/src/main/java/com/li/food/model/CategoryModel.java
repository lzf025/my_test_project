package com.li.food.model;

import android.util.Log;
import com.li.food.bean.BaseBean;
import com.li.food.bean.CategoryBean;
import com.li.food.global.Const;
import com.li.food.network.HttpUtils;
import com.li.food.network.RequestCallBack;
import com.li.food.presenter.CategrotyPresenter;
import java.util.List;
import retrofit2.Call;

/**
 * 类别
 * Created by lzf on 2017/8/5.
 */

public class CategoryModel extends BaseModel<CategrotyPresenter> {
    //public final String TAG = getClass().getSimpleName();

    public CategoryModel(CategrotyPresenter presenter) {
        super(presenter);
    }

    //获取菜单分类
    public void getFoodCategory() {

        if (cheNetWork()) return;
        mPresenter.getMvpView().showLoading();
        Log.i("-------", "getFoodCategory: " + "");
        RequestCallBack<BaseBean, List<CategoryBean>> foodCategoryCallBack = new RequestCallBack<BaseBean, List<CategoryBean>>() {
            @Override
            protected void onSucess(List<CategoryBean> data) {
                mPresenter.getMvpView().addCatrgoty(data);
            }

            @Override
            protected void onRequestFailure(Call<BaseBean> call, Throwable t, int code, String errorMsg) {

            }
        };
        HttpUtils.getInstance().getCallFood(foodCategoryCallBack, TAG);
    }

    private boolean cheNetWork() {
        if (!isNetWorkAvailable()) {
            if (mPresenter.isViewAttached()) {
                mPresenter.getMvpView().showToast(Const.NET_WORK_TIPS);
                mPresenter.getMvpView().hideLoading();
            }
            return true;
        }
        return false;
    }
}
