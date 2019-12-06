package com.example.androidlauncher_cis357;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

/**
 * Class which controls the Settings screen within the launcher
 */
public class SettingsActivity extends AppCompatActivity {
    int currentPage;
    int currentGridSize;
    int userInput;
    ArrayList<PagerObject> pagerAppList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);

        //Set up all buttons on the screen
        Button colorSettings = findViewById(R.id.colorsettings);
        Button settingButton = findViewById(R.id.systemsettings);
        Button launcherInfo = findViewById(R.id.launcherinfo);
        Button gridChangeButton = findViewById(R.id.savegridsize);

        //Set up the gridsize text box
        final TextView gridSizeNumber = findViewById(R.id.pagesize);


        gridSizeNumber.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b){
                gridSizeNumber.getText();
            }
        });

        //Get data that was passed to settings from MainActivity
        if(getIntent() != null){
            currentGridSize = getIntent().getIntExtra("currGridSize",0);
            currentPage = getIntent().getIntExtra("currPage",0);
        }
        gridSizeNumber.setText(String.valueOf(currentGridSize));

        //Button for color settings
        colorSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ColorActivity.class));
            }
        });

        //Button for system settings
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //Button for launcher info
        launcherInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),InfoActivity.class));
            }
        });

        //Button to change grid size
        gridChangeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                userInput = Integer.parseInt(gridSizeNumber.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("gridValue",userInput);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
