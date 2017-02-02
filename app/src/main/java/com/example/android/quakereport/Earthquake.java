package com.example.android.quakereport;

/**
 * Created by namlu on 30-Jan-17.
 */

public class Earthquake {

    /*
    * @param mMagnitude the magnitude of the earthquake
    * @param mLocation the location of the earthquake
    * @param mDate the date the earthquake took place
    * */
    private double mMagnitude;
    private String mLocation;
    private int mDate;

    /*
    * Creates a new Earthquake object and give it a magnitude, location and date
    * */
    public Earthquake(double magnitude, String location, int date) {
        setMagnitude(magnitude);
        setLocation(location);
        setDate(date);
    }

    /*
    * Getters and setter methods
    * */

    public double getMagnitude() {
        return mMagnitude;
    }

    public void setMagnitude(double magnitude) {
        mMagnitude = magnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public int getDate() {
        return mDate;
    }

    public void setDate(int date) {
        mDate = date;
    }
}

