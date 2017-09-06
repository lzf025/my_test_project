package com.ggxueche.utils.photo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.ggxueche.utils.log.L;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yaofclc on 2017/5/9.
 */

public class PhotoUtils {

    public static final int REQUEST_CODE_TAKE_PHOTO = 100;//调用相机的请求码
    public static final int REQUEST_CODE_ALBUM = 101;//打开相册的回掉
    public static PermissionListener permissionListener = null;
    private final int REQUEST_CODE_ZOOM = 102;//裁剪图片的请求
    private String TAG = getClass().getName().toString();
    private Activity activity;
    private String imgPath;
    private Uri outputUri;
    private String FILE_PROVIDER_AUTHORITY;
    private OnPickPhotoFinishback callback;
    private int imageType;

    public PhotoUtils(Activity activity) {
        this.activity = activity;
        FILE_PROVIDER_AUTHORITY = activity.getPackageName() + ".provider";
    }

    /**
     * 设置图片类型 默认jpg
     */
    public void setImageType(int imageType){
        this.imageType = imageType;

    }


    /**
     * 拍照
     */
    public void takePhoto() {
        openCamera();
    }

    /**
     * 打开相册
     */
    public void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, REQUEST_CODE_ALBUM);
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        // 指定调用相机拍照后照片的储存路径
        imgPath = PhotoFileUtils.generateImgePath(imageType);
        L.t(TAG).i("拍照完图片的路径为：" + imgPath);
        File imgFile = new File(imgPath);
        Uri imgUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //如果是7.0或以上
            imgUri = FileProvider.getUriForFile(activity, FILE_PROVIDER_AUTHORITY, imgFile);
        } else {
            imgUri = Uri.fromFile(imgFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        activity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);

    }

    /**
     * 打开相机
     */
    public void openCamera(Fragment fragment) {
        // 指定调用相机拍照后照片的储存路径
        imgPath = PhotoFileUtils.generateImgePath(imageType);
        L.t(TAG).i("拍照完图片的路径为：" + imgPath);
        File imgFile = new File(imgPath);
        Uri imgUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //如果是7.0或以上
            imgUri = FileProvider.getUriForFile(fragment.getContext(), FILE_PROVIDER_AUTHORITY, imgFile);
        } else {
            imgUri = Uri.fromFile(imgFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        fragment.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
    }

    /**
     * 拍照回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data, OnPickPhotoFinishback callback) {
        this.callback = callback;
        switch (resultCode) {
            case Activity.RESULT_OK://调用图片选择处理成功
                Bitmap bm = null;
                File temFile = null;
                File srcFile = null;
                File outPutFile = null;
                switch (requestCode) {
                    case REQUEST_CODE_TAKE_PHOTO:// 拍照后在这里回调
                        String s = PhotoFileUtils.generateImgePath(imageType);
                        L.t(TAG).i("onActivityResult: imgPath ====  " + imgPath + "------" + s + "--------   ");

                        changeBitmapDegree(imgPath);
                        break;
                    case REQUEST_CODE_ALBUM:// 选择相册中的图片
                        if (data != null) {
                            Uri sourceUri = data.getData();
                            String[] proj = {MediaStore.Images.Media.DATA};

                            // 好像是android多媒体数据库的封装接口，具体的看Android文档
                            Cursor cursor = activity.getContentResolver().query(sourceUri, proj, null, null, null);

                            if (cursor != null) {
                                // 按我个人理解 这个是获得用户选择的图片的索引值
                                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                                cursor.moveToFirst();
                                // 最后根据索引值获取图片路径
                                imgPath = cursor.getString(column_index);
                            } else {
                                imgPath = sourceUri.getPath();
                            }

                            if (imgPath != null) {
                                callback.onPickSuccessed(imgPath);
                            } else {
                                callback.onPickFailed();
                            }
                        }
                        break;

                    case RESULT_OK://裁剪后回调
                        if (data != null) {
                            if (outputUri != null) {
                                bm = ImageTools.decodeUriAsBitmap(activity, outputUri);
                                //如果是拍照的,删除临时文件
                                if (imgPath != null) {
                                    temFile = new File(imgPath);
                                    if (temFile.exists()) {
                                        temFile.delete();
                                    }
                                }

                                String scaleImgPath = PhotoFileUtils.saveBitmapByQuality(bm, 80);//进行压缩
                                L.t(TAG).i("压缩后图片的路径为：" + scaleImgPath);
                            }
                        } else {
                            Toast.makeText(activity, "选择图片发生错误，图片可能已经移位或删除", Toast.LENGTH_LONG).show();
                        }
                        break;
                }
                break;
        }

    }

    private void changeBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            L.t(TAG).i("jxf", "orientation" + orientation);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (degree != 0) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            Bitmap returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }

            imgPath = PhotoFileUtils.saveBitmap(returnBm,imageType);
        }
        if (imgPath != null) {
            callback.onPickSuccessed(imgPath);
        } else {
            callback.onPickFailed();
        }

    }

    /**
     * 选择完成后的回调
     */
    public interface OnPickPhotoFinishback {
        /**
         * 成功
         *
         * @param imagePath 图片路径
         */
        void onPickSuccessed(String imagePath);

        void onPickFailed();


    }

}
