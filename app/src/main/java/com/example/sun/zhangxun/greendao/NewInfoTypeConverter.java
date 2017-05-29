package com.example.sun.zhangxun.greendao;

import com.example.sun.zhangxun.bean.News;
import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Created by sun on 2017/5/13.
 */

public class NewInfoTypeConverter implements PropertyConverter<News.NewInfo,String> {
    @Override
    public News.NewInfo convertToEntityProperty(String databaseValue) {
        Gson g = new Gson();
        News.NewInfo info = g.fromJson(databaseValue, News.NewInfo.class);
        return info;
    }

    @Override
    public String convertToDatabaseValue(News.NewInfo entityProperty) {
        Gson g = new Gson();
        String json=g.toJson(entityProperty);
        return json;
    }
}
