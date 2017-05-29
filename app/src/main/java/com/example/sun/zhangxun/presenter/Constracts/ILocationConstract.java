package com.example.sun.zhangxun.presenter.Constracts;

import com.amap.api.services.weather.LocalWeatherLive;

/**
 * Created by sun on 2017/5/10.
 */

public interface ILocationConstract {

    interface ILocationView{

        void showWeather(LocalWeatherLive live);
        void showWeatherError();

    }

    interface ILocationPresenter{


        void updateWeatherNew();
    }
}
