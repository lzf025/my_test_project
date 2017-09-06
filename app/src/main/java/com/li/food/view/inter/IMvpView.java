package com.li.food.view.inter;

/**
 * Created by yaofc on 2017/5/16.
 */

public interface IMvpView<E> {
    /**
     * 显示loading对话框
     *
     */
    void showLoading();

    /**
     * 隐藏loading对话框
     */
    void hideLoading();

    /**
     * 显示无网络 或者 请求数据失败布局
     */
    void showErrorView();

    /**
     * 弹出提示
     * @param msg 提示信息
     */
    void showToast(String msg);

    /**
     * 设置监听
     */
    void setListener();

    /**
     * 检查网络
     */
    boolean isNetWorkAvailable();

}
