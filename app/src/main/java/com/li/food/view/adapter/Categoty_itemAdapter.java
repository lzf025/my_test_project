package com.li.food.view.adapter;

import android.content.Context;

import com.ggxueche.utils.adapter.CommonAdapter;
import com.ggxueche.utils.adapter.CommonViewHolder;
import com.li.food.R;
import com.li.food.bean.CategoryTwoBean;

import java.util.List;

/**
 * 菜单二级分类
 */

public class Categoty_itemAdapter extends CommonAdapter<CategoryTwoBean> {

    public Categoty_itemAdapter(Context context, int layoutId, List<CategoryTwoBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(CommonViewHolder holder, CategoryTwoBean categoryBean, int position) {
        if (getmClikePostion() == position) {
            holder.getView(R.id.name).setSelected(true);
            holder.getView(R.id.choseTag).setSelected(true);
        } else {
            holder.getView(R.id.name).setSelected(false);
            holder.getView(R.id.choseTag).setSelected(false);
        }
        holder.setText(R.id.name, categoryBean.getName());

    }

}

