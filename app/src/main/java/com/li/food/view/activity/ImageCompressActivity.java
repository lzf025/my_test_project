package com.li.food.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ggxueche.utils.SDCardUtil;
import com.ggxueche.utils.ToastUtil;
import com.ggxueche.utils.compress.Compressor;
import com.ggxueche.utils.compress.FileUtil;
import com.ggxueche.utils.photo.PhotoUtils;
import com.li.food.R;
import com.li.food.global.Const;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 图片压缩测试
 * Created by lzf on 2017/9/7.
 */
@Route(path = Const.ACTIVITY_IMAGE_TEST)
public class ImageCompressActivity extends BaseActivity {

    private Activity activity = ImageCompressActivity.this;

    @BindView(R.id.actual_image)
    ImageView actual_image;
    @BindView(R.id.compressed_image)
    ImageView compressed_image;
    @BindView(R.id.choseImage)
    Button choseImage;
    @BindView(R.id.choseCompress)
    Button choseCompress;
    @BindView(R.id.compressed_size)
    TextView compressed_size;


    PhotoUtils mPhotoUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPhotoUtils = new PhotoUtils(activity);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.choseImage)
    public void takePhoto() {
        mPhotoUtils.takePhoto();
    }
    @OnClick(R.id.choseCompress)
    public void takePhoto2() {
        mPhotoUtils.openAlbum();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mPhotoUtils != null) {
            mPhotoUtils.onActivityResult(requestCode, resultCode, data, mPickPhotoCallback);
        }

        if (resultCode == RESULT_OK) {
            if (data != null) {

            }
        }
    }

    PhotoUtils.OnPickPhotoFinishback mPickPhotoCallback = new PhotoUtils.OnPickPhotoFinishback() {
        @Override
        public void onPickSuccessed(String imagePath) {
            if (isFinishing()) {
                return;
            }
            /*Bitmap bitmap = new Compressor.Builder(activity)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .build()
                    .compressToBitmap(new File(imagePath));
            compressed_image.setImageBitmap(bitmap);
            int a = bitmap.getRowBytes() * bitmap.getHeight();
            System.out.print(a);*/
            new Compressor.Builder(activity)
                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
                    .build()
                    .compressToFile(new File(imagePath));
//            saveBitmap(bitmap,SDCardUtil.getAppFileDir());
            /*String filePath = SDCardUtil.getAppFileDir() + imagePath;
            compressed_size.setText(getReadableFileSize(new File(filePath).length()));*/
        }

        @Override
        public void onPickFailed() {
            ToastUtil.showToast(mActivity, "选择图片发生错误，图片可能已经移位或删除");
        }
    };
    /**
     * 保存Bitmap到本地
     * @param mBitmap

     * @return
     */
    public static String saveBitmap(Bitmap mBitmap, String savePath) {
        File filePic;
        try {
            filePic = new File(savePath+"aaa.jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }
    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
