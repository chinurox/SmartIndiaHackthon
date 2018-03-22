package com.example.gargc.smartindiahackthon.Model;

/**
 * Created by gargc on 20-03-2018.
 */

public class Blog {
    private String title;
    private String desc;
    private String image;
    private String uid;
    private String content;
    private String userimage;
    private String postedby;

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }


    Blog()
    {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public Blog(String title, String desc, String image, String uid, String content, String userimage,String postedby) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.uid = uid;
        this.content = content;
        this.userimage = userimage;
        this.postedby=postedby;
    }


}
