package com.example.androidlauncher_cis357;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<AppObject> installedAppList = new ArrayList<>();
    ViewPager homeViewPager;
    int cellHeight;
    int NUM_ROWS = 5;
    int DRAWER_PEEK = 100;
    GridView drawerGridView;
    BottomSheetBehavior bottomSheetBehavior;
    LinearLayout topDrawerLayout;
    public AppObject appDrag = null;
    ViewPagerAdapter hViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topDrawerLayout = findViewById(R.id.topDrawerLayout);
        topDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                DRAWER_PEEK = topDrawerLayout.getHeight();
                initHome();
                initDrawer();
            }
        });

        LinearLayout layout = new LinearLayout(this);

        ImageButton settingsButton = findViewById(R.id.settings);

        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(),SettingsActivity.class);
                startActivity(intent);
            }
        });
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        detector.onTouchEvent(event);
//        return super.onTouchEvent(event);
//    }

    private void initHome() {
        final ArrayList<PagerObject> pagerAppList = new ArrayList<>();

        if(pagerAppList.size() == 0) {
            createNewPage(pagerAppList);
        }

        cellHeight = (getDisplayContentHeight() - DRAWER_PEEK) / NUM_ROWS;

        homeViewPager = findViewById(R.id.viewPager);
        hViewPagerAdapter = new ViewPagerAdapter(this, pagerAppList, cellHeight);

        homeViewPager.setAdapter(hViewPagerAdapter);

        ImageButton pageAddButton = findViewById(R.id.addpage);
        pageAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                createNewPage(pagerAppList);
                hViewPagerAdapter.notifyGridChange();
                homeViewPager.setAdapter(hViewPagerAdapter);
            }
        });

        ImageButton pageDeleteButton = findViewById(R.id.removepage);
        pageDeleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(pagerAppList.size() != 1){
                    removePage(pagerAppList);
                    hViewPagerAdapter.notifyGridChange();
                    homeViewPager.setAdapter(hViewPagerAdapter);
                }
            }
        });


    }

    private void createNewPage(ArrayList<PagerObject> pagerAppList) {

        ArrayList<AppObject> appList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            appList.add(new AppObject("", "", ResourcesCompat.getDrawable(getResources(), R.drawable.check, null), false));
        }

        //Let user know when a new page is added to the home screen
        Toast.makeText(this,"New Page Added", Toast.LENGTH_SHORT).show();
        pagerAppList.add(new PagerObject(appList));
    }

    private void removePage(ArrayList<PagerObject> pagerAppList) {
        //Let user know when a page is removed from the home screen
        Toast.makeText(this,"Page Removed", Toast.LENGTH_SHORT).show();
        pagerAppList.remove(pagerAppList.size()-1);
    }

    private void initDrawer() {
        final View bottomSheet = findViewById(R.id.bottomSheet);
        drawerGridView = findViewById(R.id.drawerGrid);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight(DRAWER_PEEK);

        //Need a way to hide the drawer completely when not in use. Swipe up opens the drawer.

        installedAppList = getInstalledAppList();
        drawerGridView.setAdapter(new AppAdapter(this, installedAppList, cellHeight));

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (appDrag != null) {
                    return;
                }
                if (i == BottomSheetBehavior.STATE_COLLAPSED && drawerGridView.getChildAt(0).getY() != 0) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                if (i == BottomSheetBehavior.STATE_DRAGGING && drawerGridView.getChildAt(0).getY() != 0) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
        // homeAppList = getHomeAppList();

    }

    public void itemPress(AppObject app) {
        if (appDrag != null && !app.getAppName().equals("")) {
            //Let user know if a grid spot already contains an app on the homescreen
            Toast.makeText(this,"Cell Already Taken", Toast.LENGTH_SHORT).show();
            appDrag = null;
            return;
        }
        if(appDrag != null && !app.getIsAppInDrawer()) {
            app.setPackageName(appDrag.getPackageName());
            app.setName(appDrag.getAppName());
            app.setImage(appDrag.getAppImage());
            app.setIsAppInDrawer(false);
            if(!appDrag.getIsAppInDrawer()){
                removeApp(appDrag);
            }
            appDrag = null;
            hViewPagerAdapter.notifyGridChange();
            return;
        } else {
            Intent launchAppIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(app.getPackageName());
            if (launchAppIntent != null)
                getApplicationContext().startActivity(launchAppIntent);
        }
    }

    private void removeApp(AppObject appDrag) {
        appDrag.setPackageName("");
        appDrag.setName("");
        appDrag.setImage(ResourcesCompat.getDrawable(getResources(), R.drawable.check, null));
        appDrag.setIsAppInDrawer(false);
    }


    public Boolean itemDrag(AppObject app) { //drag app on long tap
        collapseDrawer();
        appDrag = app;
        return true;
    }

    private void collapseDrawer() {
        drawerGridView.setY(DRAWER_PEEK);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }


    private List<AppObject> getInstalledAppList() {
        List<AppObject> list = new ArrayList<>();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> untreatedAppList = getApplicationContext().getPackageManager().queryIntentActivities(intent, 0); //Gets list of all apps

        for (ResolveInfo untreatedApp : untreatedAppList) {
            String appName = untreatedApp.activityInfo.loadLabel(getPackageManager()).toString(); //Get the name of each app
            String appPackageName = untreatedApp.activityInfo.packageName; //Get package name of each app
            Drawable appImage = untreatedApp.activityInfo.loadIcon(getPackageManager()); //Get Image of each app

            AppObject app = new AppObject(appPackageName, appName, appImage, true); //Pass all to the new app object
            if (!list.contains(app)) {
                list.add(app); //Add app to list only if it does not already exist
            }
        }
        return list;
    }

    private int getDisplayContentHeight() {
        final WindowManager windowManager = getWindowManager();
        final Point size = new Point();
        int screenHeight = 0;
        int actionBarHeight = 0;
        int statusBarHeight = 0;
        if (getActionBar() != null) {
            actionBarHeight = getActionBar().getHeight();
        }
        int resourceId = getResources().getIdentifier("status_bar_height", "dimension", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        int contentTop = (findViewById(android.R.id.content)).getTop();
        windowManager.getDefaultDisplay().getSize(size);
        screenHeight = size.y;
        return screenHeight - contentTop - actionBarHeight - statusBarHeight;
    }
}


