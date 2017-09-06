package com.li.food.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ggxueche.utils.adapter.BaseMultiItemAdapter;
import com.ggxueche.utils.adapter.CommonViewHolder;
import com.li.food.R;
import com.li.food.model.bean.DateBean;
import com.li.food.view.widget.AutoLocateHorizontalView;

import java.util.List;


/**
 * Created by jianglei on 2/4/17.
 */

public class AgeAdapter extends RecyclerView.Adapter<AgeAdapter.AgeViewHolder> implements AutoLocateHorizontalView.IAutoLocateHorizontalView {
    private Context context;
    private View view;
    private List<DateBean> datas;
    public AgeAdapter(Context context, List<DateBean> datas){
        this.context = context;
        this.datas = datas;
    }

    @Override
    public AgeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_age,parent,false);
        AgeViewHolder viewHolder = new AgeViewHolder(view);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }
    protected void setListener(final ViewGroup parent, final AgeViewHolder viewHolder, int viewType) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder , position);
                }
            }
        });
    }
    @Override
    public void onBindViewHolder(AgeViewHolder holder, int position) {
        holder.data.setText(datas.get(position).getDate());
        holder.week.setText(datas.get(position).getWeek());

    }

    @Override
    public int getItemCount() {
        return  datas.size();
    }

    @Override
    public View getItemView() {
        return view;
    }

    @Override
    public void onViewSelected(boolean isSelected, int pos, RecyclerView.ViewHolder holder, int itemWidth) {
        if(isSelected) {
            ((AgeViewHolder) holder).data.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.y41));
            ((AgeViewHolder) holder).data.setTextColor(context.getResources().getColor(R.color.main_color));
            ((AgeViewHolder) holder).week.setVisibility(View.GONE);
        }else{
            ((AgeViewHolder) holder).data.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.y28));
            ((AgeViewHolder) holder).week.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimension(R.dimen.y20));
            ((AgeViewHolder) holder).data.setTextColor(context.getResources().getColor(R.color.black_car));
            ((AgeViewHolder) holder).week.setTextColor(context.getResources().getColor(R.color.text_weak));
            ((AgeViewHolder) holder).week.setVisibility(View.VISIBLE);
        }
    }

    static class AgeViewHolder extends RecyclerView.ViewHolder{
        TextView data;
        TextView week;
        AgeViewHolder(View itemView) {
            super(itemView);
            data = (TextView)itemView.findViewById(R.id.data);
            week = (TextView)itemView.findViewById(R.id.week);
        }
        public View getConvertView()
        {
            return itemView;
        }
    }
    protected OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
