package com.li.food.presenter;

import com.li.food.model.CategoryModel;
import com.li.food.view.inter.ICategoryView;

/**
 * Created by lzf on 2017/8/5.
 */

public class CategrotyPresenter extends BasePresenter<ICategoryView> {

    private CategoryModel categoryModel;

    public CategrotyPresenter() {
        categoryModel = new CategoryModel(this);
    }

    public void getFoodCategorty() {
        categoryModel.getFoodCategory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryModel.cancelNetRequest();
    }
}
