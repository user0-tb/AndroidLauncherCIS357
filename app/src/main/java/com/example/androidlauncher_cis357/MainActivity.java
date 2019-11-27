package com.example.androidlauncher_cis357;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.viewpagerindicator.LinePageIndicator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
    public ImageView homeImage;
    public static final int RESULT_PRO_IMG = 1;
    private HashSet<String> set;

    private int GRID_ROWS;
    private int GRID_COLUMNS;

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

        SharedPreferences preferences = getSharedPreferences("Image", Context.MODE_PRIVATE);
        String homeImageURL = preferences.getString("filepath","");
        Bitmap bitmap = BitmapFactory.decodeFile(homeImageURL);

        Drawable image = new BitmapDrawable(getResources(), bitmap);

        homeImage = findViewById(R.id.homeScreenImage);

        installedAppList = getInstalledAppList();

        homeImage.setBackground(image);

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
            public void onClick(View v) {
                int size = pagerAppList.size();
                if (size != 1) {
                    removePage(pagerAppList);
                    hViewPagerAdapter.notifyGridChange();
                    homeViewPager.setAdapter(hViewPagerAdapter);
                }
            }
        });

        ImageButton wallpaperChange = findViewById(R.id.cwallpaper);
        wallpaperChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeWallpaper();
            }
        });
    }

    /**
     * Creates a new page on the homescreen with correct columns and rows
     * @param pagerAppList
     */
    private void createNewPage(ArrayList<PagerObject> pagerAppList) {

        ArrayList<AppObject> appList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            appList.add(new AppObject("", "", ResourcesCompat.getDrawable(getResources(), R.drawable.check, null), false));
        }

        loadAppsOnOpen(appList);

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
            saveAppsOnClose(appDrag);
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
        findViewById(R.id.addpage).setVisibility(View.VISIBLE);
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

    private void saveAppsOnClose(AppObject app){
        String appName = app.getAppName();
        set = new HashSet<String>();
        SharedPreferences preferences = getSharedPreferences("savedApps", Context.MODE_PRIVATE);
        set.add(appName);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet("set",set);
        editor.commit();
    }

    private void loadAppsOnOpen(ArrayList<AppObject> appList){
        SharedPreferences preferences = getSharedPreferences("savedApps",Context.MODE_PRIVATE);
        Set<String> loadApps = preferences.getStringSet("set",new HashSet<String>());
        Iterator<String> itr = loadApps.iterator();

        if(loadApps.size() >= 1){
            while(itr.hasNext()){
                Log.i("Name",itr.next().toString());
                String appName = itr.toString();
                String packageName = "";
                Drawable appImage = null;
                for(int i = 0; i< installedAppList.size(); i++){
                    if(installedAppList.get(i).getAppName() == appName){
                        packageName = installedAppList.get(i).getPackageName();
                        appImage = installedAppList.get(i).getAppImage();
                    }
                }
              //  appList.add(1,new AppObject(packageName,appName,appImage,true));
            }
        }
    }

//    //Wallpaper change for home screen
    private void changeWallpaper(){
        Intent photoIntent = new Intent(Intent.ACTION_PICK);
        photoIntent.setType("image/*");
        startActivityForResult(photoIntent,RESULT_PRO_IMG);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Data", "Data Result Code :" + requestCode);
        switch (requestCode) {
            case RESULT_PRO_IMG:
                try {
                    Log.i("Data", "Data :" + data);
                    // When an Image is picked
                    if (requestCode == RESULT_PRO_IMG && resultCode == RESULT_OK) {

                        try {
                            /*****************************************************************************************/
                            Uri img = data.getData();
                            String[] filepc = {MediaStore.Images.Media.DATA};
                            Cursor c = this.getContentResolver().query(img,
                                    filepc, null, null, null);
                            c.moveToFirst();
                            int cIndex = c.getColumnIndex(filepc[0]);
                            String filePath = c.getString(cIndex);
                            c.close();
                            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();
                            Matrix matrix = new Matrix();
                            matrix.postRotate(0);

                            SharedPreferences preferences = getSharedPreferences("Image", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();

                            WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());

                            try {
                                if (bitmap != null) {
                                    editor.putString("filepath", filePath);
                                    editor.commit();
                                    myWallpaperManager.setBitmap(bitmap);
                                    Drawable image = new BitmapDrawable(getResources(), bitmap);
                                    homeImage.setBackground(image);
                                } else {
                                }

                            } catch (IOException e) {
                                Toast.makeText(MainActivity.this, "Something went wrong !!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }
    }

}


