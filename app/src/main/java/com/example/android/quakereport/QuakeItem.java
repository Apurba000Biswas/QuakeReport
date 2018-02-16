package com.example.android.quakereport;

/*
 * Created by Apurba on 2/14/2018.
 */


public class QuakeItem {
    private static final String LOCATION_SEPARATOR = " of ";

    private String mMagnitude;
    private String mLocation;
    private String locationOffset;
    private String primaryLocation;
    private long mTimeInMilliseconds;

    public QuakeItem(String magnitude, String location, long timeInMillisecond){
        this.mMagnitude = magnitude;
        this.mTimeInMilliseconds = timeInMillisecond;
        this.mLocation = location;
    }

    public String getMagnitude(){
        return mMagnitude;
    }
    public long getTimeInMilliseconds(){
        return mTimeInMilliseconds;
    }
    public String getLocationOffset(){
        return locationOffset;
    }
    public String getPrimaryLocation(){
        return primaryLocation;
    }

    /**
     * Split the location String into two string
     *
     */
    public void splitLocation(){
        if(mLocation.contains(LOCATION_SEPARATOR)) {
            String[] locationParts = mLocation.split("of");
            locationOffset = locationParts[0] + LOCATION_SEPARATOR;
            primaryLocation = locationParts[1];
        }else {
            locationOffset = "Near the";
            primaryLocation = mLocation;
        }
    }
}
