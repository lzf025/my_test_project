package com.li.food.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.li.food.bean.CategoryBean;
import com.li.food.view.fragment.CategotyListFragment;

import java.util.List;

/**
 * Created by lzf on 2017/8/11.
 */

public class TabCategotyAdapter extends FragmentStatePagerAdapter {
    private List<CategoryBean> categoryList;

    public TabCategotyAdapter(FragmentManager fm, List<CategoryBean> categoryList) {
        super(fm);
        this.categoryList = categoryList;
    }

    @Override
    public Fragment getItem(int position) {
        CategotyListFragment fragment = CategotyListFragment.newInstance(categoryList.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return categoryList == null ? 0 : categoryList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categoryList.get(position).getName();
    }
}
