package com.example.sun.zhangxun.presenter;

import com.example.sun.zhangxun.app.BasePresenter;
import com.example.sun.zhangxun.bean.News;
import com.example.sun.zhangxun.greendao.GreenDaoBean;
import com.example.sun.zhangxun.greendao.GreenDaoBeanDao;
import com.example.sun.zhangxun.http.MJavascriptInterface;
import com.example.sun.zhangxun.presenter.Constracts.INewDetailConstract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sun on 2017/4/28.
 */

public class NewDetailPresenter extends BasePresenter implements INewDetailConstract.INewDetailPresenter{

    private INewDetailConstract.INewDetailView mView;
    private ArrayList<String> list;

    public NewDetailPresenter(INewDetailConstract.INewDetailView view){
        this.mView = view;

    }


    @Override
    public void parseHtml(final String url) {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements elements = doc.getElementsByTag("img");
                    for (Element link:elements) {
                        String imgUrl = link.attr("src");
                        emitter.onNext(imgUrl);
                    }
                    emitter.onComplete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                        list = new ArrayList<String>();
                    }

                    @Override
                    public void onNext(String s) {
                        list.add(s);
                        System.out.println("imgUrl:"+s);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        MJavascriptInterface.mList=list;
                        mView.addClick();
                    }
                });
    }

    @Override
    public void query(final GreenDaoBeanDao dao, final News.NewInfo mInfo) {
        Observable.create(new ObservableOnSubscribe<GreenDaoBean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<GreenDaoBean> e) throws Exception {
                List<GreenDaoBean> all=dao.queryBuilder().list();
                if (all!=null && all.size()!=0){
                    TAG:
                    for (int i=0;i<all.size();i++){
                        if (all.get(i).getInfo().uniquekey.equals(mInfo.uniquekey)){
                            e.onNext(all.get(i));
                            e.onComplete();
                        }else {
                            continue TAG;
                        }
                    }
                    e.onNext(new GreenDaoBean());
                }else {
                    e.onNext(new GreenDaoBean());
                }
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GreenDaoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(GreenDaoBean bean) {
                        if (bean.getInfo()!=null && bean.getId()!=null){
                            mView.isFavor(bean);
                        }else {
                            mView.isFavor(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.isFavor(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
