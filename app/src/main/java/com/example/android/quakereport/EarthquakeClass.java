package com.example.android.quakereport;

/**
 * Created by harshit on 19/1/18.
 */

public class EarthquakeClass {

    private double magnitude;
    private String place;
    private long date;
    private String url;

    public EarthquakeClass(double mag,String str1,long str2,String str){
        magnitude = mag;
        place = str1;
        date = str2;
        url = str;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public long getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getPlace() {
        return place;
    }
}
