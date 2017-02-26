package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by namlu on 23-Feb-17.
 *
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>>{

    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    private String mUrl;

    /*
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /*
     * Performs actual task in background and returns the result.
     */
    @Override
    public List<Earthquake> loadInBackground() {
        Log.v(LOG_TAG, "inside loadInBackground()");

        // Submit task only if URL entry is not null
        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchEarthquakeData(mUrl);
    }
}
