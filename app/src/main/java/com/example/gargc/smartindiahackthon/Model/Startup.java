package com.example.gargc.smartindiahackthon.Model;

import java.io.Serializable;

/**
 * Created by gargc on 22-03-2018.
 */

public class Startup implements Serializable{
    private String About;
    private String Name;
    private  String Description;
    private String Category;
    private String logo;
    private  String cover;
    private String uid;


    public Startup(String about, String name, String description, String category, String logo, String cover, String uid) {
        About = about;
        Name = name;
        Description = description;
        Category = category;
        this.logo = logo;
        this.cover = cover;
        this.uid = uid;
    }
    public Startup()
    {

    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
