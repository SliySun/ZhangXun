package com.example.sun.zhangxun.http;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.sun.zhangxun.BuildConfig;
import com.example.sun.zhangxun.config.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sun on 2017/4/21.
 *
 */

public class HttpManager {
    private static Retrofit mRetrofit;
    private static HttpManager manager;
    private static Context mContext;
    private static final int DEFAULT_TIMEOUT = 5;

    public static void init(Context context){
        mContext = context;
    }

    private HttpManager(String baseUrl){

        OkHttpClient okHttpClient = getOkHttpClient();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @NonNull
    public static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            // Log信息拦截器
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor);

        return builder.build();
    }

    public static HttpManager newInstance(String baseUrl){
        if (manager == null){
            synchronized (HttpManager.class){
                if (manager == null){
                    manager = new HttpManager(baseUrl);
                }
            }
        }
        return manager;
    }


    public static Interceptor getToutiaoRequestkey() {
        return new Interceptor() {

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

                //设置通用请求参数
                Request request;
                HttpUrl httpUrl = originalRequest.url().newBuilder().addQueryParameter("key", "a611e344442a2f60d53941f21d03b9db").build();
                request = originalRequest.newBuilder().url(httpUrl).build();

                return chain.proceed(request);
            }

        };

//        return headerInterceptor;
    }

    public ApiService createApiService(Class<ApiService> apiServiceClass) {
        return mRetrofit.create(apiServiceClass);
    }


}
