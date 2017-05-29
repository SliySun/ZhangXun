package com.example.sun.zhangxun.ui.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.bean.News;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by sun on 2017/5/14.
 */

public class FavorNewsListRecyclerViewAdapter extends BaseMultiItemQuickAdapter<News.NewInfo,BaseViewHolder> {

    private Context context;

    //是否显示单选框,默认false
    private boolean isshowBox = false;
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> map = new HashMap<>();


    public FavorNewsListRecyclerViewAdapter(Context context,List<News.NewInfo> data) {
        super(data);
        this.context = context;
        addItemType(News.NewInfo.IMG_1, R.layout.item_recyclerview_new_list_1pic_favor);
        addItemType(News.NewInfo.IMG_3, R.layout.item_recyclerview_new_list_3pic_favor);
//        initMap(data);
    }

    //初始化map集合,默认为不选中
    public void initMap(List<News.NewInfo> data) {
        for (int i = 0; i < data.size(); i++) {
            map.put(i, false);
        }
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, News.NewInfo info) {
        final int p1=baseViewHolder.getPosition();
        int p2=baseViewHolder.getAdapterPosition();
        System.out.println(" "+p1+" "+p2);
        switch (baseViewHolder.getItemViewType()){
            case News.NewInfo.IMG_1:

                ImageView iv_right = baseViewHolder.getView(R.id.imageView_item_new_right_1pic);
                baseViewHolder.setText(R.id.textView_item_new_title_1pic,info.title)
                        .setText(R.id.textView_item_new_author_1pic,info.author_name)
                        .setText(R.id.textView_item_new_time_1pic,info.date);

                Glide.with(context)
                        .load(info.thumbnail_pic_s)
                        .placeholder(R.drawable.image_loading)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_right);

                //显示／隐藏
                if (isshowBox){
                    baseViewHolder.getView(R.id.checkbox_item_1pic_favor).setVisibility(View.VISIBLE);
                    baseViewHolder.getView(R.id.item_include_recyclerview_new_list_1pic).setBackgroundColor(mContext.getResources().getColor(R.color.item_normal));

                }else {
                    baseViewHolder.getView(R.id.checkbox_item_1pic_favor).setVisibility(View.GONE);
                    baseViewHolder.getView(R.id.item_include_recyclerview_new_list_1pic).setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_selector));
                }


                //设置checkBox改变监听
//                baseViewHolder.setOnCheckedChangeListener(R.id.checkbox_item_1pic_favor, new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        System.out.println("onCheckedChanged");
//                        map.put(p1,b);
//
//                    }
//                });

                // 设置CheckBox的状态
                if (map.get(p1) == null) {
                    map.put(p1, false);
                }
                baseViewHolder.setChecked(R.id.checkbox_item_1pic_favor,map.get(p1));
                break;
            case News.NewInfo.IMG_3:


                ImageView iv_1 = baseViewHolder.getView(R.id.imageView_item_new_img1_3pic);
                ImageView iv_2 = baseViewHolder.getView(R.id.imageView_item_new_img2_3pic);
                ImageView iv_3 = baseViewHolder.getView(R.id.imageView_item_new_img3_3pic);

                baseViewHolder.setText(R.id.textView_item_new_title_3pic,info.title)
                        .setText(R.id.textView_item_new_author_3pic,info.author_name)
                        .setText(R.id.textView_item_new_time_3pic,info.date);


                Glide.with(context).load(info.thumbnail_pic_s)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.image_loading).into(iv_1);
                Glide.with(context).load(info.thumbnail_pic_s02).placeholder(R.drawable.image_loading)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_2);
                Glide.with(context).load(info.thumbnail_pic_s03).placeholder(R.drawable.image_loading)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_3);

                //显示／隐藏
                if (isshowBox){
                    baseViewHolder.getView(R.id.checkbox_item_3pic_favor).setVisibility(View.VISIBLE);
                    baseViewHolder.getView(R.id.item_include_recyclerview_new_list_3pic).setBackgroundColor(mContext.getResources().getColor(R.color.item_normal));
                }else {
                    baseViewHolder.getView(R.id.checkbox_item_3pic_favor).setVisibility(View.GONE);
                    baseViewHolder.getView(R.id.item_include_recyclerview_new_list_3pic).setBackground(mContext.getResources().getDrawable(R.drawable.bg_item_selector));
                }


                //设置checkBox改变监听
//                baseViewHolder.setOnCheckedChangeListener(R.id.checkbox_item_3pic_favor, new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        System.out.println("onCheckedChanged");
//                        map.put(p1,b);
//                    }
//                });


                // 设置CheckBox的状态
                if (map.get(p1) == null) {
                    map.put(p1, false);
                }
                baseViewHolder.setChecked(R.id.checkbox_item_3pic_favor,map.get(p1));
                break;
            default:
                break;

        }
    }

    //设置是否显示CheckBox
    public void setShowBox() {
        //取反
        isshowBox = !isshowBox;
    }

    //点击item选中CheckBox
    public void setSelectItem(int position) {
        //对当前状态取反
        if (map.get(position)) {
            map.put(position, false);
        } else {
            map.put(position, true);
        }
        notifyItemChanged(position);
    }

    //返回集合给MainActivity
    public Map<Integer, Boolean> getMap() {
        return map;
    }

    public boolean getShow() {
        return isshowBox;
    }


    public boolean isChecked(int position){
        return map.get(position);
    }

}
