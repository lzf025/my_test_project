/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ggxueche.utils.emoji;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * @author Hieu Rocker (rockerhieu@gmail.com).
 */
public class EmojiTextView extends AppCompatTextView {
    private int mEmojiconSize;
    private int mEmojiconTextSize;
    private int mTextStart = 0;
    private int mTextLength = -1;

    public EmojiTextView(Context context) {
        super(context);
        init(null);
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EmojiTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        //获取屏幕宽度
        Resources resources = getResources();
        DisplayMetrics dm =  resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int mSpace = width/15;

        mEmojiconTextSize = (int) getTextSize();
        mEmojiconSize = (width - mSpace*6) / 7;
        mTextStart = 0;
        mTextLength = -1;
        mEmojiconTextSize = (int) Math.max(getTextSize(), mEmojiconSize);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text)) {

            SpannableStringBuilder builder = new SpannableStringBuilder(text);

            EmojiHandler.addEmojis(getContext(), builder, mEmojiconSize, mEmojiconTextSize,
                    mTextStart, mTextLength);
            text = builder;
        }
        super.setText(text, type);
    }

    /**
     * Set the size of emojicon in pixels.
     */
    public void setEmojiconSize(int pixels) {
        mEmojiconSize = pixels;
        super.setText(getText());
    }

}
