package com.ggxueche.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by lzf on 2017/9/9.
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomLinearLayoutManager(Context context ,boolean flag) {
        super(context);
        setScrollEnabled(flag);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
