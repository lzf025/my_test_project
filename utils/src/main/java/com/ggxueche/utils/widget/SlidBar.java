package com.ggxueche.utils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.ggxueche.utils.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yaofc on 2016/11/28.
 */
public class SlidBar extends View {
    private static final int TOTAL_CHAR = 27;
    private List<String> a = new ArrayList<>();
    // touching event
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // if choosed
    private int choose = -1;
    private Paint paint = new Paint();
    private float startY;
    private float endY;
    private int singleHeight;


    private TextView mTextDialog;

    public SlidBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SlidBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidBar(Context context) {
        super(context);
    }

    public void setmTextDialog(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    // override onDraw function
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取对应高度
        int height = getHeight();
        // 获取对应宽度
        int width = getWidth();
        // 获取每一个字母的高度
        singleHeight = height / TOTAL_CHAR;
        int totalHeight = singleHeight * a.size();
        startY = (height - totalHeight) / 2;

        for (int i = 0; i < a.size(); i++) {
            //paint设置颜色
            paint.setColor(Color.rgb(33, 65, 98));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.y26));
            // 被选中
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半
            float x = width / 2 - paint.measureText(a.get(i)) / 2;
            float y1 = startY + singleHeight * i;
            endY = y1 + singleHeight;
            canvas.drawText(a.get(i), x, y1, paint);
            paint.reset();
        }


    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY(); // get the Y
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener changedListener = onTouchingLetterChangedListener;
        int letterPos = (int) ((y - startY) / singleHeight);
        switch (action) {
            case MotionEvent.ACTION_UP:
                choose = -1;
                invalidate();
                if (mTextDialog != null)
                    mTextDialog.setVisibility(View.INVISIBLE);
                break;

            default:
                if (oldChoose != letterPos) {
                    if (letterPos >= 0 && letterPos < a.size()) {
                        if (changedListener != null) {
                            changedListener.onTouchingLetterChanged(a.get(letterPos));
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(a.get(letterPos));
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = letterPos;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener changedListener) {
        this.onTouchingLetterChangedListener = changedListener;
    }

    /**
     * 索引list
     *
     * @param b
     */
    public void setIndexList(List<String> b) {
        this.a = b;
        invalidate();
    }

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String str);
    }
}
