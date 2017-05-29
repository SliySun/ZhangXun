package com.example.sun.zhangxun.app;

import android.os.Bundle;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sun on 2017/5/8.
 *
 */

public abstract class BaseMvpActivity extends BaseActivity {

    private Set<BasePresenter> mSet =  new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onInitPresenters();
        addPresenters();

        initData();
    }

    /**
     * 初始化presenters
     */
    protected  void onInitPresenters(){}

    /**
     * 添加 presenters 到set 中 用于遍历销毁
     */
    private void addPresenters() {
        BasePresenter[] presenters = getPresenters();
        if (presenters != null) {
            for (int i = 0; i < presenters.length; i++) {
                mSet.add(presenters[i]);
            }
        }
    }

    /**
     * 需要子类来实现，获取子类的IPresenter，一个activity有可能有多个IPresenter
     */
    protected  BasePresenter[] getPresenters(){
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (BasePresenter presenter:mSet) {
            //退出时销毁持有Activity
            presenter.unSubscribe();
        }
    }
}
