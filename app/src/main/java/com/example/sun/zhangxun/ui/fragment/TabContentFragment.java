package com.example.sun.zhangxun.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.app.BaseMvpFragment;
import com.example.sun.zhangxun.app.BasePresenter;
import com.example.sun.zhangxun.bean.News;
import com.example.sun.zhangxun.config.Constants;
import com.example.sun.zhangxun.presenter.Constracts.ILoadDataConstract;
import com.example.sun.zhangxun.presenter.LoadDataPresenter;
import com.example.sun.zhangxun.ui.Adapter.NewsListRecyclerViewAdapter;
import com.example.sun.zhangxun.ui.Adapter.NewsListRecyclerViewTestAdapter;
import com.example.sun.zhangxun.ui.activity.NewDetailActivity;
import com.example.sun.zhangxun.utils.NetworkCheckUtil;
import com.example.sun.zhangxun.utils.permission.PermissionCall;
import com.example.sun.zhangxun.utils.permission.Permissions;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabContentFragment extends BaseMvpFragment implements ILoadDataConstract.ILoadDataView, NewsListRecyclerViewAdapter.onItemClickListener, SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.OnItemClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mTv;
    private RecyclerView mRv;
    public static final String TABCONTENT_FRAGMENT = "tab_fragment";
    private int index;
    private LoadDataPresenter loadDataPresenter;
//    private NewsListRecyclerViewAdapter adapter;
    private NewsListRecyclerViewTestAdapter adapter;
    private List<News.NewInfo> list = new ArrayList<>();
    public String TAG;

    public TabContentFragment() {
        // Required empty public constructor

    }


    public static TabContentFragment newInstance(int type) {
        TabContentFragment fragment = new TabContentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABCONTENT_FRAGMENT, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = (int) getArguments().getSerializable(TABCONTENT_FRAGMENT);
            TAG = Constants.PARAM_URLS[index];
        }
    }

    @Override
    protected void initData() {

        /*
        adapter = new NewsListRecyclerViewAdapter(mActivity, list);
        adapter.setOnItemClickLitener(this);
*/

//        adapter = new NewsListRecyclerViewTestAdapter(mActivity,list);
//        adapter.setOnItemClickListener(this);

        mActivity.requestRunTimePermissions(Permissions.STORAGE, new PermissionCall() {
            @Override
            public void requestSuccess() {
                loadDataPresenter.loadLocalData(TAG);
            }

            @Override
            public void refused() {
                refreshNews();
            }
        },Permissions.STORAGE_REQUEST);


    }

    public void refreshNews() {
        if (NetworkCheckUtil.isNetworkConnected(getContext())) {
            loadDataPresenter.loadData(index,TAG);
        }else {
            hideLoad();
            Toast.makeText(mActivity,"网络未链接",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showRefreshBar() {
        if (mSwipeRefreshLayout.isRefreshing()){
            mTv.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(true);
        }else {
            mTv.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRefreshBar() {
        mTv.setVisibility(View.GONE);
        mRv.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshNewsSucceed(List<News.NewInfo> news) {
        if (news.size() > 0) {
            Toast.makeText(mActivity, "为您更新" + news.size() + "条新闻", Toast.LENGTH_SHORT).show();
            list.addAll(0, news);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(mActivity, "暂时没有新资讯", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mTv = (ProgressBar) view.findViewById(R.id.progressBar_fragment_content);
        mRv = (RecyclerView) view.findViewById(R.id.recyclerView_fragment_news_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout_fragment_news_refresh);
        mRv.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new NewsListRecyclerViewTestAdapter(mActivity,list);
        mRv.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_content;
    }


    @Override
    public void showLoad() {
        mTv.setVisibility(View.VISIBLE);
        mRv.setVisibility(View.GONE);
    }

    @Override
    public void showData(List<News.NewInfo> news) {
        if (news !=null && news.size()!=0){

            list.addAll(0, news);
            adapter.notifyDataSetChanged();
        }else {
//            loadDataPresenter.loadData(index,TAG);
            refreshNews();
        }
    }

    @Override
    public void showFailed(String s) {
        Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadNetData() {
        refreshNews();
    }

    @Override
    public void hideLoad() {
        mRv.setVisibility(View.VISIBLE);
        mTv.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(mActivity, NewDetailActivity.class);
        intent.putExtra("url", list.get(position).url);
        startActivity(intent);
        System.out.println("position:" + position);
        System.out.println(list.get(position).itemType);
    }

    @Override
    public void onRefresh() {
        refreshNews();
    }

    @Override
    public void onInitPresenters() {
        loadDataPresenter = new LoadDataPresenter(this);
    }

    @Override
    public BasePresenter[] getPresenters() {
        return new BasePresenter[]{loadDataPresenter};
    }

    @Override
    public void onPause() {
        super.onPause();
        mSwipeRefreshLayout.setRefreshing(false);
        loadDataPresenter.unSubscribe();
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Intent intent = new Intent(mActivity, NewDetailActivity.class);
        intent.putExtra("url", list.get(i).url);
        intent.putExtra("newInfo",list.get(i));
        startActivity(intent);
    }
}
