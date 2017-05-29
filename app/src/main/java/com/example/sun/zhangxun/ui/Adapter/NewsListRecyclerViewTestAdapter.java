package com.example.sun.zhangxun.ui.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.bean.News;

import java.util.List;

/**
 * Created by sun on 2017/5/11.
 *
 */

public class NewsListRecyclerViewTestAdapter extends BaseMultiItemQuickAdapter<News.NewInfo,BaseViewHolder> {

    private Context context;



    public NewsListRecyclerViewTestAdapter(Context context,List<News.NewInfo> data) {
        super(data);
        this.context =context;
        addItemType(News.NewInfo.IMG_1, R.layout.item_recyclerview_new_list_1pic);
        addItemType(News.NewInfo.IMG_3, R.layout.item_recyclerview_new_list_3pic);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, News.NewInfo info) {
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

                break;
            case News.NewInfo.IMG_3:

                ImageView iv_1 =  baseViewHolder.getView(R.id.imageView_item_new_img1_3pic);
                ImageView iv_2 =  baseViewHolder.getView(R.id.imageView_item_new_img2_3pic);
                ImageView iv_3 =  baseViewHolder.getView(R.id.imageView_item_new_img3_3pic);

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
                break;
            default:
                break;

        }
    }

    private static class OnePicViewHolder extends BaseViewHolder{


        private TextView tv_title;
        private TextView tv_author;
        private TextView tv_time;
        private ImageView iv_right;

        public OnePicViewHolder(View view) {
            super(view);
            tv_title = (TextView) itemView.findViewById(R.id.textView_item_new_title_1pic);
            tv_author = (TextView) itemView.findViewById(R.id.textView_item_new_author_1pic);
            tv_time = (TextView) itemView.findViewById(R.id.textView_item_new_time_1pic);
            iv_right = (ImageView) itemView.findViewById(R.id.imageView_item_new_right_1pic);

        }
    }

    private static class ThreePicViewHolder extends BaseViewHolder{

        private TextView tv_title;
        private ImageView iv_1;
        private ImageView iv_2;
        private ImageView iv_3;
        private TextView tv_author;
        private TextView tv_time;

        public ThreePicViewHolder(View view) {
            super(view);
            tv_title = (TextView) itemView.findViewById(R.id.textView_item_new_title_3pic);
            iv_1 = (ImageView) itemView.findViewById(R.id.imageView_item_new_img1_3pic);
            iv_2 = (ImageView) itemView.findViewById(R.id.imageView_item_new_img2_3pic);
            iv_3 = (ImageView) itemView.findViewById(R.id.imageView_item_new_img3_3pic);
            tv_author = (TextView) itemView.findViewById(R.id.textView_item_new_author_3pic);
            tv_time = (TextView) itemView.findViewById(R.id.textView_item_new_time_3pic);

        }
    }

}
