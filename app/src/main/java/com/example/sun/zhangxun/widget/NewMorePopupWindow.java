package com.example.sun.zhangxun.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sun.zhangxun.R;

import java.util.ArrayList;

/**
 * Created by sun on 2017/5/16.
 */

public class NewMorePopupWindow extends PopupWindow{

    private Context mContext;

    private View mView;

    public NewMorePopupWindow(Context context){
        this.mContext = context;

        this.mView = View.inflate(context, R.layout.layout_popup_win_new,null);


        initView(this.mView);
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.mView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mView.findViewById(R.id.pop_layout).getTop();

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
        this.setContentView(this.mView);
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

    private ArrayList<View> list = new ArrayList<>();
    private TextView tv_back;

    private void initView(View mView) {


        View view1=initItemView(R.id.item1_popup_win_sina,R.drawable.umeng_socialize_sina,"新浪");
        View view2=initItemView(R.id.item1_popup_win_wxfriends,R.drawable.umeng_socialize_wxcircle,"朋友圈");
        View view3=initItemView(R.id.item1_popup_win_wxfriend,R.drawable.umeng_socialize_wechat,"微信");
        View view4=initItemView(R.id.item1_popup_win_qq,R.drawable.umeng_socialize_qq,"qq");
        View view5=initItemView(R.id.item1_popup_win_qzone,R.drawable.umeng_socialize_qzone,"qq空间");
        View view6=initItemView(R.id.item1_popup_win_gmail,R.drawable.umeng_socialize_gmail,"邮箱");
        View view7=initItemView(R.id.item1_popup_win_sms,R.drawable.umeng_socialize_sms,"信息");
        View view8=initItemView(R.id.item1_popup_win_favor,R.drawable.b_newcare_tabbar,"收藏");
        tv_back = (TextView) mView.findViewById(R.id.button_popWin_back);


        list.add(view1);
        list.add(view2);
        list.add(view3);
        list.add(view4);
        list.add(view5);
        list.add(view6);
        list.add(view7);
        list.add(view8);


    }

    private View initItemView(int id, int resId, String text) {

        View view=mView.findViewById(id);

        ImageView iv= (ImageView) view.findViewById(R.id.socialize_image_view);
        TextView tv= (TextView) view.findViewById(R.id.socialize_text_view);
        iv.setImageResource(resId);
        tv.setText(text);
        return view;
    }

    public void setOnItemClickLstener(View.OnClickListener listener) {

        for (View view:list) {
            view.setOnClickListener(listener);
        }
        tv_back.setOnClickListener(listener);
    }

    public void setFavorButtonState(boolean isFavor) {
        ImageView iv = getImageView();
        if (isFavor){
            Glide.with(mContext).load(R.drawable.b_newcare_tabbar_press).into(iv);
        }else {
            Glide.with(mContext).load(R.drawable.b_newcare_tabbar).into(iv);
        }
    }

    private ImageView getImageView() {
        return (ImageView) list.get(7).findViewById(R.id.socialize_image_view);
    }

}
