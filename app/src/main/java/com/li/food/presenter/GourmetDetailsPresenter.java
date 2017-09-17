package com.li.food.presenter;

import android.content.Context;
import android.util.Log;

import com.li.food.model.GourmetModel;
import com.li.food.view.inter.IGourmetDetailsView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lzf on 2017/8/11.
 */

public class GourmetDetailsPresenter extends BasePresenter<IGourmetDetailsView> {
    private GourmetModel gourmetModel;
    public GourmetDetailsPresenter() {
        gourmetModel = new GourmetModel(this);
    }
    public void test(){
        gourmetModel.calcTime();
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        //gourmetModel.cancleTime();
        //gourmetModel = null;

    }
}
