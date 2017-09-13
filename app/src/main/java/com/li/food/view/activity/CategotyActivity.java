package com.li.food.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.li.food.R;
import com.li.food.bean.CategoryOneBean;
import com.li.food.global.Const;
import com.li.food.presenter.CategrotyPresenter;
import com.li.food.view.adapter.TabCategotyAdapter;
import com.li.food.view.inter.ICategoryView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;

@Route(path = Const.ACTIVITY_CATEGOTY)
public class CategotyActivity extends BaseActivity<CategrotyPresenter, ICategoryView> implements ICategoryView {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    List<CategoryOneBean> categoryList = new ArrayList<>();

    TabCategotyAdapter tabCategotyAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_categoty;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        presenter.getFoodCategorty();
    }

    @Override
    public void onFaile(Call call, Throwable t, int code, String errorMsg) {

    }

    @Override
    public void addCatrgoty(List<CategoryOneBean> data) {
        categoryList.addAll(data);
        tabCategotyAdapter = new TabCategotyAdapter(getSupportFragmentManager(),categoryList);
        viewPager.setAdapter(tabCategotyAdapter);
        tablayout.setupWithViewPager(viewPager);
    }
}
