package com.li.food.presenter;

import com.li.food.view.inter.IMvpView;

public interface IPresenter<V extends IMvpView> {

    /**
     * Presenter与View建立连接
     *
     * @param mvpView 与此Presenter相对应的View
     */
    void attachView(V mvpView);

    /**
     * Presenter与View连接断开
     */
    void detachView();

    /**
     * Presenter关联View生命周期 onStart
     */
    void onStart();

    /**
     * Presenter关联View生命周期 onResume
     */
    void onResume();

    /**
     * Presenter关联View生命周期 onPause
     */
    void onPause();

    /**
     * Presenter关联View生命周期 onStop
     */
    void onStop();

    /**
     * Presenter关联View生命周期 onDestroy
     */
    void onDestroy();

}
