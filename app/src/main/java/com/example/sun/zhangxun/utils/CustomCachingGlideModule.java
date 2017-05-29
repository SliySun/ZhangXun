package com.example.sun.zhangxun.utils;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by sun on 2017/5/10.
 *
 */

public class CustomCachingGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置图片的显示格式ARGB_8888(指图片大小为32bit)
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //设置磁盘缓存目录（和创建的缓存目录相同）
        String downloadDirectoryPath=getCacheFile(context).getPath();

        //设置缓存的大小为100M

        int cacheSize = 100*1000*1000;
        builder.setDiskCache(new DiskLruCacheFactory(
                downloadDirectoryPath, cacheSize
            )
        );
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }

    private File getCacheFile(Context context) {
        String cachePath ;
        if ((Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable())
                && context.getExternalCacheDir() != null) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + "GlideImgCache");
    }
}
