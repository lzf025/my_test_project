package com.ggxueche.utils.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ggxueche.utils.R;

import java.util.ArrayList;

/**
 * <p>
 * size type: TypedValue.COMPLEX_UNIT_PX, TypedValue.COMPLEX_UNIT_SP, TypedValue.COMPLEX_UNIT_DP
 * default size type: TypedValue.COMPLEX_UNIT_PX
 *
 * 验证码输入框 added by yaofc 2017/07/11
 */

public class AuthNumberView extends LinearLayout {

    private static final String TAG = "authNumberView";

    public static final String BORDER = "border";

    public static final String LINE = "line";

    public static final String SYSTEM = "system";

    private static final int DEFAULT_LINE_COLOR = 0xff000000;

    private static final int DEFAULT_NUMBER_COUNT = 4;

    private static int DEFAULT_WH = 50;

    private static int DEFAULT_MARGIN = 5;

    private static int DEFAULT_CODETEXTSIZE = 22;

    private static final int DEFAULT_CODETEXTCOLOR = 0xffe36b1f;

    private static int DEFAULT_CODEPADDING = 2;

    private static final int DEFAULT_BGDRAWABLE = R.drawable.selector_default;

    private static boolean DEFAULT_AUTOTEXTSIZE = true;

    private Context context;

    private DisplayMetrics dm;

    private int number_count = DEFAULT_NUMBER_COUNT;

    private int wh = DEFAULT_WH;

    private int margin = DEFAULT_MARGIN;

    private int codeTextSize = DEFAULT_CODETEXTSIZE;

    private int codeTextColor = DEFAULT_CODETEXTCOLOR;

    private int codePadding = DEFAULT_CODEPADDING;

    private int codeBgDrawable = DEFAULT_BGDRAWABLE;

    private boolean autoTextSize = DEFAULT_AUTOTEXTSIZE;

    private String codeTest = "";

    private EditText selectView;

    private String editStyle = BORDER;

    private int linePadding = 0;

    private int lineWidth = 1;

    private int defaultLineColor = DEFAULT_LINE_COLOR;

    private int selectLineColor = DEFAULT_LINE_COLOR;

    private int lineMarginTop = 0;

    private ArrayList<EditText> list = new ArrayList<>();
    private ArrayList<View> lineViews = new ArrayList<>();

    private CodeFinishListener codeFinishListener;

    public interface CodeFinishListener {
        void addCodeFinishListener(String code);
    }

    public void addCodeFinishListener(CodeFinishListener codeFinishListener) {
        this.codeFinishListener = codeFinishListener;
    }

    public AuthNumberView(Context context) {
        super(context);
        init(context, null);
    }

    public AuthNumberView(Context context, int number_count) {
        super(context);
        this.number_count = number_count;
        init(context, null);
    }

    public AuthNumberView(Context context, int number_count, String editStyle) {
        super(context);
        this.number_count = number_count;
        this.editStyle = editStyle;
        init(context, null);
    }

    public AuthNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AuthNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AuthNumberView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;

        DEFAULT_WH = (int) getResources().getDimension(R.dimen.x50);
        DEFAULT_MARGIN = (int) getResources().getDimension(R.dimen.x5);
        DEFAULT_CODETEXTSIZE = (int) getResources().getDimension(R.dimen.y22);
        DEFAULT_CODEPADDING = (int) getResources().getDimension(R.dimen.x2);

        wh = DEFAULT_WH;
        margin = DEFAULT_MARGIN;
        codeTextSize = DEFAULT_CODETEXTSIZE;
        codePadding = DEFAULT_CODEPADDING;



