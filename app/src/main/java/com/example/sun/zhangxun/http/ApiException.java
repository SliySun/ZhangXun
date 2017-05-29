package com.example.sun.zhangxun.http;

import com.example.sun.zhangxun.bean.News;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import retrofit2.HttpException;

/**
 * Created by sun on 2017/4/22.
 */

public class ApiException extends Exception{

    public String resultcode;

    public String reason;

    public int error_code;

    public News news ;



    public ApiException(News news){
        this.news = news;
    }



}
