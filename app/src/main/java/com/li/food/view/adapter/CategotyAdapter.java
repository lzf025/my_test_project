package com.li.food.view.adapter;

import android.content.Context;

import com.ggxueche.utils.adapter.CommonAdapter;
import com.ggxueche.utils.adapter.CommonViewHolder;
import com.li.food.R;
import com.li.food.bean.CategoryOneBean;

import java.util.List;

/**
 * 菜单一级分类
 *
 */

public class CategotyAdapter extends CommonAdapter<CategoryOneBean> {

    public CategotyAdapter(Context context, int layoutId, List<CategoryOneBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(CommonViewHolder holder, CategoryOneBean categoryOneBean, int position) {
        holder.setText(R.id.name, categoryOneBean.getName());
    }
}

