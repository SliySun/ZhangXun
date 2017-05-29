package com.example.sun.zhangxun.ui.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.bean.News;

import java.util.List;

/**
 * Created by sun on 2017/4/23.
 */

public class NewsListRecyclerViewAdapter extends RecyclerView.Adapter<NewsListRecyclerViewAdapter.NewsListViewHolder> implements View.OnClickListener,View.OnLongClickListener{

    private static final int TYPE_THREE= 0X3;
    private static final int TYPE_ONE= 0X1;


    private Context context;
    private List<News.NewInfo> list;
    private NewsListRecyclerViewAdapter.onItemClickListener mOnItemClickLitener;
    private NewsListRecyclerViewAdapter.onItemLongClickListener mOnItemLongClickListener;


    public NewsListRecyclerViewAdapter(Context context, List<News.NewInfo> list){
        this.context= context;
        this.list=list;
    }

    public void setData( List<News.NewInfo> newsList){
        this.list = newsList;
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickLitener != null){
            mOnItemClickLitener.onItemClick(view, (int) view.getTag());
            System.out.println((int)view.getTag()+"");
        }

    }

    @Override
    public boolean onLongClick(View view) {
        if (mOnItemLongClickListener != null){
            mOnItemLongClickListener.onItemLongClick(view, (int) view.getTag());
            System.out.println((int)view.getTag()+"---");
        }
        return false;
    }

    /*
        点击事件监听
     */
    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface onItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    @Override
    public int getItemViewType(int position) {
        News.NewInfo info=list.get(position);
        if (info.thumbnail_pic_s03 != null){
            return TYPE_THREE;
        }else {
            return TYPE_ONE;
        }
    }

    public void setOnItemClickLitener(NewsListRecyclerViewAdapter.onItemClickListener mOnItemClickListener) {
        this.mOnItemClickLitener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(NewsListRecyclerViewAdapter.onItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public NewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =  LayoutInflater.from(context).inflate(R.layout.item_recyclerview_new_list,parent,false);
        NewsListViewHolder holder=new NewsListViewHolder(view);
        holder.setItemType(viewType);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NewsListViewHolder holder, final int position) {
        News.NewInfo info = list.get(position);
        holder.itemView.setTag(position);
        holder.tv_title.setText(info.title);
        holder.tv_author.setText(info.author_name);
        holder.tv_time.setText(info.date);
        switch (getItemViewType(position)){
            case TYPE_ONE:
                Glide.with(context)
                        .load(info.thumbnail_pic_s)
                        .placeholder(R.drawable.image_loading)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.iv_right);
                break;
            case TYPE_THREE:
                Glide.with(context).load(info.thumbnail_pic_s)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.image_loading).into(holder.iv_1);
                Glide.with(context).load(info.thumbnail_pic_s02).placeholder(R.drawable.image_loading)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.iv_2);
                Glide.with(context).load(info.thumbnail_pic_s03).placeholder(R.drawable.image_loading)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.iv_3);

                break;
        }

        // 如果设置了回调，则设置点击事件
        /*
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    System.out.println( "pos:"+pos );
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

        }
        if (mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class NewsListViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_title;
        private LinearLayout ll_imgs;
        private ImageView iv_1;
        private ImageView iv_2;
        private ImageView iv_3;
        private TextView tv_author;
        private TextView tv_time;
        private ImageView iv_right;



        public NewsListViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.textView_item_new_title);
            ll_imgs = (LinearLayout) itemView.findViewById(R.id.linearLayout_item_new_images);

            iv_1 = (ImageView) itemView.findViewById(R.id.imageView_item_new_img1);
            iv_2 = (ImageView) itemView.findViewById(R.id.imageView_item_new_img2);
            iv_3 = (ImageView) itemView.findViewById(R.id.imageView_item_new_img3);
            tv_author = (TextView) itemView.findViewById(R.id.textView_item_new_author);
            tv_time = (TextView) itemView.findViewById(R.id.textView_item_new_time);
            iv_right = (ImageView) itemView.findViewById(R.id.imageView_item_new_right);
        }

        public void setItemType(int itemType){
            switch (itemType){
                case TYPE_ONE:
                    ll_imgs.setVisibility(View.GONE);
                    iv_right.setVisibility(View.VISIBLE);
                    break;
                case TYPE_THREE:
                    ll_imgs.setVisibility(View.VISIBLE);
                    iv_right.setVisibility(View.GONE);
                    break;
            }
        }
    }
}
