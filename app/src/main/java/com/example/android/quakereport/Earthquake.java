package com.example.android.quakereport;

/**
 * Created by namlu on 30-Jan-17.
 */

public class Earthquake {

    /*
    * @param mMagnitude the magnitude of the earthquake
    * @param mLocation the location of the earthquake
    * @param mTimeInMills the time (in Milliseconds) of when the earthquake took place
    * */
    private double mMagnitude;
    private String mLocation;
    private long mTimeInMills;

    /*
    * Constructs a new {@link Earthquake} object and initialize it w/ a magnitude, location and time
    * */
    public Earthquake(double magnitude, String location, long timeInMills) {
        setMagnitude(magnitude);
        setLocation(location);
        setTimeInMills(timeInMills);
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

    public long getTimeInMills() {
        return mTimeInMills;
    }

    public void setTimeInMills(long timeInMills) {
        mTimeInMills = timeInMills;
    }
}

