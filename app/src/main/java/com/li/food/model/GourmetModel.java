package com.li.food.model;

import android.util.Log;

import com.ggxueche.utils.log.L;
import com.li.food.presenter.GourmetDetailsPresenter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lzf on 2017/9/4.
 */

public class GourmetModel extends BaseModel<GourmetDetailsPresenter> {
    public GourmetModel(GourmetDetailsPresenter presenter) {
        super(presenter);
    }
    Timer timer;
    public void calcTime(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                L.d("run: ");
            }
        }, 3000 , 1000);
    }
    public void cancleTime(){
        if (timer != null){
            timer.cancel();
        }
    }
}
