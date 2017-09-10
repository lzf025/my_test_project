package com.li.food.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.ggxueche.utils.GlideImgManager;
import com.ggxueche.utils.adapter.CommonAdapter;
import com.ggxueche.utils.adapter.CommonViewHolder;
import com.li.food.R;
import com.li.food.bean.MaterialBean;
import com.li.food.bean.ProcessBean;

import java.util.List;

/**
 * Created by lzf on 2017/9/9.
 */

public class ProcessAdapter extends CommonAdapter<ProcessBean> {
    public ProcessAdapter(Context context, int layoutId, List<ProcessBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(CommonViewHolder holder, ProcessBean processBean, int position) {
        holder.setText(R.id.tvStep, position + 1 + "");
        holder.setText(R.id.tvStepDes,processBean.getPcontent());
        ImageView imageView = holder.getView(R.id.ivStepImager);
        GlideImgManager.getInstance().loadImageView(mContext, processBean.getPic(),imageView);

    }
}
