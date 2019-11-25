package com.example.androidlauncher_cis357;

import android.graphics.drawable.Drawable;

public class AppObject{
    private String appName, packageName;
    private Drawable appImage;
    private Boolean appInDrawer;

    public AppObject(String packageName, String appName, Drawable appImage, Boolean appInDrawer){
        this.appName = appName;
        this.packageName = packageName;
        this.appImage = appImage;
        this.appInDrawer = appInDrawer;
    }

    public String getPackageName(){
        return packageName;
    }

    public String getAppName(){
        return appName;
    }

    public Drawable getAppImage(){
        return appImage;
    }

    public Boolean getIsAppInDrawer(){
        return appInDrawer;
    }

    public void setPackageName(String packageName){
        this.packageName = packageName;
    }

    public void setName(String name){
        this.appName = name;
    }

    public void setImage(Drawable image){
        this.appImage = image;
    }

    public void setIsAppInDrawer(Boolean bool){
        this.appInDrawer = appInDrawer;
    }
}
