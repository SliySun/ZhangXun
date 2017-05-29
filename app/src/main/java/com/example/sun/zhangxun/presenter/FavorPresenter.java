package com.example.sun.zhangxun.presenter;

import com.example.sun.zhangxun.app.BasePresenter;
import com.example.sun.zhangxun.bean.News;
import com.example.sun.zhangxun.greendao.GreenDaoBean;
import com.example.sun.zhangxun.greendao.GreenDaoBeanDao;
import com.example.sun.zhangxun.presenter.Constracts.IFavorConstract;

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
import io.reactivex.internal.observers.ConsumerSingleObserver;
import io.reactivex.internal.operators.single.SingleToObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sun on 2017/5/14.
 */

public class FavorPresenter extends BasePresenter implements IFavorConstract.IFavorPresenter {

    private IFavorConstract.IFavorView view;
    private ArrayList<GreenDaoBean> list;

    public FavorPresenter(IFavorConstract.IFavorView view){
        this.view = view;
    }

    @Override
    public void loadFromDb(final GreenDaoBeanDao dao) {

        view.showLoad();
        Observable.create(new ObservableOnSubscribe<GreenDaoBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<GreenDaoBean> e) throws Exception {
        System.out.println("loadFromDb");
                List<GreenDaoBean> all=dao.queryBuilder().list();
                System.out.println(all);
                for (GreenDaoBean bean:all) {

                    e.onNext(bean);
                }
                e.onComplete();

            }
        })
//                .map(new Function<GreenDaoBean, News.NewInfo>() {
//                    @Override
//                    public News.NewInfo apply(@NonNull GreenDaoBean greenDaoBean) throws Exception {
//                        return greenDaoBean.getInfo();
//                    }
//                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GreenDaoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                        list = new ArrayList<GreenDaoBean>();
                    }

                    @Override
                    public void onNext(GreenDaoBean info) {
                        list.add(info);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("newinfo"+list);
                        if(list!= null && list.size()!=0){

                        view.showDataSucceed(list);
                        }else {
                            view.showDataFailed("暂无收藏");
                        }
                    }
                });
        ;
    }
}
