package com.example.android.quakereport;

/**
 * Created by namlu on 02-Feb-17.
 */

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /* Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /*
     * Query the USGS dataset and return an {@link Earthquake} object to represent a single earthquake.
     */
    public static List<Earthquake> fetchEarthquakeData(String requestUrl) {
        // Create a URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error obtaining JSON response.", e);
        }

        // Extract relevant fields from the JSON response and create a List of {@link Earthquake}
        List<Earthquake> earthquakes = extractEarthquakesFromJson(jsonResponse);

        // Return the List of {@link Earthquake}
        return earthquakes;
    }

    /*
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL ", e);
        }
        return url;
    }

    /*
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If url is null, then return early
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        /*
         * Create connection to object, setup parameters and general request properties, and
         * make the actual connection to remote object
         */
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            /* time in milliseconds */
            urlConnection.setReadTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();

            /*
             * If connection is successful (Response code = 200), read the input stream
             * and parse the response
             */
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /*
     * Convert the binary data {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        if (inputStream != null) {
            // InputStreamReader reads bytes and decodes them into characters using a specified charset
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            // Since InputStreamReader only read a single character at a time, wrapping it in a
            // BufferedReader will buffer the input before converting into characters and returning
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    /*
     * Return a List of {@link Earthquake} object by parsing out information
     * about the earthquake from the input earthquakeJSON string.
     */
    private static List<Earthquake> extractEarthquakesFromJson(String earthquakeJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        // Create an empty List that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        try {
            JSONObject earthquakeObject = new JSONObject(earthquakeJSON);
            JSONArray featuresArray = earthquakeObject.getJSONArray("features");

            // If there are results in the JSONArray, then continue
            if (featuresArray.length() > 0) {

                // For each earthquake in JSONArray, create an {@link Earthquake} object and add it to
                // the earthquakes List.
                for (int i = 0; i < featuresArray.length(); i++) {

                    // Extract each "Feature" object, and get its "properties" object, which
                    // contains data needed for an {@link Earthquake} object
                    JSONObject currentEarthquake = featuresArray.getJSONObject(i);
                    JSONObject properties = currentEarthquake.getJSONObject("properties");

                    // Extract the magnitude, location, time, and URL from the JSON object
                    double magnitude = properties.getDouble("mag");
                    String location = properties.getString("place");
                    long timeInMills = properties.getLong("time");
                    String url = properties.getString("url");

                    // Add a {@link Earthquake} object to the list earthquakes
                    earthquakes.add(new Earthquake(magnitude, location, timeInMills, url));
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        // Return a list of {@link Earthquake}
        return earthquakes;
    }
}
