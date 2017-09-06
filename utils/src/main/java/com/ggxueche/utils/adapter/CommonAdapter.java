package com.ggxueche.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T> extends BaseMultiItemAdapter<T>
{
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonAdapter(final Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(CommonViewHolder holder, T t, int position)
            {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    public void addItem(boolean isPull, List<T> data){
        if (!isPull && mDatas != null) {
            mDatas.addAll(data);
        } else {
            mDatas.clear();
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    protected abstract void convert(CommonViewHolder holder, T t, int position);


}
