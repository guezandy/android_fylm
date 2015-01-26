package com.fylm;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fylm.map.FeedFragment;
import com.fylm.map.MapFeedDrawerInfoFragment;
import com.fylm.map.MapFeedDrawerSearchFragment;
import com.fylm.map.MapFragment;
import com.fylm.ui.SlidingUpPanelLayout;
import com.fylm.ui.SlidingUpPanelLayout.PanelSlideListener;


public class MainNavActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    /**Debug TAG **/
    public static String TAG = MainNavActivity.class.getSimpleName();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /** Sliding drawer **/
    private SlidingUpPanelLayout mLayout;

    /** Are we in feed or map mode **/
    public Boolean mapMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.setPanelSlideListener(new PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelExpanded(View panel) {
                Log.i(TAG, "onPanelExpanded");

            }

            @Override
            public void onPanelCollapsed(View panel) {
                Log.i(TAG, "onPanelCollapsed");

            }

            @Override
            public void onPanelAnchored(View panel) {
                Log.i(TAG, "onPanelAnchored");
            }

            @Override
            public void onPanelHidden(View panel) {
                Log.i(TAG, "onPanelHidden");
            }
        });

/*        TextView t = (TextView) findViewById(R.id.main);
        t.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.collapsePanel();
            }
        });*/


/*        TextView t = (TextView) findViewById(R.id.name);
        t.setText(Html.fromHtml(getString(R.string.hello)));
        Button f = (Button) findViewById(R.id.follow);
        f.setText(Html.fromHtml(getString(R.string.follow)));
        f.setMovementMethod(LinkMovementMethod.getInstance());
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Button pressed", Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(Intent.ACTION_VIEW);
                //i.setData(Uri.parse("http://www.twitter.com/umanoapp"));
                //startActivity(i);
            }
        });*/
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        switch(position) {
            case 0:
                //ON START
                replaceFragment(new MapFragment(), true, FragmentTransaction.TRANSIT_FRAGMENT_FADE, getString(R.string.title_home));
                replaceDrawerInfoFragment(new MapFeedDrawerInfoFragment(), true, FragmentTransaction.TRANSIT_FRAGMENT_FADE, getString(R.string.title_home));
                replaceDrawerFragment(new MapFeedDrawerSearchFragment(), true, FragmentTransaction.TRANSIT_FRAGMENT_FADE, getString(R.string.title_home));
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_home);
                break;
            case 2:
                mTitle = getString(R.string.title_audiences);
                break;
            case 3:
                mTitle = getString(R.string.title_explore);
                break;
            case 4:
                mTitle = getString(R.string.title_notifications);
                break;
            case 5:
                mTitle = getString(R.string.title_profile);
                break;
            case 6:
                mTitle = getString(R.string.title_settings);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_nav, menu);
            MenuItem item = menu.findItem(R.id.action_toggle);
            if (mLayout != null) {
                if (mLayout.isPanelHidden()) {
                    item.setTitle(R.string.action_show);
                } else {
                    item.setTitle(R.string.action_hide);
                }
            }
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_toggle: {
                if (mLayout != null) {
                    if (!mLayout.isPanelHidden()) {
                        mLayout.hidePanel();
                        item.setTitle(R.string.action_show);
                    } else {
                        mLayout.showPanel();
                        item.setTitle(R.string.action_hide);
                    }
                }
                return true;
            }
            case R.id.action_anchor: {
                if (mLayout != null) {
                    if (mLayout.getAnchorPoint() == 1.0f) {
                        mLayout.setAnchorPoint(0.7f);
                        mLayout.expandPanel(0.7f);
                        item.setTitle(R.string.action_anchor_disable);
                    } else {
                        mLayout.setAnchorPoint(1.0f);
                        mLayout.collapsePanel();
                        item.setTitle(R.string.action_anchor_enable);
                    }
                }
                return true;
            }
            case R.id.action_map: {
                if(mapMode) {
                    item.setIcon(R.drawable.ic_menu_device_access_video);
                    replaceFragment(new FeedFragment(), true, FragmentTransaction.TRANSIT_FRAGMENT_FADE, getString(R.string.title_home));
                    replaceDrawerInfoFragment(new MapFeedDrawerInfoFragment(), true, FragmentTransaction.TRANSIT_FRAGMENT_FADE, getString(R.string.title_home));
                    replaceDrawerFragment(new MapFeedDrawerSearchFragment(), true, FragmentTransaction.TRANSIT_FRAGMENT_FADE, getString(R.string.title_home));
                    mapMode = false;
                    mLayout.hidePanel();
                } else {
                    item.setIcon(R.drawable.tag);
                    replaceFragment(new MapFragment(), true, FragmentTransaction.TRANSIT_FRAGMENT_FADE, getString(R.string.title_home));
                    replaceDrawerInfoFragment(new MapFeedDrawerInfoFragment(), true, FragmentTransaction.TRANSIT_FRAGMENT_FADE, getString(R.string.title_home));
                    replaceDrawerFragment(new MapFeedDrawerSearchFragment(), true, FragmentTransaction.TRANSIT_FRAGMENT_FADE, getString(R.string.title_home));
                    mapMode = true;
                    mLayout.showPanel();
                }
            }
            case R.id.action_video: {

            }
            case R.id.action_settings: {
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_nav, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainNavActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null && mLayout.isPanelExpanded() || mLayout.isPanelAnchored()) {
            mLayout.collapsePanel();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Handles adding all fragments to the view.
     * @param newFragment The fragment to add.
     * @param addToBackstack Whether this Fragment should appear in the backstack or not.
     * @param transition The transition animation to apply
     * @param backstackName The name
     */
    public void replaceFragment(android.support.v4.app.Fragment newFragment, boolean addToBackstack, int transition, String backstackName) {
        // use fragmentTransaction to replace the fragment
        Log.i(TAG, "Initializing Fragment Transaction");
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Log.i(TAG, "Replacing the fragment and calling backstack");
        fragmentTransaction.replace(R.id.container, newFragment, backstackName);
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(backstackName);
        }
        Log.i(TAG, "setting the transition");
        fragmentTransaction.setTransition(transition);
        Log.i(TAG,"Commiting Transaction");
        fragmentTransaction.commit();
    }

    public void replaceDrawerInfoFragment(android.support.v4.app.Fragment newFragment, boolean addToBackstack, int transition, String backstackName) {
        // use fragmentTransaction to replace the fragment
        Log.i(TAG, "Initializing Fragment2 Transaction");
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container2, newFragment, backstackName);
        if (addToBackstack) {
            //fragmentTransaction.addToBackStack(backstackName);
        }
        fragmentTransaction.setTransition(transition);
        Log.i(TAG,"Commiting 2 Transaction");
        fragmentTransaction.commit();
    }

    public void replaceDrawerFragment(android.support.v4.app.Fragment newFragment, boolean addToBackstack, int transition, String backstackName) {
        // use fragmentTransaction to replace the fragment
        Log.i(TAG, "Initializing Fragment3 Transaction");
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container3, newFragment, backstackName);
        if (addToBackstack) {
            //fragmentTransaction.addToBackStack(backstackName);
        }
        fragmentTransaction.setTransition(transition);
        Log.i(TAG,"Commiting 3 Transaction");
        fragmentTransaction.commit();
    }

    public void openDrawerThroughFragment() {
        mLayout.setAnchorPoint(0.7f);
        mLayout.expandPanel(0.7f);
    }

}
