/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<QuakeItem>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=3&limit=20";
    private QuakeItemAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View loading_indicator;

    /*------------------------------------------------------------------Methods---------------------------------------------------------------------**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "TEST: EarthQuakeActivity onCreateCalled");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //List of earthquake data
        final List<QuakeItem> earthquakes =  new ArrayList<QuakeItem>();

        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        mAdapter = new QuakeItemAdapter(this, earthquakes);
        earthquakeListView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        loading_indicator = findViewById(R.id.loading_spinner);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                QuakeItem clickedEarthQuake = earthquakes.get(i);
                String url = clickedEarthQuake.getUrl();

                //Implicit intent that contains a url to got
                Intent intent = new Intent(Intent.ACTION_VIEW); // ACTION_VIEW is used to open Web browser
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        if(isConnectedToInternet()){
            LoaderManager loaderManager = getSupportLoaderManager();
            Log.i(LOG_TAG, "TEST: calling initLoader().... ");
            loaderManager.initLoader(0, null, EarthquakeActivity.this).forceLoad();
        }else {
            loading_indicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    private boolean isConnectedToInternet(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    public Loader<List<QuakeItem>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "TEST: called onCreateLoader() ");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<QuakeItem>> loader, List<QuakeItem> earthquakes) {
        Log.i(LOG_TAG, "TEST: called onLoadFinished() ");
        loading_indicator.setVisibility(View.GONE);

        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
        mEmptyStateTextView.setText(R.string.no_earthquakes);
    }

    @Override
    public void onLoaderReset(Loader<List<QuakeItem>> loader) {
        Log.i(LOG_TAG, "TEST: called onLoadReset() ");
        mAdapter.clear();
    }
}
