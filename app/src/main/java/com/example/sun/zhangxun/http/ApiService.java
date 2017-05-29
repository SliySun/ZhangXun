package com.example.sun.zhangxun.http;

import com.example.sun.zhangxun.bean.News;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sun on 2017/4/21.
 */

public interface ApiService {

    String BASE_URL2 = "http://v.juhe.cn/toutiao/";
    String APP_KEY="a611e344442a2f60d53941f21d03b9db";
    String[] PARAM_URLS = {"top","shehui","guonei","guoji","yule","tiyu","junshi","keji","caijing","shishang"};

    @GET("index")
    Observable<News> getNewsRxjava(@Query("type") String type ,@Query("key") String key);
}
