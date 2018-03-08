package com.example.android.quakereport;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

/*
 * Created by Apurba on 3/7/2018.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<QuakeItem>> {
    public static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();
    String url;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST: called onStartLoading() ");
        forceLoad();
    }

    @Override
    public List<QuakeItem> loadInBackground() {
        Log.i(LOG_TAG, "TEST: called loadInBackground() ");
        if (url.length() < 1) {
            return null;
        }
        List<QuakeItem> earthquakeData = QueryUtils.fetchEarthquakeData(url);
        return earthquakeData;
    }
}
