package com.li.food.view.activity;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ggxueche.utils.DateUtil;
import com.ggxueche.utils.ToastUtil;
import com.li.food.R;
import com.li.food.bean.BaseBean;
import com.li.food.bean.CategoryBean;
import com.li.food.model.bean.DateBean;
import com.li.food.network.HttpUtils;
import com.li.food.network.RequestCallBack;
import com.li.food.view.adapter.AgeAdapter;
import com.li.food.view.widget.AutoLocateHorizontalView;
import com.li.food.view.widget.HorizontalNavigationBar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

public class ChoseClassActivity extends AppCompatActivity implements HorizontalNavigationBar.SelectDate {

    HorizontalNavigationBar horizontalNavigationBar;
    List<Long> dates = new ArrayList<>();
    List<String> weeks = new ArrayList<>();

    AutoLocateHorizontalView mContent;
    String[] mdates = new String[]{"09-05", "09-06", "09-07", "09-08", "09-09", "09-10", "09-11", "09-12", "09-13", "09-14"};
    String[] mweeks = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期1", "星期2", "星期3", "星期4"};
//    String[] mdates = new String[]{"09-05", "09-06", "09-07"};
//    String[] mweeks = new String[]{"星期一", "星期二", "星期三"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_class);

        horizontalNavigationBar = (HorizontalNavigationBar) findViewById(R.id.horizontal_scroll_bar);
        dates.add(1504584671000L);
        dates.add(1504671071000L);
        dates.add(1504757471000L);
        dates.add(1504843871000L);
        dates.add(1504843871000L);
        dates.add(1504843871000L);
        dates.add(1504843871000L);dates.add(1504843871000L);
        dates.add(1504843871000L);dates.add(1504843871000L);
        dates.add(1504843871000L);dates.add(1504843871000L);
        dates.add(1504843871000L);dates.add(1504843871000L);
        dates.add(1504843871000L);
        for (int i = 0; i < dates.size(); i++) {
            weeks.add(DateUtil.getWeekOfDate(new Date(dates.get(i))));
        }
        horizontalNavigationBar.setDatas(dates,weeks);
        horizontalNavigationBar.setSelectDate(this);

        mContent = (AutoLocateHorizontalView) findViewById(R.id.reclyerView);
        final ArrayList<DateBean> dateBeanList = new ArrayList<>();
        for (int i = 0; i <mdates.length ; i++) {
            dateBeanList.add(new DateBean(mdates[i],mweeks[i]));
        }
        AgeAdapter ageAdapter = new AgeAdapter(ChoseClassActivity.this, dateBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mContent.setLayoutManager(linearLayoutManager);
        mContent.setOnSelectedPositionChangedListener(new AutoLocateHorizontalView.OnSelectedPositionChangedListener() {
            @Override
            public void selectedPositionChanged(int pos) {
                Log.i("mContent", "pos:: " + pos);
            }
        });
        ageAdapter.setOnItemClickListener(new AgeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String date = dateBeanList.get(position-1).getDate();
                mContent.moveToPosition(position-1);
                Log.i("mContent", "onItemClick: " + date);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        mContent.setInitPos(3);
        mContent.setItemCount(5);
        mContent.setAdapter(ageAdapter);

    }

    @Override
    public void getReminderdata(Long date) {
        ToastUtil.showToast(ChoseClassActivity.this,DateUtil.timestampStr12(date));
    }
}
