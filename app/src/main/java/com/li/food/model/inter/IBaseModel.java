package com.li.food.model.inter;

/**
 * Created by lzf on 2017/5/19.
 */

public interface IBaseModel {

    /**
     * 检查网络是否可用
     *
     * @return
     */
    boolean isNetWorkAvailable();

    /**
     * 取消网络请求
     */
    void cancelNetRequest();


}
