package com.example.androidlauncher_cis357;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to set up the Pager Object
 */
public class PagerObject implements Serializable {
    private ArrayList<AppObject> homeAppList;

    /**
     * Sets up the new PagerObject within the home screen
     * @param homeAppList
     */
    public PagerObject(ArrayList<AppObject> homeAppList){
        this.homeAppList = homeAppList;
    }

    /**
     * Gets the homeAppList from the PagerObject
     * @return
     */
    public ArrayList<AppObject> getAppList(){
        return homeAppList;
    }
}
