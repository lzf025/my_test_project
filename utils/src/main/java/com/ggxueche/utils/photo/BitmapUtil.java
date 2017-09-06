package com.ggxueche.utils.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.SparseArray;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BitmapUtil {
    public static int max = 0;
//    public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();
    private ArrayList<ImageItem> tempSelectBitmap;

    public BitmapUtil(ArrayList<ImageItem> tempSelectBitmap) {
        this.tempSelectBitmap = tempSelectBitmap;
    }

    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /***
     *
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static boolean deleteTempFile(String path) throws IOException {
        boolean isOk = true;
        File file = new File(path);
        if (file != null) {
            if (file.exists()) {
                if (!file.delete()) {
                    isOk = false;
                }
            }
        }
        return isOk;
    }

    public ArrayList<ImageItem> getTempSelectBitmap() {
        return tempSelectBitmap;
    }

    public SparseArray<ImageItem> getUploadBitmap(){
        SparseArray<ImageItem> uploadImage = new SparseArray<>();
        for (int i=0; i< tempSelectBitmap.size();i++)
        {
            ImageItem imageItem = tempSelectBitmap.get(i);
            if (TextUtils.isEmpty(imageItem.getImagePath())){
                continue;
            }
            uploadImage.put(i,imageItem);
        }

        return uploadImage;
    }

}
