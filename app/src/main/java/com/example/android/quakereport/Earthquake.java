package com.example.android.quakereport;

/**
 * Created by namlu on 30-Jan-17.
 */

public class Earthquake {

    /*
    * @param mMagnitude the magnitude of the event
    * @param mLocation the location of the event
    * @param mDate the date the event tool place
    * */
    private int mMagnitude;
    private String mLocation;
    private String mDate;

    /*
    * Creates a new Earthquake object and give it a magnitude, location and date
    * */
    public Earthquake(int magnitude, String location, String date) {
        setMagnitude(magnitude);
        setLocation(location);
        setDate(date);
    }

    /*
    * Getters and setter methods
    * */

    public int getMagnitude() {
        return mMagnitude;
    }

    public void setMagnitude(int magnitude) {
        mMagnitude = magnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }
}

