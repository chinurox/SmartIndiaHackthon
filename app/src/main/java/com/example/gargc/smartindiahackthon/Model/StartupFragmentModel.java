package com.example.gargc.smartindiahackthon.Model;

import java.util.ArrayList;

/**
 * Created by gargc on 30-03-2018.
 */

public class StartupFragmentModel {

    private String About;
    private String Name;
    private  String Description;
    private String Category;
    private String logo;
    private  String cover;
    private String uid;
    private ArrayList<String> coFounderEmail;
    private String size;

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

    public ArrayList<String> getCoFounderEmail() {
        return coFounderEmail;
    }

    public void setCoFounderEmail(ArrayList<String> coFounderEmail) {
        this.coFounderEmail = coFounderEmail;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
