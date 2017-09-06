package com.ggxueche.utils.emoji;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ggxueche.utils.ToastUtil;

/**
 * Emoji键盘工具类
 * Created by yaofc on 2017/6/5.
 */

public class EmojiUtil {
    private static EmojiUtil mEmojiUtil = new EmojiUtil();
    private InputMethodManager mInputMan;
    public BaseInputConnection mInputConnection = null;
    private int totalTime = 0;
    private boolean isFinish = false;
    private Context mContext;
    private EmojiBorad mEmojiBoard;
    private FrameLayout selectLayout;
    private ImageView ivSelectEmoji;
    private EditText mEditText;

    public static EmojiUtil getInstace(){
        return mEmojiUtil;
    }

    /**
     * 该Handler主要处理软键盘的弹出跟隐藏
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            View view = (View) msg.obj;
            // 显示软键盘
            if (msg.what == EmojiConstants.INPUT_METHOD_SHOW) {
                boolean result = mInputMan.showSoftInput(view, 0);
                if (!result && totalTime < EmojiConstants.LIMIT_TIME) {
                    totalTime += EmojiConstants.IDLE;
                    Message message = Message.obtain(msg);
                    mHandler.sendMessageDelayed(message, EmojiConstants.IDLE);
                } else if (!isFinish) {
                    totalTime = 0;
                    result = view.requestFocus();
                    isFinish = true;
                }
            } else if (msg.what == EmojiConstants.INPUT_METHOD_DISAPPEAR) {
                // 隐藏软键盘
                mInputMan.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        }
    };

    public void init(Context context, EditText editText, EmojiBorad mEmojiBoard, FrameLayout selectLayout, ImageView ivSelectEmoji){
        this.mContext = context;
        this.mEditText = editText;
        this.mEmojiBoard = mEmojiBoard;
        this.selectLayout = selectLayout;
        this.ivSelectEmoji = ivSelectEmoji;
        ResFinder.initContext(context);
        mInputMan = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputConnection = new BaseInputConnection(editText, true);

    }

    /**
     * 显示emoji键盘
     * @param frameLayout
     * @param ivSelectEmoji
     * @param etEditFeed
     */
    public void showKeyboard(FrameLayout frameLayout, ImageView ivSelectEmoji, EditText etEditFeed) {
        frameLayout.setVisibility(View.GONE);
        if (!ivSelectEmoji.isSelected()) {
            showInputMethod(etEditFeed);
        }
    }

    /**
     * 显示输入法</br>
     *
     * @param view
     */
    public void showInputMethod(View view) {
        sendInputMethodMessage(EmojiConstants.INPUT_METHOD_SHOW, view);
    }

    /**
     * 发送show or hide输入法消息</br>
     *
     * @param type
     * @param view
     */
    private void sendInputMethodMessage(int type, View view) {
        Message message = mHandler.obtainMessage(type);
        message.obj = view;
        mHandler.sendMessage(message);
    }

    /**
     * 隐藏输入法</br>
     *
     * @param view
     */
    public void hideInputMethod(View view) {
        sendInputMethodMessage(EmojiConstants.INPUT_METHOD_DISAPPEAR, view);
    }

    /**
     * 隐藏emoji键盘
     * @return
     */
    public boolean hideEmojiBoard() {
        if (ivSelectEmoji.isSelected()) {
            mEmojiBoard.setVisibility(View.GONE);
            ivSelectEmoji.setSelected(false);
            return true;
        }
        return false;
    }

    /**
     * 用户点击back按钮。</br>
     */
    public void dealBackLogic() {
        selectLayout.setVisibility(View.GONE);
        hideInputMethod(mEditText);
    }

    public boolean isCharsOverflow(int extraTextLen,EditText editText) {
        int len = editText.getText().length();
        return len + extraTextLen >= 300;
    }

    public void setOnEmojiItemClick(EmojiBean emojiBean) {
        // delete event
        if (EmojiBorad.DELETE_KEY.equals(emojiBean.getEmoji())) {
            // 对于删除事件，此时模拟一个输入法上的删除事件达到删除的效果
            //【注意：此处不能调用delete方法，原因是emoji有些是单字符，有的是双字符】
            mInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_DEL));
            return;
        }
        // 预判断，如果插入超过140字符，则不显示最新的表情
        int emojiLen = emojiBean.isDouble ? 2 : 1;
        if (isCharsOverflow(emojiLen,mEditText)) {
            ToastUtil.showToast(mContext, "抱歉,已超过字符上限");
            return;
        }
        int start = mEditText.getSelectionStart();
        int end = mEditText.getSelectionEnd();
        if (start < 0) {
            mEditText.append(emojiBean.getEmoji());
        } else {
            mEditText.getText().replace(Math.min(start, end), Math.max(start, end),
                    emojiBean.getEmoji(), 0, emojiBean.getEmoji().length());
        }
    }

    public void setOnEmojiClick() {
        if (mEmojiBoard.getVisibility() == View.VISIBLE) { // 显示输入法，隐藏表情board
            mEmojiBoard.setVisibility(View.GONE);
            ivSelectEmoji.setSelected(false);
            showKeyboard(selectLayout,ivSelectEmoji,mEditText);
        } else { // 隐藏输入法，显示表情board
            ivSelectEmoji.setSelected(true);
            mEmojiBoard.postDelayed(new Runnable() {

                @Override
                public void run() {
                    mEmojiBoard.setVisibility(View.VISIBLE);
                }
            }, 100);
           hideInputMethod(mEmojiBoard);
        }
    }


}
