package com.example.sun.zhangxun.presenter.Constracts;

import com.example.sun.zhangxun.bean.News;
import com.example.sun.zhangxun.greendao.GreenDaoBean;
import com.example.sun.zhangxun.greendao.GreenDaoBeanDao;

import java.util.List;

/**
 * Created by sun on 2017/5/14.
 */

public interface IFavorConstract {

    interface IFavorView{

        void showDataSucceed(List<GreenDaoBean> list);

        void showDataFailed(String message);

        void showLoad();
    }

    interface IFavorPresenter{

        void loadFromDb(GreenDaoBeanDao dao);
    }
}
