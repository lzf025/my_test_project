package com.li.food.model;

import com.li.food.bean.BaseBean;
import com.li.food.bean.FoodContentBean;
import com.li.food.global.Const;
import com.li.food.network.HttpUtils;
import com.li.food.network.RequestCallBack;
import com.li.food.presenter.CategrotyListPresenter;

import retrofit2.Call;

/**
 * 类别
 */

public class CategoryListModel extends BaseModel<CategrotyListPresenter> {

    public CategoryListModel(CategrotyListPresenter presenter) {
        super(presenter);
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

    public void getClassById(String classId, String star, String num) {

        if (cheNetWork()) return;
        mPresenter.getMvpView().showLoading();
        RequestCallBack<BaseBean, FoodContentBean> classCallBack = new RequestCallBack<BaseBean, FoodContentBean>() {
            @Override
            protected void onSucess(FoodContentBean data) {
                if (mPresenter.isViewAttached()){
                    mPresenter.getMvpView().addFoodDetail(data.getList());
                }

            }

            @Override
            protected void onRequestFailure(Call<BaseBean> call, Throwable t, int code, String errorMsg) {

            }
        };
        HttpUtils.getInstance().getEnqueueFood(getApiService().getClassById(classId, star, num), classCallBack, TAG);
    }

}
