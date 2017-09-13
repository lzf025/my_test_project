package com.li.food.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.ggxueche.utils.GlideImgManager;
import com.ggxueche.utils.adapter.CommonAdapter;
import com.ggxueche.utils.adapter.CommonViewHolder;
import com.li.food.R;
import com.li.food.bean.FoodDetailBean;

import java.util.List;

/**
 * 菜单列表
 *
 */

public class FoodAdapter extends CommonAdapter<FoodDetailBean> {

    public FoodAdapter(Context context, int layoutId, List<FoodDetailBean> datas) {
        super(context, layoutId, datas);

    }

    @Override
    protected void convert(CommonViewHolder holder, FoodDetailBean foodDetailBean, int position) {
        GlideImgManager.getInstance().loadImageView(mContext,foodDetailBean.getPic(),(ImageView) holder.getView(R.id.iv_food_img));
        holder.setText(R.id.tv_menu_name,foodDetailBean.getName());
        holder.setText(R.id.tv_eat_number,foodDetailBean.getPeoplenum());
    }
}

