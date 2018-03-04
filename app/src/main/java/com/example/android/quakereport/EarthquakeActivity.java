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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=3&limit=20";
    QuakeItemAdapter mAdapter;

    /*------------------------------------------------------------------Methods---------------------------------------------------------------------**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        final List<QuakeItem> earthquakes =  new ArrayList<QuakeItem>();

        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        mAdapter = new QuakeItemAdapter(this, earthquakes);
        earthquakeListView.setAdapter(mAdapter);

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

        EarthQuakeAsyncTask task = new EarthQuakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }
    /*-----------------------------------------------------------------Inner class-------------------------------------------------------------------**/
    private class EarthQuakeAsyncTask extends AsyncTask<String, Void, List<QuakeItem>> {

        @Override
        protected List<QuakeItem> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<QuakeItem> earthquakeData = QueryUtils.fetchEarthquakeData(urls[0]);
            return earthquakeData;
        }

        @Override
        protected void onPostExecute(List<QuakeItem> earthquakeData) {
            mAdapter.clear();
            if (earthquakeData != null && !earthquakeData.isEmpty()) {
                mAdapter.addAll(earthquakeData);
            }
        }
    }
    /*-----------------------------------------------------------------------------------------------------------------------------------------------**/

}
