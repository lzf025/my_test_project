package com.li.food.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ggxueche.utils.adapter.BaseMultiItemAdapter;
import com.li.food.R;
import com.li.food.bean.CategoryTwoBean;
import com.li.food.bean.CategoryOneBean;
import com.li.food.bean.FoodDetailBean;
import com.li.food.global.Const;
import com.li.food.presenter.CategrotyListPresenter;
import com.li.food.view.adapter.Categoty_itemAdapter;
import com.li.food.view.adapter.FoodAdapter;
import com.li.food.view.inter.ICategotyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lzf on 2017/8/11.
 */

public class CategotyListFragment extends BaseFragment<CategrotyListPresenter, ICategotyListView> implements ICategotyListView {

    @BindView(R.id.recyclerChild)
    RecyclerView recyclerChild;
    @BindView(R.id.recycler3)
    RecyclerView recycler3;

    List<CategoryTwoBean> categoryItemList = new ArrayList<>();
    List<FoodDetailBean> foodList = new ArrayList<>();
    private Categoty_itemAdapter categoty_itemAdapter;
    private FoodAdapter foodAdapter;

    public static CategotyListFragment newInstance(CategoryOneBean categoryOneBean) {
        CategotyListFragment fragment = new CategotyListFragment();
        Bundle args = new Bundle();
        args.putSerializable("categoryOneBean", categoryOneBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.categoty_list;
    }

    @Override
    protected void initView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerChild.setLayoutManager(layoutManager);
        recycler3.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    protected void initData() {
        categoty_itemAdapter = new Categoty_itemAdapter(getContext(), R.layout.list_categoty_para_item, categoryItemList);
        foodAdapter = new FoodAdapter(getContext(), R.layout.list_categoty_menu_item, foodList);
        recyclerChild.setAdapter(categoty_itemAdapter);
        recycler3.setAdapter(foodAdapter);
        Bundle args = getArguments();
        CategoryOneBean categoryOneBean = (CategoryOneBean) args.getSerializable("categoryOneBean");
        categoty_itemAdapter.addItem(true, categoryOneBean.getList());


    }

    @Override
    public void setListener() {
        super.setListener();
        categoty_itemAdapter.setOnItemClickListener(new BaseMultiItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                String classId = categoryItemList.get(position).getClassid();
                presenter.getClassById(classId, "0", "300");
                categoty_itemAdapter.setmClikePostion(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        foodAdapter.setOnItemClickListener(new BaseMultiItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                FoodDetailBean foodDetailBean = foodList.get(position);
                ARouter.getInstance().build(Const.ACTIVITY_GOURMETDETAILS).withObject("foodDetail", foodDetailBean).navigation();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            String classId = categoryItemList.get(0).getClassid();
            presenter.getClassById(classId, "0", "300");
        }
    }

    @Override
    public void addFoodDetail(List<FoodDetailBean> data) {
        foodAdapter.addItem(true, data);
    }
}
