package com.example.androidlauncher_cis357;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to set up the Pager Object
 */
public class PagerObject implements Serializable {
    private ArrayList<AppObject> homeAppList;

    public PagerObject(ArrayList<AppObject> homeAppList){
        this.homeAppList = homeAppList;
    }

    public ArrayList<AppObject> getAppList(){
        return homeAppList;
    }
}
