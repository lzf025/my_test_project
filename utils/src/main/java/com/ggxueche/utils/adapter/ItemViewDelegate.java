package com.ggxueche.utils.adapter;


/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(CommonViewHolder holder, T t, int position);

}
