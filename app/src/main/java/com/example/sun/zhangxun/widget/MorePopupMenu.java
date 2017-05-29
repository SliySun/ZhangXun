package com.example.sun.zhangxun.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sun.zhangxun.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sun on 2017/5/12.
 */

public class MorePopupMenu extends PopupWindow {

    private Context mContext;

    private CustomSeekbar seekbar;
    private TextView tv_back;
    private ImageView iv_favor;
    private ImageView iv_sina;
    private ImageView iv_sms;
    private ImageView iv_wxf;
    private ImageView iv_wxfs;



    private View view;

    public MorePopupMenu(Context context){
        this.mContext = context;

        this.view = LayoutInflater.from(context).inflate(R.layout.layout_pop_detail_more,null);

        initView(this.view);
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffefefef);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

        //设置背景半透明
//        backgroundAlpha(0.5f);

    }

    private void initView(View view) {
        seekbar = (CustomSeekbar) view.findViewById(R.id.customSeekbar_popWin);
        tv_back = (TextView) view.findViewById(R.id.button_popWin_back);
        iv_favor = (ImageView) view.findViewById(R.id.button_popWin_favor);
        iv_sina = (ImageView) view.findViewById(R.id.button_popWin_share_sina);
        iv_sms = (ImageView) view.findViewById(R.id.button_popWin_share_sms);
        iv_wxf = (ImageView) view.findViewById(R.id.button_popWin_share_wx_friend);
        iv_wxfs = (ImageView) view.findViewById(R.id.button_popWin_share_wx_friends);
        initSeekbar(seekbar);
    }

    private void initSeekbar(CustomSeekbar seekbar) {
        ArrayList<String> volume_sections = new ArrayList<String>();
        volume_sections.add("小");
        volume_sections.add("中");
        volume_sections.add("大");
        volume_sections.add("特大");
        seekbar.initData(volume_sections);
//        seekbar.setProgress(2);
//        seekbar.setResponseOnTouch(this);
    }


    public CustomSeekbar getSeekbar() {
        return this.seekbar;
    }


    public void setSeekbarProgress(WebSettings.TextSize size) {
        if (size== WebSettings.TextSize.SMALLER){
            this.seekbar.setProgress(0);

        }else if (size == WebSettings.TextSize.NORMAL){
            this.seekbar.setProgress(1);

        }else if (size == WebSettings.TextSize.LARGER){
            this.seekbar.setProgress(2);

        }else if (size == WebSettings.TextSize.LARGEST){
            this.seekbar.setProgress(3);
        }
    }

    public void setSeekbarResponseOnTouch(CustomSeekbar.ResponseOnTouch responseOnTouch) {
        this.seekbar.setResponseOnTouch(responseOnTouch);
    }

    public void setOnItemClickLstener(View.OnClickListener listener) {
        tv_back.setOnClickListener(listener);
        iv_favor.setOnClickListener(listener);
        iv_sina .setOnClickListener(listener);
        iv_sms .setOnClickListener(listener);
        iv_wxf .setOnClickListener(listener);
        iv_wxfs.setOnClickListener(listener);

    }

    public void setFavorButtonState(boolean isFavor) {
        if (isFavor){
            Glide.with(mContext).load(R.drawable.b_newcare_tabbar_press).into(iv_favor);
        }else {
            Glide.with(mContext).load(R.drawable.b_newcare_tabbar).into(iv_favor);
        }
    }
}
