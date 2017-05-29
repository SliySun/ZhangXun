package com.example.sun.zhangxun.utils;

import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.example.sun.zhangxun.app.CustomApplication;
import com.example.sun.zhangxun.http.HttpManager;

/**
 * Created by sun on 2017/5/10.
 */

public class WeatherManager {

    private static WeatherManager manager;

    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;


    private WeatherManager(String city){

        mquery = new WeatherSearchQuery(city, WeatherSearchQuery.WEATHER_TYPE_LIVE);//检索参数为城市和天气类型，实时天气为1、天气预报为2
        mweathersearch=new WeatherSearch(CustomApplication.getContext());
        mweathersearch.setQuery(mquery);
    }

    public static WeatherManager newInstance(String city){
        if (manager == null){
            synchronized (HttpManager.class){
                if (manager == null){
                    manager = new WeatherManager(city);
                }
            }
        }
        return manager;

    }

    public WeatherManager setOnWeatherSearchListener(WeatherSearch.OnWeatherSearchListener onWeatherSearchListener) {
        mweathersearch.setOnWeatherSearchListener(onWeatherSearchListener);
        return this;
    }

    public void searchWeatherAsyn() {
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }
}
