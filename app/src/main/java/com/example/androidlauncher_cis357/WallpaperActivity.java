package com.example.androidlauncher_cis357;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.prefs.PreferencesFactory;
import java.util.zip.Inflater;

/**
 * Class used in order for the user to select a picture for background usage
 */
public class WallpaperActivity extends AppCompatActivity {
    public ImageView backgroundPreview;
    public ImageView backgroundImage;
    public Button wallpaperButton;
    public static final int RESULT_PRO_IMG = 1;
    public String picturePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallpaper_page);

        backgroundPreview = (ImageView)findViewById(R.id.backpreview);


        backgroundImage = findViewById(R.id.homeScreenImage);
        backgroundImage.setImageResource(R.drawable.ic_launcher_background);

        backgroundPreview.setBackground(backgroundImage.getBackground());


        SharedPreferences preferences = getSharedPreferences("Image",Context.MODE_PRIVATE);
        picturePath = preferences.getString("filepath","");



        if(picturePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            backgroundPreview.setImageBitmap(bitmap);
        }

        wallpaperButton = findViewById(R.id.wallpaperchange);

        wallpaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseWallpaper();
            }
        });
    }

    //Need code to select wallpaper from the

    public void chooseWallpaper(){
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
                            backgroundPreview.setImageBitmap(bitmap);

                            SharedPreferences preferences = getSharedPreferences("Image",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();

                            WallpaperManager myWallpaperManager = WallpaperManager
                                    .getInstance(getApplicationContext());
                            try {
                                if (bitmap != null) {
                                    editor.putString("filepath",filePath);
                                    editor.commit();
                                    myWallpaperManager.setBitmap(bitmap);
                                    Toast.makeText(WallpaperActivity.this, "Wallpaper set Successfully !!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(WallpaperActivity.this, "Your bitmap object is null !!", Toast.LENGTH_LONG).show();
                                }

                            } catch (IOException e) {
                                Toast.makeText(WallpaperActivity.this, "Something went wrong !!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(WallpaperActivity.this, "You haven't pick", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(WallpaperActivity.this, "Something went wrong", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }
    }

}
