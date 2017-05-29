package com.example.sun.zhangxun.presenter.Constracts;


import com.example.sun.zhangxun.bean.News;
import com.example.sun.zhangxun.greendao.GreenDaoBean;
import com.example.sun.zhangxun.greendao.GreenDaoBeanDao;

/**
 * Created by sun on 2017/4/28.
 *
 */

public interface INewDetailConstract {

    interface INewDetailView {


        void isFavor(GreenDaoBean b);

        void addClick();
    }

    interface INewDetailPresenter {

        void parseHtml(String url);

        void query(GreenDaoBeanDao dao, News.NewInfo mInfo);
    }
}
