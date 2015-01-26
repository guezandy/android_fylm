package com.fylm.map;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fylm.R;
import com.fylm.model.ParseVideoModel;
import com.fylm.service.IParseCallback;
import com.fylm.service.ParseService;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Icon;
import com.mapbox.mapboxsdk.overlay.UserLocationOverlay;
import com.mapbox.mapboxsdk.views.InfoWindow;
import com.mapbox.mapboxsdk.views.MapView;
import com.mapbox.mapboxsdk.views.util.TilesLoadedListener;

import java.util.List;


public class MapFragment extends Fragment {
    public String TAG = MapFragment.class.getSimpleName();
    private MapView mv;
    private UserLocationOverlay myLocationOverlay;
    private String currentMap = null;
    private ParseService mParseService;


    private RelativeLayout mView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = (RelativeLayout) inflater.inflate(R.layout.fragment_map, container, false);
        mParseService = new ParseService(mView.getContext());

        mv = (MapView) mView.findViewById(R.id.mapview);
        mv.setMinZoomLevel(mv.getTileProvider().getMinimumZoomLevel());
        mv.setMaxZoomLevel(mv.getTileProvider().getMaximumZoomLevel());
        mv.setCenter(mv.getTileProvider().getCenterCoordinate());
        mv.setZoom(0);
        currentMap = getString(R.string.streetMapId);

        // Show user location (purposely not in follow mode)
        mv.setUserLocationEnabled(true);

        Context con = mView.getContext();


        new ParseService(mView.getContext()).getVideoPerUser(mView.getContext(), new IParseCallback<List<ParseVideoModel>>() {
            @Override
            public void onSuccess(List<ParseVideoModel> items) {

                //MapBoxMarker m;
                for (ParseVideoModel item : items) {

                    final CustomMarker m = new CustomMarker(mv, item.getDescription(), item.getAwsKey(), new LatLng(item.getLocation().getLatitude(), item.getLocation().getLongitude()), getActivity());
                    m.setIcon(new Icon(mView.getContext(), Icon.Size.LARGE, "land-use", "00FFFF"));
                    Log.i(TAG, "Icon set now setting info window");
                    final InfoWindow infoWindow = m.getToolTip(mv);
                    mv.addMarker(m);
                }
            }

            @Override
            public void onFail(String message) {

            }
        });
        mv.goToUserLocation(true);

        mv.setOnTilesLoadedListener(new TilesLoadedListener() {
            @Override
            public boolean onTilesLoaded() {
                return false;
            }

            @Override
            public boolean onTilesLoadStarted() {
                // TODO Auto-generated method stub
                return false;
            }
        });
        mv.setVisibility(View.VISIBLE);

        return mView;
    }

}
