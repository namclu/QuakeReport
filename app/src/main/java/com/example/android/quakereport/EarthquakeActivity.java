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

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private EarthquakeItemsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list_earthquake);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeItemsAdapter(this, R.layout.earthquake_list_item, new ArrayList());

        // Set the adapter on the {@link ListView} so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        getLoaderManager().initLoader(1, null, this).forceLoad();
        Log.v(LOG_TAG, "inside onCreate(), after initLoader() called");

        // Set OnItemClickListener onto the earthquake list item to open URL of the quake event
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the current Earthquake item that was clicked on
                Earthquake currentEarthquakeItem = mAdapter.getItem(position);

                // Get URL of the current Earthquake object
                String url = currentEarthquakeItem.getUrl();

                // Create a new Intent to view Earthquake URL
                Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));

                // Send Intent to launch a new activity
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    /*
     * called when the system needs a new loader to be created. Your code should create a Loader
     * object and return it to the system.
     */
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        Log.v(LOG_TAG, "inside onCreateLoader(int, Bundle)");

        return new EarthquakeLoader(EarthquakeActivity.this, USGS_REQUEST_URL);
    }

    /*
     * called when a loader has finished loading data. Typically, your code should display
     * the data to the user.
     */
    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakeData) {
        Log.v(LOG_TAG, "inside onLoadFinished()");

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid List of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakeData != null && !earthquakeData.isEmpty()) {
            mAdapter.addAll(earthquakeData);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.v(LOG_TAG, "inside onLoaderReset()");

        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
