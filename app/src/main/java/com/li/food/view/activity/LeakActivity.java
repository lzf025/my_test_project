package com.li.food.view.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.li.food.R;

import java.util.Timer;
import java.util.TimerTask;

public class LeakActivity extends BaseActivity {
    TextView aa;
    @Override
    public int getLayoutId() {
        return R.layout.activity_leak;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
          aa = (TextView) findViewById(R.id.aa);
    }

    @Override
    protected void initData() {
        //testLeak();
        //LeakSingle.getInstance(LeakActivity.this).setRetainedTextView(aa);
        startAsyncTask();

    }
    void startAsyncTask() {

        // This async task is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
        // the activity instance will leak.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // Do some slow work in background
                SystemClock.sleep(200000);
                return null;
            }
        }.execute();
        Toast.makeText(this, "请关闭这个A完成泄露", Toast.LENGTH_SHORT).show();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("lzf", "run: "+"运行中"+ System.currentTimeMillis());
        }
    };
    Timer timer;

    public void testLeak() {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 3000 , 1000);
    }
}
