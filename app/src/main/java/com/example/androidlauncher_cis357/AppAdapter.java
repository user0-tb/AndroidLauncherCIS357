package com.example.androidlauncher_cis357;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class AppAdapter extends BaseAdapter {
    Context context;
    List<AppObject> appList;
    int cellHeight;
    ClipData data;


    public AppAdapter(Context context, List<AppObject> appList, int cellHeight) {
        this.context = context;
        this.appList = appList;
        this.cellHeight = cellHeight;
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public Object getItem(int position) {
        return appList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_app, parent, false);
        } else {
            v = convertView;
        }

        LinearLayout vLayout = v.findViewById(R.id.layout);

        ImageView aImage = v.findViewById(R.id.image);
        TextView aLabel = v.findViewById(R.id.label);

        LinearLayout.LayoutParams layoutp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, cellHeight);
        vLayout.setLayoutParams(layoutp);

        aImage.setImageDrawable(appList.get(position).getAppImage());
        aLabel.setText(appList.get(position).getAppName());

        vLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).itemPress(appList.get(position));
            }
        });

//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                ((MainActivity) context).itemPress(appList.get(position));
//                return false;
//            }
//        });((MainActivity) context).itemDrag(appList.get(position));

        vLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData clipData = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDragAndDrop(clipData,shadowBuilder,v,0);
                ((MainActivity) context).itemDrag(appList.get(position));
                return false;
            }
        });
        return v;
    }
}


//        vLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity) context).itemPress(appList.get(position));
//            }
//        });
//        vLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                ((MainActivity) context).itemDrag(appList.get(position));
//                return true;
//            }
//        });
//        return v;


//    private class myDragListener implements View.OnDragListener{
//        @Override
//        public boolean onDrag(View v, DragEvent event){
//            switch (event.getAction()){
//                case DragEvent.ACTION_DRAG_STARTED:
//                    if(event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
//                        v.invalidate();
//                        return true;
//                    }
//                    break;
//                case DragEvent.ACTION_DRAG_ENTERED:
//                    break;
//                case DragEvent.ACTION_DRAG_EXITED:
//                    break;
//                case DragEvent.ACTION_DROP:
//                    ClipData.Item item = event.getClipData().getItemAt(0);
//                    data = item.getText();
//
//            }
//        return false;
//        }
//    }

