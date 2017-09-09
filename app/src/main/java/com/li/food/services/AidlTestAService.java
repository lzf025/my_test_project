package com.li.food.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.li.food.IClickHistory;

/**
 * aidl测试服务
 */
public class AidlTestAService extends Service {

    //点击的时间
    private String mClickTime;
    private MyBinder myBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        myBinder = new MyBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyBinder extends IClickHistory.Stub {

        @Override
        public String getClickTime() throws RemoteException {
            return mClickTime;
        }

        @Override
        public void setClickTime(String clickTime) throws RemoteException {
            mClickTime = clickTime;
        }
    }
}
