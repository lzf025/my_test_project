package com.ggxueche.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.File;

/**
 * Glide 包装
 * Created by yaofc on 2017/5/19.
 */
@GlideModule
public class GlideImgManager extends AppGlideModule {

    private static GlideImgManager mGlideManager = new GlideImgManager();

    public static GlideImgManager getInstance() {
        return mGlideManager;
    }

    /**
     * 根据路径图片加载
     *
     * @param mContext   上下文
     * @param path       路径
     * @param mImageView ImageView
     */
    public void loadImageView(Context mContext, String path, ImageView mImageView) {
        GlideApp.with(mContext).load(path).into(mImageView);
    }

    /**
     * 设置头像 性别是Integer
     *
     * @param mContext
     * @param path
     * @param mImageView
     */
    public void loadHeadImageView(Context mContext, String path, ImageView mImageView,int sex) {
        if (!VerifyUtil.isEmpty(path)) {
            GlideApp.with(mContext)
                    .load(path)
                    .placeholder(sex == Const.SEX_FEMALE ? R.mipmap.bg_default_female : R.mipmap.bg_default_male)
                    .error(sex == Const.SEX_FEMALE ? R.mipmap.bg_default_female : R.mipmap.bg_default_male)
                    .into(mImageView);
        } else {
            if (sex == Const.SEX_FEMALE) {
                mImageView.setImageResource(R.mipmap.bg_default_female);
            } else {
                mImageView.setImageResource(R.mipmap.bg_default_male);
            }
        }

    }

    /**
     * 设置头像 性别是枚举
     *
     * @param mContext
     * @param path
     * @param mImageView
     */
    public void loadHeadImageView(Context mContext, String path, ImageView mImageView,String sex) {
        if (!VerifyUtil.isEmpty(path)) {
            GlideApp.with(mContext)
                    .load(path)
                    .placeholder("FEMALE".equals(sex) ? R.mipmap.bg_default_female : R.mipmap.bg_default_male)
                    .error("FEMALE".equals(sex) ? R.mipmap.bg_default_female : R.mipmap.bg_default_male)
                    .into(mImageView);
        } else {
            if ("FEMALE".equals(sex)) {
                mImageView.setImageResource(R.mipmap.bg_default_female);
            } else {
                mImageView.setImageResource(R.mipmap.bg_default_male);
            }
        }

    }



    /**
     * 加载本地资源图片
     *
     * @param mContext   上下文
     * @param resourceId 资源文件Id
     * @param mImageView ImageView
     */
    public void loadImageView(Context mContext, int resourceId, ImageView mImageView) {
        Glide.with(mContext).load(resourceId).into(mImageView);
    }

    /**
     * 加载图片文件
     *
     * @param mContext   上下文
     * @param file       图片文件
     * @param mImageView ImageView
     */
    public void loadImageView(Context mContext, File file, ImageView mImageView) {
        GlideApp.with(mContext).load(file).fitCenter().into(mImageView);
    }


    /**
     * 从Uri加载图片
     *
     * @param mContext   上下文
     * @param uri        图片Uri
     * @param mImageView ImageView
     */
    public void loadImageView(Context mContext, Uri uri, ImageView mImageView) {
        Glide.with(mContext).load(uri).into(mImageView);
    }

    /**
     * 从资源文件加载Gif图片
     *
     * @param mContext
     * @param resourceId
     * @param mImageView
     */
    public void loadGifImageView(Context mContext, int resourceId, ImageView mImageView) {
        Glide.with(mContext).asGif().load(resourceId).into(mImageView);
    }

    /**
     * 从文件加载Gif文件
     *
     * @param mContext
     * @param file
     * @param mImageView
     */
    public void loadGifImageView(Context mContext, File file, ImageView mImageView) {
        Glide.with(mContext).asGif().load(file).into(mImageView);
    }

    /**
     * @param mContext    上下文
     * @param path        路径
     * @param placeholder 加载中占位图片
     * @param error       加载出错显示的图片
     * @param mImageView  ImageView
     */
    public void loadImageView(Context mContext, String path, int placeholder, int error, ImageView mImageView) {
        GlideApp.with(mContext)
                .load(path)
                .placeholder(placeholder)
                .error(error)
                .into(mImageView);
    }

    /**
     * 设置圆形图片
     *
     * @param mContext
     * @param path
     * @param mImageView
     */
    public void loadCircleImageView(final Context mContext, String path, final ImageView mImageView) {
        /*RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.bg_default_female)
                .error(R.mipmap.bg_default_female)
                .centerCrop();

        Glide.with(mContext).asBitmap().load(path).apply(options).into(new BitmapImageViewTarget(mImageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mImageView.setImageDrawable(circularBitmapDrawable);
            }
        });*/

        GlideApp.with(mContext).asBitmap()
                .load(path)
                .placeholder(R.mipmap.bg_default_female)
                .error(R.mipmap.bg_default_female)
                .centerCrop()
                .into(new BitmapImageViewTarget(mImageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        mImageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    /**
     * 根据路径图片加载
     *
     * @param mContext   上下文
     * @param sex        性别
     * @param path       路径
     * @param mImageView ImageView
     */
    public void loadImageView(Context mContext, int sex, String path, ImageView mImageView) {
        GlideApp.with(mContext)
                .load(path)
                .placeholder(sex == Const.SEX_MALE ? R.mipmap.bg_default_male : R.mipmap.bg_default_female)
                .into(mImageView);
    }

    /**
     * 设置圆形图片
     *
     * @param mContext
     * @param sex        性别
     * @param path
     * @param mImageView
     */
    public void loadCircleImageView(final Context mContext, int sex, String path, final ImageView mImageView) {

        GlideApp.with(mContext).asBitmap()
                .load(path)
                .placeholder(sex == Const.SEX_MALE ? R.mipmap.bg_default_male : R.mipmap.bg_default_female)
                .error(R.mipmap.bg_default_female)
                .centerCrop()
                .into(new BitmapImageViewTarget(mImageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        mImageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


}
