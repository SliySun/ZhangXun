package com.example.sun.zhangxun.presenter.Constracts;

import com.example.sun.zhangxun.app.BasePresenter;

/**
 * Created by sun on 2017/5/27.
 */

public interface ISavePhotoConstract {

    interface  ISavaPhotoView {
        void showSucceed(String path);
        void showFailed(String msg);
    }

    interface ISavePhotoPresenter {
        void save(String url);
    }
}
