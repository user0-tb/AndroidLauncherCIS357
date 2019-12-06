package com.example.androidlauncher_cis357;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<PagerObject> pagerAppList;
    ArrayList<AppAdapter> appAdapterList = new ArrayList<>();
    public GridView homeGridView;
    int cellHeight;

    /**
     * Set up a new view pager adapter. These are the new pages which display on the
     * screen.
     * @param context
     * @param pagerAppList
     * @param cellHeight
     */
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

    /**
     * Remove a view from the container
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    /**
     * Get the size of the view pager
     * @return
     */
    @Override
    public int getCount() {
        return pagerAppList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    /**
     * Notifies the adapter list of a change in the grid. Used when the user creates a new page,
     * adds/removes apps, and also when the user changes the grid size of a launcher page.
     */
    public void notifyGridChange() {
        for (int i = 0; i < appAdapterList.size(); i++) {
            appAdapterList.get(i).notifyDataSetChanged();
        }
    }

}
