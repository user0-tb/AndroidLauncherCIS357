package com.example.androidlauncher_cis357;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

class AppAdapter extends BaseAdapter {
    Context context;
    List<AppObject> appList;
    int cellHeight;

    /**
     * AppAdapter construct that sets up the app with touch and movement abilities
     * @param context
     * @param appList
     * @param cellHeight
     */
    public AppAdapter(Context context, List<AppObject> appList, int cellHeight) {
        this.context = context;
        this.appList = appList;
        this.cellHeight = cellHeight;
    }

    /**
     * Get a count of the appList
     * @return
     */
    @Override
    public int getCount() {
        return appList.size();
    }

    /**
     * Get an item from the appList at a certain position
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return appList.get(position);
    }

    /**
     * Get the Item ID of an item
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Set up the view of the appList within the screen, sets up touch control as well as the
     * images for each of the apps.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
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

        //Set a long click listener for all the apps within the view
        vLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((MainActivity) context).itemDrag(appList.get(position));
                return true;
            }
        });
        return v;
    }
}

