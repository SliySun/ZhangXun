package com.example.sun.zhangxun.presenter.Constracts;

import com.example.sun.zhangxun.bean.News;

import java.util.List;

/**
 * Created by sun on 2017/4/21.
 */

public interface ILoadDataConstract {

    interface ILoadDataView {

        void showLoad();
        void hideLoad();

        void showData(List<News.NewInfo> news);

        void refreshNews();
        void showRefreshBar();
        void hideRefreshBar();
        void refreshNewsSucceed(List<News.NewInfo> news);

        void showFailed(String s);


        void loadNetData();
    }

    interface ILoadDataPresenter {
        void loadData(int index,String TAG);
    }

}
