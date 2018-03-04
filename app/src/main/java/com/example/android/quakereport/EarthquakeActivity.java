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
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        EarthQuakeAsyncTask task = new EarthQuakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }

    private class EarthQuakeAsyncTask extends AsyncTask<String, Void, List<QuakeItem>> {

        @Override
        protected List<QuakeItem> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }


            // Perform the HTTP request for earthquake data and process the response.
            List<QuakeItem> earthquakeData = QueryUtils.extractEarthquakes();
            return earthquakeData;
        }

        @Override
        protected void onPostExecute(List<QuakeItem> earthquakeData) {
            // If there is no result, do nothing.
            if (earthquakeData.get(0) == null) {
                return;
            }

            updateUi(earthquakeData);
        }
    }

    private void updateUi(List<QuakeItem> earthquakeData){
        // Create a list of earthquake locations.
        final List<QuakeItem> earthquakes =  earthquakeData;

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        QuakeItemAdapter adapter = new QuakeItemAdapter(this, earthquakes);
        earthquakeListView.setAdapter(adapter); //Adapter sets here

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
    }
}
