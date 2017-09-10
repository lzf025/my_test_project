package com.li.food.view.adapter;

import android.content.Context;

import com.ggxueche.utils.adapter.CommonAdapter;
import com.ggxueche.utils.adapter.CommonViewHolder;
import com.li.food.R;
import com.li.food.bean.MaterialBean;

import java.util.List;

/**
 * Created by lzf on 2017/9/9.
 */

public class MaterialAdapter extends CommonAdapter<MaterialBean> {
    public MaterialAdapter(Context context, int layoutId, List<MaterialBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(CommonViewHolder holder, MaterialBean materialBean, int position) {
        holder.setText(R.id.materialName,materialBean.getMname());
        holder.setText(R.id.materialValue,materialBean.getAmount());
    }
}
