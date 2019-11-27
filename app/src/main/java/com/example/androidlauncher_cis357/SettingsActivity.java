package com.example.androidlauncher_cis357;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    public ImageView homeScreenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);

        Button colorSettings = findViewById(R.id.colorsettings);
        Button settingButton = findViewById(R.id.systemsettings);
        Button launcherInfo = findViewById(R.id.launcherinfo);
        Button gridChangeButton = findViewById(R.id.gridsizebutton);

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
                startActivity(new Intent(getApplicationContext(),GridSizeActivity.class));
            }
        });
    }

}
