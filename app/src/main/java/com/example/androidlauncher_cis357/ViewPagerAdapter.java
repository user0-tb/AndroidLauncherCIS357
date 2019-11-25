package com.example.androidlauncher_cis357;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.view.MenuItem;
import android.view.ContextMenu.ContextMenuInfo;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<PagerObject> pagerAppList;
    ArrayList<AppAdapter> appAdapterList = new ArrayList<>();
    public GridView homeGridView;
    public GestureDetector detector;

    int cellHeight;

    public ViewPagerAdapter(Context context, ArrayList<PagerObject> pagerAppList, int cellHeight) {
        this.context = context;
        this.pagerAppList = pagerAppList;
        this.cellHeight = cellHeight;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.home_layout, container, false);

        homeGridView = layout.findViewById(R.id.grid);
        AppAdapter homeGridAdapter = new AppAdapter(context, pagerAppList.get(position).getAppList(), cellHeight);
        homeGridView.setAdapter(homeGridAdapter);

        appAdapterList.add(homeGridAdapter);

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return pagerAppList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    public void notifyGridChange() {
        for (int i = 0; i < appAdapterList.size(); i++) {
            appAdapterList.get(i).notifyDataSetChanged();
        }
    }

}