        setOrientation(LinearLayout.HORIZONTAL);
        dm = getResources().getDisplayMetrics();
        initialAttrs(attrs);
        initEditText();
        addOnTouchListener();
        addTextChangeWatcher();
        setSelectView(list.get(0));
    }

    private void addTextChangeWatcher() {
        for (int j = 0; j < list.size(); j++) {
            final int finalJ = j;
            list.get(j).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() >= 1) {
                        if (finalJ != list.size() - 1)
                            setSelectView(list.get(finalJ + 1));
                        if (codeFinishListener != null) {
                            String code = getCode();
                            if (code.length() == number_count)
                                codeFinishListener.addCodeFinishListener(code);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    private void addOnTouchListener() {
        for (final EditText et : list) {
            et.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    setSelectView(et);
                    return false;
                }
            });
        }
    }

    private void setSelectView(EditText editText) {
        editText.requestFocus();
        selectView = editText;
        int positon = list.indexOf(editText);
        if (editStyle.equals(LINE))
            for (int i = 0; i < number_count; i++) {
                if (i == positon)
                    lineViews.get(i).setBackgroundColor(selectLineColor);
                else
                    lineViews.get(i).setBackgroundColor(defaultLineColor);
            }
    }

    private void initEditText() {
        EditText editText = null;
        LinearLayout linearLayout = null;
//        margin = dip2px(margin);
//        codePadding = dip2px(codePadding);
        for (int i = 0; i < number_count; i++) {

            linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(VERTICAL);
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

            editText = new EditText(context);
            editText.setGravity(Gravity.CENTER);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            editText.setCursorVisible(false);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setTextColor(codeTextColor);
            editText.setPadding(codePadding, codePadding, codePadding, codePadding);
            getAutoCodeTextSize();
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, codeTextSize);
            addCodeTest(editText, i);
            linearLayout.addView(editText);

//            int pxWH = dip2px(wh);
            int pxWH = wh;
            LayoutParams mLp = new LayoutParams(pxWH, pxWH);
            mLp.setMargins(margin, margin, margin, margin);
            editText.setLayoutParams(mLp);
            list.add(editText);

            if (editStyle.equals(LINE)) {
                editText.setBackground(null);
                View view = new View(context);
                mLp = new LayoutParams(pxWH - linePadding * 2, lineWidth);
//                mLp.topMargin = -sp2px(codeTextSize) + dip2px(10) + dip2px(lineMarginTop);
                mLp.topMargin = -codeTextSize + (int)getResources().getDimension(R.dimen.x20) + lineMarginTop;
                view.setLayoutParams(mLp);
                view.setBackgroundColor(defaultLineColor);
                linearLayout.addView(view);
                lineViews.add(view);
            } else if (editStyle.equals(BORDER)) {
                editText.setBackgroundResource(codeBgDrawable);
            }
            addView(linearLayout);
            mLp = null;
            editText = null;
            linearLayout = null;
        }
    }

    private void getAutoCodeTextSize() {
        if (codeTextSize == -1) {
            if (autoTextSize)
//                codeTextSize = dip2px(wh) / 5 * 4;
                codeTextSize = wh / 5 * 3;
            else
                codeTextSize = DEFAULT_CODETEXTSIZE;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
            if (selectView != null) {
                if (!TextUtils.isEmpty(selectView.getText().toString()))
                    selectView.setText("");
                else {
                    int position = list.indexOf(selectView);
                    if (position != 0)
                        position = position - 1;
                    setSelectView(list.get(position));
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private void initialAttrs(AttributeSet attrs) {
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.AuthNumberView);
        int count = types.getIndexCount();
        for (int i = 0; i < count; ++i) {
            int attr = types.getIndex(i);
            if (attr == R.styleable.AuthNumberView_autoTextSize)
                autoTextSize = types.getBoolean(attr, DEFAULT_AUTOTEXTSIZE);
            else if (attr == R.styleable.AuthNumberView_editTextWH)
                wh = types.getDimensionPixelSize(attr, DEFAULT_WH);
            else if (attr == R.styleable.AuthNumberView_codeMargin)
                margin = types.getDimensionPixelSize(attr, DEFAULT_MARGIN) / 2;
            else if (attr == R.styleable.AuthNumberView_codeTest)
                codeTest = types.getString(attr);
            else if (attr == R.styleable.AuthNumberView_numberCount)
                number_count = types.getInteger(attr, -1);
            else if (attr == R.styleable.AuthNumberView_codeTextColor)
                codeTextColor = types.getColor(attr, DEFAULT_CODETEXTCOLOR);
            else if (attr == R.styleable.AuthNumberView_codeTextSize)
                codeTextSize = types.getDimensionPixelSize(attr, -1);
            else if (attr == R.styleable.AuthNumberView_codePadding)
                codePadding = types.getDimensionPixelSize(attr, DEFAULT_CODEPADDING);
            else if (attr == R.styleable.AuthNumberView_codeBgDrawable)
                codeBgDrawable = types.getResourceId(attr, DEFAULT_BGDRAWABLE);
            else if (attr == R.styleable.AuthNumberView_editStyle)
                editStyle = types.getString(attr);
            else if (attr == R.styleable.AuthNumberView_lineWidth)
                lineWidth = types.getDimensionPixelSize(attr, 1);
            else if (attr == R.styleable.AuthNumberView_linePadding)
                linePadding = types.getDimensionPixelSize(attr, 0);
            else if (attr == R.styleable.AuthNumberView_defaultLineColor)
                defaultLineColor = types.getColor(attr, DEFAULT_LINE_COLOR);
            else if (attr == R.styleable.AuthNumberView_selectLineColor)
                selectLineColor = types.getColor(attr, DEFAULT_LINE_COLOR);
            else if (attr == R.styleable.AuthNumberView_lineMarginTop)
                lineMarginTop = types.getDimensionPixelSize(attr, 0);
        }
        types.recycle();
    }

//    private int dip2px(float dip) {
//        return (int) (dip * dm.density + 0.5);
//    }

    private int sp2px(float spValue) {
        return (int) (spValue * dm.scaledDensity + 0.5f);
    }

    /**
     * default is px
     *
     * @param codePadding
     */
    public void setCodePadding(int codePadding) {
        this.codePadding = codePadding;
        for (EditText et : list) {
            et.setPadding(codePadding, codePadding, codePadding, codePadding);
        }
    }

    /**
     * type:
     *
     * @param type
     * @param codePadding
     */
    public void setCodePadding(int type, int codePadding) {
        setCodePadding(getPX(type, codePadding));
    }

    public void setCodeTextColor(String color) {
        int _color = Color.parseColor(color);
        setCodeTextColor(_color);
    }

    public void setCodeTextColor(int color) {
        for (EditText et : list) {
            et.setTextColor(color);
        }
    }

    public void setCodeMargin(int margin) {
        for (EditText et : list) {
//            int pxWH = dip2px(wh);
            int pxWH = wh;
            LayoutParams mLp = new LayoutParams(pxWH, pxWH);
            mLp.setMargins(margin, margin, margin, margin);
            et.setLayoutParams(mLp);
        }
    }

    private void addCodeTest(EditText et, int position) {
        if (codeTest.length() - 1 >= position)
            et.setText(String.valueOf(codeTest.charAt(position)));
    }

    public void setCodeTest(String codeTest) {
        this.codeTest = codeTest;
        for (int i = 0; i < list.size(); i++) {
            addCodeTest(list.get(i), i);
        }
    }

    /**
     * default is px
     *
     * @param size
     */
    public void setCodeTextSize(int size) {
        for (EditText et : list) {
            et.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    public void setCodeTextSize(int type, int size) {
        setCodeTextSize(getPX(type, size));
    }

    private int getPX(int type, int size) {
        int finallySize = size;
        switch (type) {
            case TypedValue.COMPLEX_UNIT_SP:
                finallySize = sp2px(size);
                break;

            case TypedValue.COMPLEX_UNIT_DIP:
//                finallySize = dip2px(size);
                break;

            case TypedValue.COMPLEX_UNIT_PX:
            default:
                finallySize = size;
                break;
        }
        return finallySize;
    }

    public void setCodeBackground(int resId) {
        for (EditText et : list) {
            try {
                et.setBackgroundResource(resId);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "bg resId is wrong");
            }

        }
    }


    public String getCode() {
        String codes = "";
        for (EditText et : list) {
            codes += getString(et);
        }
        return codes;
    }

    private String getString(EditText editText) {
        return editText.getText().toString().trim();
    }

    public void setEditTextWH(int size) {
        for (EditText editText : list) {
            LayoutParams layoutParams = (LayoutParams) editText.getLayoutParams();
            layoutParams.width = size;
            layoutParams.height = size;
            editText.setLayoutParams(layoutParams);
        }
    }

    public void setEditTextWH(int type, int size) {
        setEditTextWH(getPX(type, size));
    }

    public void setAutoTextSize(boolean autoTextSize) {
        this.autoTextSize = autoTextSize;
        getAutoCodeTextSize();
        for (EditText editText : list) {
            editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, codeTextSize);
        }
    }

    public void setLineViewColor(int defaultLineColor) {
        setLineViewColor(defaultLineColor, defaultLineColor);
    }

    public void setLineViewColor(String defaultLineColor) {
        int color = Color.parseColor(defaultLineColor);
        setLineViewColor(color, color);
    }

    public void setLineViewColor(String defaultLineColor, String selectLineColor) {
        int color1 = Color.parseColor(defaultLineColor);
        int color2 = Color.parseColor(selectLineColor);
        setLineViewColor(color1, color2);
    }

    public void setLineViewColor(int defaultLineColor, int selectLineColor) {
        this.defaultLineColor = defaultLineColor;
        this.selectLineColor = selectLineColor;
        for (View view : lineViews) {
            view.setBackgroundColor(defaultLineColor);
        }
    }

    public void setLineMarginTop(int lineMarginTop) {
        this.lineMarginTop = lineMarginTop;
        for (View view : lineViews) {
            LayoutParams mLp = (LayoutParams) view.getLayoutParams();
//            mLp.topMargin = -sp2px(codeTextSize) + dip2px(10) + lineMarginTop;
            mLp.topMargin = -codeTextSize + (int) getResources().getDimension(R.dimen.x20) + lineMarginTop;
            view.setLayoutParams(mLp);
        }
    }

    public void setLinePadding(int linePadding) {
        this.linePadding = linePadding;
        for (View view : lineViews) {
            LayoutParams mLp = (LayoutParams) view.getLayoutParams();
//            mLp.width = dip2px(wh) - linePadding * 2;
            mLp.width = wh - linePadding * 2;
            view.setLayoutParams(mLp);
        }
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        for (View view : lineViews) {
            LayoutParams mLp = (LayoutParams) view.getLayoutParams();
            mLp.height = lineWidth;
            view.setLayoutParams(mLp);
        }
    }

    public void resetCode() {
        if (list.size() > 0) {
            for (EditText editText : list) {
                editText.setText("");
            }
            setSelectView(list.get(0));
        }
    }

}
