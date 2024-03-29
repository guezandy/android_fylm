package com.fylm.service;


import android.content.Context;
import android.database.DataSetObserver;
import android.widget.BaseAdapter;

import com.parse.ParseQueryAdapter;

import java.util.List;

/**
 * Interface to define interactions with Parse.
 *
 * Created by benjamin on 9/19/14.
 */
public interface IParseService {

    public void getStoresByTags(Context context, IParseCallback<List<String>> stores);

    public void getStoresByCartItems(Context context, IParseCallback<List<String>> stores);

    public BaseAdapter getTagHistoryAdapter(Context context, String store);

    public BaseAdapter CartAdapter(Context context, String store, DataSetObserver dataChangedObserver);

    public ParseQueryAdapter TagHistoryAdapter(Context context, String store, DataSetObserver dataChangedObserver);

    //public void getTagHistory(final Context context, final String storey, final IParseCallback<List<TagHistoryItem>> itemsCallback);

    //public void getAllStores(final Context context, final IParseCallback<List<StoreItem>> itemsCallback);
    /**
     *Returns cart items
     * @param context
     * @param store
     * @param dataChangedObserver Callback so when the list gets updated asyncronously we can update the totals.
     * @return
     */
    public BaseAdapter getCartItemAdapter(final Context context, String store, DataSetObserver dataChangedObserver);

    /**
     * Returns checkout items
     * @param context
     * @param store
     * @param dataChangedObserver
     * @return
     */
    public BaseAdapter getCheckoutItemAdapter(final Context context, String store, DataSetObserver dataChangedObserver);

    /**
     * Returns outfit items
     * @param context
     * @param store
     * @param dataChangedObserver
     * @return
     */
    public BaseAdapter getOutfitItemAdapter(final Context context, String store, DataSetObserver dataChangedObserver);

    /**
     * Combines closet and snags to return tops
     * @param context
     *
     * @return
     */
    //public void getTops(final Context context, final IParseCallback<List<TagHistoryItem>> callback);

    /**
     * Combines closet and snags to return bottoms
     * @param context
     *
     * @return
     */
    //public void getBottoms(final Context context, final IParseCallback<List<TagHistoryItem>> callback);

    /**
     * Combines closet and snags to return shoes.
     * @param context
     *
     * @return
     */
    //public void getShoes(final Context context, final IParseCallback<List<TagHistoryItem>> callback);

    /**
     *
     * @param context
     * @param callback
     */
    //public void getClosetTops(final Context context, final IParseCallback<List<TagHistoryItem>> callback);

    /**
     *
     * @param context
     * @param callback
     */
    //public void getClosetBottoms(final Context context, final IParseCallback<List<TagHistoryItem>> callback);

    /**
     *
     * @param context
     * @param callback
     */
    //public void getClosetShoes(final Context context, final IParseCallback<List<TagHistoryItem>> callback);

}
