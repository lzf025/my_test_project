package com.li.food.view.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ggxueche.utils.ToastUtil;
import com.ggxueche.utils.log.L;
import com.li.food.IClickHistory;
import com.li.food.R;
import com.li.food.services.AidlTestAService;

public class AidlTestActivity extends AppCompatActivity {
    private IClickHistory iClickHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_test);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iClickHistory){
                    try {
                        iClickHistory.setClickTime("李志枫");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    try {
                        String clickTime = iClickHistory.getClickTime();
                        ToastUtil.showToast(AidlTestActivity.this,clickTime);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        //创建Intent，对应服务端注册的Intent
        Intent intent = new Intent();
        intent.setAction("com.li.food.services.AidlTestAService");
        //绑定连接远程服务
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);



    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iClickHistory = IClickHistory.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iClickHistory = null;
        }
    };
}
