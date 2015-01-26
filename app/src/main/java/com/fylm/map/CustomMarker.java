package com.fylm.map;


import android.app.Activity;

import com.fylm.R;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.views.MapView;

public class CustomMarker extends Marker {
    Activity act;

    public CustomMarker(MapView mv, String aTitle, String aDescription, LatLng aLatLng){
        super(mv, aTitle, aDescription, aLatLng);
    }

    public CustomMarker(MapView mv, String aTitle, String aDescription, LatLng aLatLng, Activity act){
        super(mv, aTitle, aDescription, aLatLng);
        this.act = act;
    }

    @Override
    protected CustomInfoWindow createTooltip(MapView mv) {
        CustomInfoWindow w = new CustomInfoWindow(R.layout.mapbox_custom_layout, mv, act);
        return w;
    }
}