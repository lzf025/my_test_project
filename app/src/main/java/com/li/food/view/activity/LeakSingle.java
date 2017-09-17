package com.li.food.view.activity;

import android.content.Context;
import android.widget.TextView;

import com.li.food.R;

/**
 * des:
 * Created by lizhifeng
 * on 2017/9/15 16:37
 */

public class LeakSingle {
    private Context mContext;
    private TextView mTextView;

    private static LeakSingle sInstance;

    private LeakSingle(Context context) {
        mContext = context;
    }

    public static LeakSingle getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LeakSingle(context);
        }
        return sInstance;
    }

    // 内存泄露
    public void setRetainedTextView(TextView tv) {
        mTextView = tv;
        mTextView.setText(mContext.getString(R.string.app_name));
    }

    // 删除引用, 防止泄露
    public void removeRetainedTextView() {
        mTextView = null;
    }
}
