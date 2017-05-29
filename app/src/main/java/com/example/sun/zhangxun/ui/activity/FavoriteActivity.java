package com.example.sun.zhangxun.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.sun.zhangxun.Animator.MyDefaultItemAnimator;
import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.app.BaseMvpActivity;
import com.example.sun.zhangxun.app.BasePresenter;
import com.example.sun.zhangxun.app.CustomApplication;
import com.example.sun.zhangxun.app.SwipeBackMvpActivity;
import com.example.sun.zhangxun.bean.News;
import com.example.sun.zhangxun.greendao.DaoSession;
import com.example.sun.zhangxun.greendao.GreenDaoBean;
import com.example.sun.zhangxun.greendao.GreenDaoBeanDao;
import com.example.sun.zhangxun.presenter.Constracts.IFavorConstract;
import com.example.sun.zhangxun.presenter.FavorPresenter;
import com.example.sun.zhangxun.ui.Adapter.FavorNewsListRecyclerViewAdapter;
import com.example.sun.zhangxun.ui.Adapter.NewsListRecyclerViewTestAdapter;
import com.example.sun.zhangxun.utils.NetworkCheckUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoriteActivity extends SwipeBackMvpActivity implements IFavorConstract.IFavorView,BaseQuickAdapter.OnItemClickListener,View.OnClickListener,BaseQuickAdapter.OnItemChildClickListener{

    private ImageView mIv_back;
    private TextView mTv_title;
    private TextView mTv_edit;
    private RecyclerView mRv_list;
    private TextView mTv_tip;
    private RelativeLayout mRl_del;
    private TextView mTv_del;

    private FavorPresenter mPresenter;

    private GreenDaoBeanDao dao;

    private FavorNewsListRecyclerViewAdapter mAdapter;

    private List<News.NewInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mIv_back = (ImageView) findViewById(R.id.imageView_activity_favor_back);
        mTv_title = (TextView) findViewById(R.id.textView_activity_favor_title);
        mTv_edit = (TextView) findViewById(R.id.textView_activity_favor_edit);
        mRv_list = (RecyclerView) findViewById(R.id.recyclerView_activity_favor);
        mTv_tip = (TextView) findViewById(R.id.textView_activity_favor_tip);
        mRl_del = (RelativeLayout) findViewById(R.id.relativeLayout_activity_favor_delete);
        mTv_del = (TextView) findViewById(R.id.textView_activity_favor_delete);
        mRv_list.setLayoutManager(new LinearLayoutManager(this));
        mRv_list.setItemAnimator(new MyDefaultItemAnimator());
    }

    @Override
    protected void setListener() {
        mTv_edit.setOnClickListener(this);
        mTv_del.setOnClickListener(this);
        mIv_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {


        initDb();

        getDataFromDb();
    }

    private void getDataFromDb() {
        System.out.println(NetworkCheckUtil.isNetworkConnected(this));
        if (NetworkCheckUtil.isNetworkConnected(this)) {
            mPresenter.loadFromDb(dao);
        }else {
            showDataFailed("网络未连接");
        }
    }

    private void initDb() {
        DaoSession daoSession = CustomApplication.getDaoInstant();
        dao = daoSession.getGreenDaoBeanDao();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_favorite;
    }

    @Override
    protected void onInitPresenters() {
        mPresenter = new FavorPresenter(this);
    }

    @Override
    protected BasePresenter[] getPresenters() {
        return new BasePresenter[]{mPresenter};
    }


    private List<GreenDaoBean> greenDaoBeen;
    private List<GreenDaoBean> delGreenDaoBean = new ArrayList<>();
    @Override
    public void showDataSucceed(List<GreenDaoBean> list) {
        mRv_list.setVisibility(View.VISIBLE);
        mTv_tip.setVisibility(View.GONE);
        mTv_edit.setEnabled(true);
        this.greenDaoBeen = list;
        this.list = toNewInfoList(list);
        mAdapter = new FavorNewsListRecyclerViewAdapter(this,this.list);
        mRv_list.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);

    }

    private List<News.NewInfo> toNewInfoList(List<GreenDaoBean> list) {
        List<News.NewInfo> temp = new ArrayList<>();
        for (GreenDaoBean bean:list) {
            temp.add(bean.getInfo());
        }
        return temp;
    }

    @Override
    public void showDataFailed(String message) {
        mRv_list.setVisibility(View.GONE);
        mTv_tip.setVisibility(View.VISIBLE);
        mTv_edit.setEnabled(false);
        mTv_tip.setText(message);
    }

    @Override
    public void showLoad() {
        mTv_tip.setVisibility(View.VISIBLE);
        mRv_list.setVisibility(View.GONE);
        mTv_tip.setText("正在加载中...");
    }


    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (mAdapter.getShow()){

            mAdapter.setSelectItem(i);
            System.out.println(mAdapter.isChecked(i));
            if (mAdapter.isChecked(i)){
                delList.add(list.get(i));
                delGreenDaoBean.add(greenDaoBeen.get(i));
            }else {
                delList.remove(list.get(i));
                delGreenDaoBean.remove(greenDaoBeen.get(i));
            }
            updateTvDelState();
        }else {
            News.NewInfo info = list.get(i);
            Intent intent = new Intent(FavoriteActivity.this, NewDetailActivity.class);
            intent.putExtra("url", info.url);
            intent.putExtra("newInfo",info);
            startActivity(intent);
        }
    }

    private void updateTvDelState() {
        if (delList.size()!=0){
            mTv_del.setEnabled(true);
            mTv_del.setText("删除("+delList.size()+")");
            mTv_del.setTextColor(Color.RED);
        }else {
            mTv_del.setEnabled(false);
            mTv_del.setText("删除");
            mTv_del.setTextColor(getResources().getColor(R.color.grey_text_5d5c5c));
        }
    }

    private List<News.NewInfo> delList = new ArrayList<>();

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textView_activity_favor_edit:
                mAdapter.setShowBox();
                mAdapter.notifyDataSetChanged();
                if (mAdapter.getShow()){
                    mTv_title.setText("编辑收藏");
                    mRl_del.setVisibility(View.VISIBLE);
                    mTv_del.setText("删除");
                    mTv_del.setEnabled(false);
                }else {
                    mTv_title.setText("收藏");
                    mRl_del.setVisibility(View.GONE);
                    mTv_del.setEnabled(false);
                }
                break;
            case R.id.textView_activity_favor_delete:
                showTipDialog();
                break;
            case R.id.imageView_activity_favor_back:
                this.finish();
        }
    }

    private void showTipDialog() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= getLayoutInflater().inflate(R.layout.layout_tip_dialog,null);
        builder.setView(view);
        TextView tv_tip = (TextView) view.findViewById(R.id.textView_dialog_tip);

        tv_tip.setText("确定删除"+delList.size()+"条收藏吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                list.removeAll(delList);
                dao.deleteInTx(delGreenDaoBean);
                mAdapter.notifyDataSetChanged();
                mAdapter.initMap(list);
                delList.clear();
                delGreenDaoBean.clear();
                updateTvDelState();

            }
        })
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.pull_left_out);
    }
}
