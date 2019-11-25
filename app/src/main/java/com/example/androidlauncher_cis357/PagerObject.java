package com.example.androidlauncher_cis357;

import java.util.ArrayList;

public class PagerObject {
    private ArrayList<AppObject> homeAppList;

    public PagerObject(ArrayList<AppObject> homeAppList){
        this.homeAppList = homeAppList;
    }

    public ArrayList<AppObject> getAppList(){
        return homeAppList;
    }
}
