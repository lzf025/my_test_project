package com.li.food.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ggxueche.utils.DateUtil;
import com.ggxueche.utils.log.L;
import com.li.food.R;
import com.li.food.global.Const;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 无标题

        setContentView(R.layout.activity_home);
        ARouter.getInstance().build(Const.ACTIVITY_CATEGOTY).navigation();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(Const.ACTIVITY_CATEGOTY).navigation();
            }
        });
        findViewById(R.id.buttonImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this,ImagerActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btChoseClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this,ChoseClassActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btImageCompress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(Const.ACTIVITY_IMAGE_TEST).navigation();
            }
        });
        findViewById(R.id.btCarKeyboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
