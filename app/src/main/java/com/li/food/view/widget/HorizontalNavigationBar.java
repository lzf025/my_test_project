package com.li.food.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ggxueche.utils.DateUtil;
import com.li.food.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GL on 2017/6/30.
 */

public class HorizontalNavigationBar extends View {


    private Context context;

    private int seeSize = 5;
    private int selectedTextColor;
    private float selectedTextSize;
    private int textColor;
    private float textSize;
    private TextPaint textPaint;
    private TextPaint selectedTextPaint;

    //当前定位到的文字的位置
    private int position = 0;
    //控件宽度
    private int width;


    private boolean firstVisible = true;
    //每个条目所占的大小
    private int anInt;
    private Rect rect = new Rect();
    private int centerTextHeight = 0;
    private int centerTextWidth = 0;
    //记录滑动过程中的偏移量
    private float anOffset;
    //手指按下屏幕时的位置
    private float downX;
    private int textWidth = 0;
    private int textHeight = 0;

    private List<Long> dates = new ArrayList<Long>();
    private List<String> weeks = new ArrayList<String>();


    public HorizontalNavigationBar(Context context) {
        this(context, null);
    }

    public HorizontalNavigationBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalNavigationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
//        initDatas();
        setWillNotDraw(false);
        setClickable(true);
        //初始化属性
        initAttrs(attrs);
        initPaint();

    }


    public void setTextColor(int color, int selectedColor) {
        textColor = color;
        selectedTextColor = selectedColor;
    }

    /**
     * 初始化属性
     */
    private void initAttrs(AttributeSet attributeSet) {
        TintTypedArray tta = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet,
                R.styleable.HorizontalNavigationBar);

        seeSize = tta.getInteger(R.styleable.HorizontalNavigationBar_HorizontalNavigationBarSeeSize, 5);
        selectedTextSize = tta.getFloat(R.styleable.HorizontalNavigationBar_HorizontalNavigationBarSelectedTextSize, getResources().getDimension(R.dimen.y41));
        selectedTextColor = tta.getColor(R.styleable.HorizontalNavigationBar_HorizontalNavigationBarSelectedTextColor,
                context.getResources().getColor(R.color.main_color));
        textSize = tta.getFloat(R.styleable.HorizontalNavigationBar_HorizontalNavigationBarTextSize, getResources().getDimension(R.dimen.y28));
        textColor = tta.getColor(R.styleable.HorizontalNavigationBar_HorizontalNavigationBarTextColor,
                context.getResources().getColor(R.color.text_weak));

    }

    private void initPaint() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        selectedTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        selectedTextPaint.setColor(selectedTextColor);
        selectedTextPaint.setTextSize(selectedTextSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //初次绘制的话，获取控件的宽、高
        if (firstVisible) {
            width = getWidth();
            anInt = width / seeSize;
            firstVisible = false;
        }
        if (dates.size() == 0)
            return;
        if (position >= 0 && position <= dates.size() - 1) {
            String date = DateUtil.timestampStr12(dates.get(position));

            selectedTextPaint.getTextBounds(date, 0, date.length(), rect);

            centerTextWidth = rect.width();
            centerTextHeight = rect.height();
            canvas.drawText(date, getWidth() / 2 - centerTextWidth / 2 + anOffset,
                    getHeight() / 2 + centerTextHeight / 2, selectedTextPaint);


            for (int i = 0; i < dates.size(); i++) {
                //遍历strings，把每个地方都绘制出来，
                if (position > 0 && position < dates.size() - 1) {
                    //这里主要是因为strings数据源的文字长度不一样，为了让被选中两边文字距离中心宽度一样，
                    // 我们取得左右两个文字长度的平均值
                    String dateLeft = DateUtil.timestampStr12(dates.get(position - 1));
                    textPaint.getTextBounds(dateLeft, 0, dateLeft.length(), rect);
                    int width1 = rect.width();
                    String dateRight = DateUtil.timestampStr12(dates.get(position + 1));
                    textPaint.getTextBounds(dateRight, 0, dateRight.length(), rect);
                    int width2 = rect.width();
                    textWidth = (width1 + width2) / 2;
                }
                if (i == 0) {
                    //得到高，高度是一样的，所以无所谓
                    String dateZero = DateUtil.timestampStr12(dates.get(0));
                    textPaint.getTextBounds(dateZero, 0, dateZero.length(), rect);
                    textHeight = rect.height();
                }

                if (i != position) {
                    canvas.drawText(DateUtil.timestampStr12(dates.get(i)), (i - position) * anInt + getWidth() / 2 - textWidth / 2 + anOffset,
                            getHeight() / 3 + textHeight / 2, textPaint);//画出每组文字
                    canvas.drawText(weeks.get(i), (i - position) * anInt + getWidth() / 2 - textWidth / 2 + anOffset,
                            getHeight() * 2 / 3 + textHeight / 2, textPaint);//画出每组文字
                }


            }


        }


    }

    //记录滑动之前的位置
    private int lastPosition;

    private float scrollX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();//获得点下去的x坐标
                lastPosition = position;
                break;
            case MotionEvent.ACTION_MOVE://复杂的是移动时的判断
                scrollX = event.getX();

                if (position != 0 && position != dates.size() - 1)
                    anOffset = scrollX - downX;//滑动时的偏移量，用于计算每个是数据源文字的坐标值
                else {
                    anOffset = (float) ((scrollX - downX) / 1.5);//当滑到两端的时候添加一点阻力
                }

                if (scrollX > downX) {
                    //向右滑动，当滑动距离大于每个单元的长度时，则改变被选中的文字。
                    if (scrollX - downX >= anInt) {
                        if (position > 0) {
                            anOffset = 0;
                            position = position - 1;
                            downX = scrollX;
                        }
                    }
                } else {

                    //向左滑动，当滑动距离大于每个单元的长度时，则改变被选中的文字。
                    if (downX - scrollX >= anInt) {

                        if (position < dates.size() - 1) {
                            anOffset = 0;
                            position = position + 1;
                            downX = scrollX;
                        }
                    }
                }

                invalidate();
                break;

            case MotionEvent.ACTION_UP:

                if (scrollX > downX) {
                    //向右滑动，当滑动距离大于每个单元的长度时，则改变被选中的文字。
                    if (scrollX - downX >= anInt / 2) {
                        if (position > 0) {
                            position = position - 1;
                        }
                    }
                } else {
                    //向左滑动，当滑动距离大于每个单元的长度时，则改变被选中的文字。
                    if (downX - scrollX >= anInt / 2) {
                        if (position < dates.size() - 1) {
                            position = position + 1;
                        }
                    }
                }
                //抬起手指时，偏移量归零，相当于回弹。
                anOffset = 0;
                invalidate();
                if (lastPosition != position) {
                    mSelectDate.getReminderdata(dates.get(position));
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setDatas(List<Long> data, List<String> week) {
        dates.addAll(data);
        weeks.addAll(week);
        invalidate();
    }


    private SelectDate mSelectDate;

    public void setSelectDate(SelectDate selectDate) {
        if (selectDate != null) {
            mSelectDate = selectDate;
        }
    }

    public interface SelectDate {
        void getReminderdata(Long date);
    }


}
