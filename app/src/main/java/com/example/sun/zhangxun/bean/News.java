package com.example.sun.zhangxun.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sun on 2017/4/10.
 */

@SuppressWarnings("serial")
public class News implements Serializable{

    @SerializedName("resultcode")
    public String resultcode;

    @SerializedName("reason")
    public String reason;

    @SerializedName("result")
    public JsonResult result;

    @SerializedName("error_code")
    public int error_code;

    public static class JsonResult {
        @SerializedName("stat")
        public String stat;

        @SerializedName("data")
        public List<NewInfo> data;

        @Override
        public String toString() {
            return "JsonResult{" +
                    "stat='" + stat + '\'' +
                    ", data=" + data +
                    '}';
        }
    }


    /**
     * MultiItemEntity recyclerview
     *
     */
    @Entity
    public class NewInfo  implements Serializable ,MultiItemEntity{

        @Id(autoincrement = true)
        public long id;

        @Property(nameInDb = "uniquekey")
        public String uniquekey;

        @Property(nameInDb = "title")
        public String title;

        @Property(nameInDb = "date")
        public String date;

        @Property(nameInDb = "category")
        public String category;

        @Property(nameInDb = "author_name")
        public String author_name;

        @Property(nameInDb = "url")
        public String url;

        @Property(nameInDb = "thumbnail_pic_s")
        public String thumbnail_pic_s;

        @Property(nameInDb = "thumbnail_pic_s02")
        public String thumbnail_pic_s02;

        @Property(nameInDb = "thumbnail_pic_s03")
        public String thumbnail_pic_s03;

        @Transient
        public int itemType;

        @Override
        public String toString() {
            return "NewInfo{" +
                    "uniquekey='" + uniquekey + '\'' +
                    ", title='" + title + '\'' +
                    ", date='" + date + '\'' +
                    ", category='" + category + '\'' +
                    ", author_name='" + author_name + '\'' +
                    ", url='" + url + '\'' +
                    ", thumbnail_pic_s='" + thumbnail_pic_s + '\'' +
                    ", thumbnail_pic_s02='" + thumbnail_pic_s02 + '\'' +
                    ", thumbnail_pic_s03='" + thumbnail_pic_s03 + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof NewInfo) {
                NewInfo u = (NewInfo) obj;
                return this.uniquekey.equals(u.uniquekey)
                        && this.url.equals(u.url);
            }
            return super.equals(obj);
        }

        @Transient
        public static final int IMG_1 = 1;
        @Transient
        public static final int IMG_3 = 2;

        public NewInfo(int itemType) {
            this.itemType = itemType;
        }

        @Override
        public int getItemType() {
            return itemType;
        }


    }


    @Override
    public String toString() {
        return "News{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }


}
