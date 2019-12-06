package com.example.androidlauncher_cis357;

import android.graphics.drawable.Drawable;

public class AppObject{
    private String appName, packageName;
    private Drawable appImage;
    private Boolean appInDrawer;

    /**
     * Sets up a new AppObject with a package name, app name, app image, and also determines
     * whether an app is already added or not.
     *
     * @param packageName
     * @param appName
     * @param appImage
     * @param appInDrawer
     */
    public AppObject(String packageName, String appName, Drawable appImage, Boolean appInDrawer){
        this.appName = appName;
        this.packageName = packageName;
        this.appImage = appImage;
        this.appInDrawer = appInDrawer;
    }

    /**
     * Get an app package name
     * @return
     */
    public String getPackageName(){
        return packageName;
    }

    /**
     * Get an app name
     * @return
     */
    public String getAppName(){
        return appName;
    }

    /**
     * Get an app image
     * @return
     */
    public Drawable getAppImage(){
        return appImage;
    }

    /**
     * Get if the app is in the drawer or not
     * @return
     */
    public Boolean getIsAppInDrawer(){
        return appInDrawer;
    }

    /**
     * Set the package name of an app
     * @param packageName
     */
    public void setPackageName(String packageName){
        this.packageName = packageName;
    }

    /**
     * Set the name of an app
     * @param name
     */
    public void setName(String name){
        this.appName = name;
    }

    /**
     * Set the image of an app
     * @param image
     */
    public void setImage(Drawable image){
        this.appImage = image;
    }

    /**
     * Directly set if an app is in the drawer or not
     * @param bool
     */
    public void setIsAppInDrawer(Boolean bool){
        this.appInDrawer = appInDrawer;
    }
}
