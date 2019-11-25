package com.example.androidlauncher_cis357;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Struct;

public class ColorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_page);

        final Spinner statColorSpinner = findViewById(R.id.colorSpinner1);
        final Spinner navColorSpinner = findViewById(R.id.colorSpinner2);

        final ImageView navColorPreview = findViewById(R.id.navBarColor);
        final ImageView statColorPreview = findViewById(R.id.statusBarColor);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.color_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.color_array, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        statColorSpinner.setAdapter(adapter1);
        navColorSpinner.setAdapter(adapter2);

        statColorSpinner.setSelection(0);
        navColorSpinner.setSelection(0);

        final Window w = this.getWindow();


        statColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String colorChoice = (String) parent.getItemAtPosition(position);
                int color = getResources().getColor(getResources().getIdentifier(colorChoice,"color", getPackageName()));
                statColorSpinner.setSelection(position);
                statColorPreview.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                w.setStatusBarColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        navColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String colorChoice = (String) parent.getItemAtPosition(position);
                int color = getResources().getColor(getResources().getIdentifier(colorChoice,"color", getPackageName()));
                navColorSpinner.setSelection(position);
                navColorPreview.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                w.setNavigationBarColor(color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

}

