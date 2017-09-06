package com.li.food.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.ggxueche.utils.GlideImgManager;
import com.li.food.R;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class ImagerActivity extends AppCompatActivity {
    String imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502454236909&di=4b5456b8d6611d378578100e332415e6&imgtype=0&src=http%3A%2F%2Fwww.qianon.com%2Ffile%2Fupload%2F201410%2F19%2F19-30-44-10-1.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imager);
        initLoader();
        ImageView cookPic = (ImageView) findViewById(R.id.cookPic);
        final ImageView cookPicImagerLoader = (ImageView) findViewById(R.id.cookPicImagerLoader);
        GlideImgManager.getInstance().loadImageView(this, imgUrl,cookPic);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.image_progress) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.image_progress) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.image_progress) // 设置图片加载或解码过程中发生错误显示的图片
                .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
                .delayBeforeLoading(1000)  // 下载前的延迟时间
                .cacheInMemory(false) // default  设置下载的图片是否缓存在内存中
                .cacheOnDisk(false) // default  设置下载的图片是否缓存在SD卡中
                .build();
        ImageLoader.getInstance().displayImage(imgUrl,cookPicImagerLoader, options);
/*        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = getHttpBitmap(imgUrl);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cookPicImagerLoader.setImageBitmap(bitmap);
                    }
                });

            }
        }).start();*/


    }
    private void initLoader(){
        File cacheDir = StorageUtils.getCacheDirectory(ImagerActivity.this);  //缓存文件夹路径
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ImagerActivity.this)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions 内存缓存文件的最大长宽
                .diskCacheExtraOptions(480, 800, null)  // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100)  // 可以缓存的文件数量
                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(ImagerActivity.this)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs() // 打印debug log
                .build(); //开始构建
        ImageLoader.getInstance().init(config);
    }
}
