package com.fylm.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fylm.R;
import com.fylm.service.ParseService;


public class MapFeedDrawerSearchFragment extends Fragment {
    public String TAG = MapFeedDrawerInfoFragment.class.getSimpleName();

    private ParseService mParseService;


    private RelativeLayout mView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = (RelativeLayout) inflater.inflate(R.layout.fragment_mapfeed_drawer_info, container, false);
        mParseService = new ParseService(mView.getContext());


        return mView;
    }

}
