package com.example.sun.zhangxun.http;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.example.sun.zhangxun.ui.activity.PhotoBrowserActivity;

import java.util.ArrayList;

/**
 * Created by sun on 2017/4/28.
 */

public class MJavascriptInterface {

    private Context context;
    private String[] imageUrls;
    public static ArrayList<String> mList;

    public MJavascriptInterface(Context context){
        this.context = context;
    }

    public MJavascriptInterface(Context context,String[] imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
//        list = new ArrayList<>();
    }

    public void setImageUrls(String[] imageUrls){
        this.imageUrls = imageUrls;
    }

    public static void setList(ArrayList<String> list) {
       mList = list;
    }

    @JavascriptInterface
    public void openImage(String img) {
        Intent intent = new Intent();
//        intent.putExtra("imageUrls", imageUrls);
        intent.putStringArrayListExtra("urls",mList);
        intent.putExtra("curImageUrl", img);
        intent.setClass(context, PhotoBrowserActivity.class);
        context.startActivity(intent);
    }

    @JavascriptInterface
    public void showSource(String html) {
        System.out.println("====>html="+html);
    }
}
