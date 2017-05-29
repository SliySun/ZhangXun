package com.example.sun.zhangxun.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import com.example.sun.zhangxun.greendao.DaoMaster;
import com.example.sun.zhangxun.greendao.DaoSession;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by sun on 2017/5/2.
 */

public class CustomApplication extends Application {

    public static CustomApplication sApplication;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        if (sApplication == null) {
            sApplication = this;
        }


    /*
        极光推送初始化
     */
        setUpJPush();


        /*
            初始化数据库
         */
        setupDatabase();

        /*
            友盟分享初始化
         */
        UMShareAPI.get(this);
    }

    private void setUpJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static Context getContext() {
        return sApplication.getApplicationContext();
    }

    /**
     * 获取应用的版本号
     *
     * @return 应用版本号，默认返回1
     */
    public static int getAppVersionCode() {
        Context context = getContext();
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "favornews", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }


    {
//        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("1106168430", "nbJlOeRwnTIbPrtS");
        PlatformConfig.setSinaWeibo("3278055204", "311bc4b5eb51ca8f7838c9fd4dd97a03", "http://sns.whalecloud.com");
    }
}
