package com.fylm.map;


import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.fylm.MainNavActivity;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.views.InfoWindow;
import com.mapbox.mapboxsdk.views.MapView;

public class CustomInfoWindow extends InfoWindow {
    public String TAG = CustomInfoWindow.class.getSimpleName();
    Activity holder;
    //this is the constructor used
    public CustomInfoWindow(int layoutResId, MapView mv, Activity act){
        super(layoutResId, mv);
        holder = act;
        this.getView().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });
    }

    public CustomInfoWindow(View view, MapView mv){
        super(view, mv);
        this.getView().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onOpen(Marker overlayItem) {
        Log.i(TAG, "Yay, we made it in custom info window on open");
        Activity act = holder;
       ((MainNavActivity) act).openDrawerThroughFragment();
    }

}