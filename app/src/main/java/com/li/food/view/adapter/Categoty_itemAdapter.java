package com.li.food.view.adapter;

import android.content.Context;

import com.ggxueche.utils.adapter.CommonAdapter;
import com.ggxueche.utils.adapter.CommonViewHolder;
import com.li.food.R;
import com.li.food.bean.Category3Bean;

import java.util.List;

/**
 * 菜单二级分类
 *
 */

public class Categoty_itemAdapter extends CommonAdapter<Category3Bean> {

    public Categoty_itemAdapter(Context context, int layoutId, List<Category3Bean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(CommonViewHolder holder, Category3Bean categoryBean, int position) {
        holder.setText(R.id.name,categoryBean.getName());

    }

}
