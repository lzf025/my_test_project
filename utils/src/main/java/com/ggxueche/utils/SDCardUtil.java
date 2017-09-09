package com.ggxueche.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.lang.ref.SoftReference;

/**
 * SD卡操作工具类
 * Created by yaofclc on 2017/5/9.
 */
public class SDCardUtil {
    public static final int ONE_K = 1024;
    public static final long ONE_M = ONE_K * 1024;
    public static final long ONE_G = ONE_M * 1024;

    public static final String FILE_DIR = "file";
    public static final String ICON_DIR = "icon";
    public static final String APP_STORAGE_ROOT = "ecar";

    /**
     * 判断sd卡是否可用
     *
     * @return
     */
    public static boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable();
    }

    /**
     * 大于50M返回true;
     *
     * @return
     */
    public static boolean isSDCardCanWrite() {
        return isSDCardCanWrite(50);
    }

    /**
     * 传入指定大小，可用空间小于指定大小，或sd卡不可用时，返回false
     *
     * @param minSize
     * @return
     */
    public static boolean isSDCardCanWrite(long minSize) {
        if (isSDCardAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs statfs = new StatFs(path.getPath());
            long blockSize = statfs.getBlockSize();
            long availaBlock = statfs.getAvailableBlocks();
            if (((blockSize * availaBlock) / ONE_M) > minSize) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取SD卡根路径
     *
     * @return
     */
    public static File getSDCardRootDir() {
        return Environment.getExternalStorageDirectory();
    }

    public static String getSDCardRootDirPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 创建文件夹
     */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            boolean mkdirs = file.mkdirs();
            return mkdirs;
        }
        return true;
    }

    /**
     * 获取应用文件根目录
     *
     * @return /sdcard/ecar/
     */
    public static String getAppStorageRoot() {
        String path = getSDCardRootDirPath() + APP_STORAGE_ROOT + File.separator;
        boolean dirs = createDirs(path);
        return path;
    }

    /**
     * 获取应用 文件存储路径
     *
     * @return /sdcard/ecar/file/
     */
    public static String getAppFileDir() {
        String path = getAppStorageRoot() + FILE_DIR + File.separator;
        boolean dirs = createDirs(path);
        return path;
    }

    /**
     * 获取应用图片存储路径
     *
     * @return /sdcard/ecar/icon/
     */
    public static String getAppIconDir() {
        String path = getAppStorageRoot() + ICON_DIR + File.separator;
        createDirs(path);
        return path;
    }
}
