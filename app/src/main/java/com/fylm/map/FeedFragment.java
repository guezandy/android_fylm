package com.fylm.map;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fylm.R;
import com.fylm.aws.Constants;
import com.fylm.aws.Util;
import com.fylm.service.ParseService;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;


public class FeedFragment extends Fragment {
    public String TAG = FeedFragment.class.getSimpleName();

    private ParseService mParseService;
    private LinearLayout mView;
    private ListView mList;
    private AmazonS3Client mClient;
    private ObjectAdapter mAdapter;
    // keeps track of the objects the user has selected
    private HashSet<S3ObjectSummary> mSelectedObjects =
            new HashSet<S3ObjectSummary>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = (LinearLayout) inflater.inflate(R.layout.fragment_feed, container, false);
        mParseService = new ParseService(mView.getContext());

        mList = (ListView) mView.findViewById(R.id.list);

        // initialize the client
        mClient = Util.getS3Client(mView.getContext());

        mAdapter = new ObjectAdapter(mView.getContext());
        mList.setOnItemClickListener(new ItemClickListener());
        mList.setAdapter(mAdapter);

        new RefreshTask(mView.getContext()).execute();

        return mView;
    }

    private class RefreshTask extends AsyncTask<Void, Void, List<S3ObjectSummary>> {
        Context mContext;

        RefreshTask(Context context) {
            super();
            mContext = context;
            Log.i(TAG, "Calling constructor");
        }
        @Override
        protected void onPreExecute() {
            //mRefreshButton.setEnabled(false);
            //mRefreshButton.setText(R.string.refreshing);
        }

        @Override
        protected List<S3ObjectSummary> doInBackground(Void... params) {
            // get all the objects in bucket
            Log.i(TAG, "Getting items");
            return mClient.listObjects(Constants.BUCKET_NAME.toLowerCase(Locale.US),
                    Util.getPrefix(mContext)).getObjectSummaries();
        }

        @Override
        protected void onPostExecute(List<S3ObjectSummary> objects) {
            // now that we have all the keys, add them all to the adapter
            mAdapter.clear();
            mAdapter.addAll(objects);
            for(S3ObjectSummary ob : objects){
                Log.i(TAG, "Object: " + ob.getKey());
            }
            mSelectedObjects.clear();
            //mRefreshButton.setEnabled(true);
            //mRefreshButton.setText(R.string.refresh);
        }
    }

    /*
     * This lets the user click on anywhere in the row instead of just the
     * checkbox to select the files to download
     */
    private class ItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int pos,
                                long id) {
            S3ObjectSummary item = mAdapter.getItem(pos);
            boolean checked = false;
            // try removing, if it wasn't there add
            if (!mSelectedObjects.remove(item)) {
                mSelectedObjects.add(item);
                checked = true;
            }
            ((ObjectAdapter.ViewHolder) view.getTag()).checkbox.setChecked(
                    checked);
        }
    }

    /* Adapter for all the S3 objects */
    private class ObjectAdapter extends ArrayAdapter<S3ObjectSummary> {
        public ObjectAdapter(Context context) {
            super(context, R.layout.aws_bucket_row);
        }
        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.aws_bucket_row, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            S3ObjectSummary summary = getItem(pos);
            holder.checkbox.setChecked(mSelectedObjects.contains(summary));
            holder.key.setText(Util.getFileName(summary.getKey()));
            holder.size.setText(String.valueOf(summary.getSize()));
            return convertView;
        }

        public void addAll(Collection<? extends S3ObjectSummary> collection) {
            for (S3ObjectSummary obj : collection) {
                // if statement removes the "folder" from showing up
                if (!obj.getKey().equals(Util.getPrefix(getContext())))
                {
                    add(obj);
                }
            }
        }

        private class ViewHolder {
            private CheckBox checkbox;
            private TextView key;
            private TextView size;

            private ViewHolder(View view) {
                checkbox = (CheckBox) view.findViewById(R.id.checkbox);
                key = (TextView) view.findViewById(R.id.key);
                size = (TextView) view.findViewById(R.id.size);
            }
        }
    }
}
