package com.li.food.presenter;

import com.li.food.model.CategoryListModel;
import com.li.food.model.CategoryModel;
import com.li.food.view.inter.ICategoryView;
import com.li.food.view.inter.ICategotyListView;

/**
 * Created by lzf on 2017/8/5.
 */

public class CategrotyListPresenter extends BasePresenter<ICategotyListView> {

    private CategoryListModel categoryListModel;

    public CategrotyListPresenter() {
        categoryListModel = new CategoryListModel(this);
    }

    public void getClassById(String classId, String star, String num) {
        categoryListModel.getClassById(classId, star, num);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryListModel.cancelNetRequest();
    }
}
