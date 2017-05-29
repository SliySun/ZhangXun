package com.example.sun.zhangxun.presenter;

import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.example.sun.zhangxun.app.BasePresenter;
import com.example.sun.zhangxun.presenter.Constracts.ILocationConstract;
import com.example.sun.zhangxun.utils.LocationManager;
import com.example.sun.zhangxun.utils.LocationUtils;
import com.example.sun.zhangxun.utils.WeatherManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sun on 2017/5/10.
 *
 */

public class LocationPresenter extends BasePresenter implements ILocationConstract.ILocationPresenter {

    private ILocationConstract.ILocationView view;
    private LocalWeatherLive weatherlive;

    public LocationPresenter(ILocationConstract.ILocationView view){
        this.view = view;
    }


    public void updateWeatherNew(){


         Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> e) throws Exception {
                LocationManager.newInstance().setLocationListener( new AMapLocationListener(){
                    @Override
                    public void onLocationChanged(AMapLocation aMapLocation) {

                        if(null == aMapLocation){
                            e.onError(new Throwable("定位失败"));
                        } else {
                            System.out.println(aMapLocation.getCity());
                            e.onNext(aMapLocation.getCity());
                        }
                    }
                }).startLocation();
            }
        })
                 .flatMap(new Function<String, ObservableSource<LocalWeatherLive>>() {

             @Override
             public ObservableSource<LocalWeatherLive> apply(@NonNull final String s) throws Exception {
                 System.out.println(s);
                 return Observable.create(new ObservableOnSubscribe<LocalWeatherLive>() {
                     @Override
                     public void subscribe(@NonNull final ObservableEmitter<LocalWeatherLive> e) throws Exception {
                         WeatherManager.newInstance(s).setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener(){
                             @Override
                             public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
                                 if (i == AMapException.CODE_AMAP_SUCCESS) {
                                     if (localWeatherLiveResult != null && localWeatherLiveResult.getLiveResult() != null) {
                                         weatherlive = localWeatherLiveResult.getLiveResult();
                                         e.onNext(weatherlive);
                                     }else {
                                         e.onError(new Throwable("对不起，没有搜索到相关数据！"));
                                     }
                                 }else {
                                     e.onError(new AMapException());
                                 }
                             }

                             @Override
                             public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

                             }
                         }).searchWeatherAsyn();
                     }
                 });
             }
         })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LocalWeatherLive>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(LocalWeatherLive live) {
                        System.out.println(live.getWeather());
                        view.showWeather(live);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showWeatherError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

}
