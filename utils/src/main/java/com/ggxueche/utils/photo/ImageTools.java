package com.ggxueche.utils.photo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;

/**
 * Created by yaofclc on 2017/5/9.
 */

public class ImageTools {

    private static int DEFAULT_IMAGE_SIZE = 320;

    public static Bitmap decodeUriAsBitmap(Activity activity, Uri uri) {
        /*Bitmap bitmap = null;
        try {
            // 先通过getContentResolver方法获得一个ContentResolver实例，
            // 调用openInputStream(Uri)方法获得uri关联的数据流stream
            // 把上一步获得的数据流解析成为bitmap
            bitmap = BitmapFactory.decodeStream(AppApplication.context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;*/

        Bitmap bitmap = null;
        if (uri == null) {
            return null;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            bitmap = BitmapFactory.decodeStream(activity.getContentResolver()
                    .openInputStream(uri), null, options);
            options.inJustDecodeBounds = false;
            int rateX = (int) (options.outWidth / (float) DEFAULT_IMAGE_SIZE);
            int rateY = (int) (options.outHeight / (float) DEFAULT_IMAGE_SIZE);
            options.inSampleSize = Math.max(rateX, rateY);
            bitmap = BitmapFactory.decodeStream(activity.getContentResolver()
                    .openInputStream(uri), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
