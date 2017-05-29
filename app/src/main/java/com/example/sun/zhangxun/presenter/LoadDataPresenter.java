package com.example.sun.zhangxun.presenter;


import com.example.sun.zhangxun.app.BasePresenter;
import com.example.sun.zhangxun.app.CustomApplication;
import com.example.sun.zhangxun.bean.News;
import com.example.sun.zhangxun.http.ApiException;
import com.example.sun.zhangxun.http.ApiService;
import com.example.sun.zhangxun.http.HttpManager;
import com.example.sun.zhangxun.presenter.Constracts.ILoadDataConstract;
import com.example.sun.zhangxun.utils.DiskCacheManager;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sun on 2017/4/21.
 *
 */

public class LoadDataPresenter extends BasePresenter implements ILoadDataConstract.ILoadDataPresenter {


    private ILoadDataConstract.ILoadDataView view;

    public LoadDataPresenter(ILoadDataConstract.ILoadDataView view){
        this.view = view;

    }

    @Override
    public void loadData(int index, final String TAG) {

        if (view!=null) {
            view.showRefreshBar();
        }

        HttpManager.newInstance(ApiService.BASE_URL2)
                .createApiService(ApiService.class)
                .getNewsRxjava(ApiService.PARAM_URLS[index],ApiService.APP_KEY)
                .map(new Function<News, List<News.NewInfo>>() {
                    @Override
                    public List<News.NewInfo> apply(@NonNull News news) throws Exception {
                        if (news.error_code != 0){
                            throw new ApiException(news);
                        }
                        return news.result.data;
                    }
                })
                .flatMap(new Function<List<News.NewInfo>, ObservableSource<News.NewInfo>>() {
                    @Override
                    public ObservableSource<News.NewInfo> apply(@NonNull List<News.NewInfo> list) throws Exception {
                        return Observable.fromIterable(list);
                    }
                })
                .filter(new Predicate<News.NewInfo>() {
                    @Override
                    public boolean test(@NonNull News.NewInfo newInfo) throws Exception {
                        ArrayList<News.NewInfo> list = new DiskCacheManager(CustomApplication.getContext(), TAG)
                                .getSerializable(TAG);
                        if (list !=null && list.size()!=0){
                            int min;
                            if (list.size()>60){
                                min=60;
                            }else {
                                min = list.size();
                            }
                            TAG:
                            for (int i=0; i < min;i++){
                                if (list.get(i).uniquekey.equals(newInfo.uniquekey)){
                                    return false;
                                }else {
                                    continue TAG;
                                }
                            }
                        }

                        return true;
                    }
                })
                .doOnNext(new Consumer<News.NewInfo>() {
                    @Override
                    public void accept(@NonNull News.NewInfo newInfo) throws Exception {
                        if (newInfo.thumbnail_pic_s03!=null){
                            newInfo.itemType = News.NewInfo.IMG_3;
                        }else {
                            newInfo.itemType = News.NewInfo.IMG_1;
                        }
                    }
                })
                .toList()
                .map(new Function<List<News.NewInfo>, ArrayList<News.NewInfo>>() {
                    @Override
                    public ArrayList<News.NewInfo> apply(List<News.NewInfo> baseItems) {
                        ArrayList<News.NewInfo> items = new ArrayList<>();
                        items.addAll(baseItems);
                        return items;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<News.NewInfo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(@NonNull ArrayList<News.NewInfo> newInfos) {
                        //存入缓存数据中
                        if (newInfos!=null && newInfos.size()!=0){
                            DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(), TAG);
                            ArrayList<News.NewInfo> list = manager.getSerializable(TAG);
                            if (list == null){
                                list= new ArrayList<News.NewInfo>();
                            }
                            list.addAll(0,newInfos);
                            manager.put(TAG, list);
                        }
                        if (view!=null) {
                            view.hideRefreshBar();
                            view.refreshNewsSucceed(newInfos);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (view != null){
                            view.hideRefreshBar();
                            view.showFailed(e.getMessage());
                        }
                    }
                });
    }

    public void loadLocalData(final String TAG) {
        view.showLoad();

        Observable.create(new ObservableOnSubscribe<List<News.NewInfo>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<News.NewInfo>> e) throws Exception {
                DiskCacheManager manager = new DiskCacheManager(CustomApplication.getContext(),TAG);
                ArrayList<News.NewInfo> list = manager.getSerializable(TAG);
                if (list!=null && list.size()!=0){
                    e.onNext(list);
                }else {
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<News.NewInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposable(d);
            }

            @Override
            public void onNext(List<News.NewInfo> list) {
                if (view!=null){
                    view.showData(list);
                    view.hideLoad();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                view.loadNetData();
            }
        });


    }

}
