package com.example.sun.zhangxun.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.app.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabContentFragment extends BaseFragment {

    public static final String TABCONTENT_FRAGMENT = "tab_fragment";
    private int type;
    private TextView mTv;

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
            type = (int) getArguments().getSerializable(TABCONTENT_FRAGMENT);
        }
    }

    @Override
    protected void loadData() {
        mTv.setText("fragment---"+type);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mTv= (TextView) view.findViewById(R.id.textView_fragment_content);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_content;
    }


}
