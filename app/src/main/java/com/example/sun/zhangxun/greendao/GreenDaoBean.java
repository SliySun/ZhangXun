package com.example.sun.zhangxun.greendao;

import com.example.sun.zhangxun.bean.News;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sun on 2017/5/13.
 */

@Entity
//        (indexes = {@Index(value = "info",unique = true)})
public class GreenDaoBean {

    @Id
    private Long id;



    @Convert(converter = NewInfoTypeConverter.class , columnType = String.class)
    private News.NewInfo info;



    @Generated(hash = 1707758269)
    public GreenDaoBean(Long id, News.NewInfo info) {
        this.id = id;
        this.info = info;
    }



    @Generated(hash = 826843181)
    public GreenDaoBean() {
    }



    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public News.NewInfo getInfo() {
        return this.info;
    }



    public void setInfo(News.NewInfo info) {
        this.info = info;
    }








   
}
