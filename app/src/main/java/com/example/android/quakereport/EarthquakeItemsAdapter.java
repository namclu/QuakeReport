package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by namlu on 30-Jan-17.
 *
 * {@link Earthquake} is an {@link ArrayAdapter} that can provide the layout for each list item
 * based on a data source, which is a list of {@link Earthquake} objects.
 */

public class EarthquakeItemsAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeItemsAdapter(Context context, int resource, List<Earthquake> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        Earthquake currentEarthquakeItem = getItem(position);

        /*
         * Find the TextView in the earthquake_list_item.xml layout with the corresponding ID,
         * then get the currentEarthquakeItem object's magnitude, location, date, or time and set
         * the value to the correct view.
         */
        TextView magnitudeTextView = (TextView) convertView.findViewById(R.id.text_earthquake_mag);
        magnitudeTextView.setText(Double.toString(currentEarthquakeItem.getMagnitude()));

        TextView locationTextView = (TextView) convertView.findViewById(R.id.text_earthquake_location);
        locationTextView.setText(currentEarthquakeItem.getLocation());

        Date dateObj = new Date(currentEarthquakeItem.getTimeInMills());

        // Find TextView with view ID text_earthquake_date
        TextView dateTextView = (TextView) convertView.findViewById(R.id.text_earthquake_date);
        // Display the formatted date of the current earthquake
        dateTextView.setText(formatDate(dateObj));

        // Find TextView with view ID text_earthquake_time
        TextView timeTextView = (TextView) convertView.findViewById(R.id.text_earthquake_time);
        // Display the formatted time of the current earthquake
        timeTextView.setText(formatTime(dateObj));

        // Return the whole Earthquake item layout so that it can be shown in the ListView
        return convertView;
    }

    // Return a formatted date string (i.e. Jan 1, 2000 ) from a Date object.
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(date);
    }

    // Return a formatted time string (i.e. 1:00 PM ) from a Date object.
    private String formatTime(Date time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(time);
    }
}
