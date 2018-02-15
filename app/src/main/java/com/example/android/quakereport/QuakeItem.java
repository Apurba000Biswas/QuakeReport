package com.example.android.quakereport;

/*
 * Created by Apurba on 2/14/2018.
 */

public class QuakeItem {

    private String mMagnitude;
    private String mArea;
    private long mTimeInMilliseconds;

    public QuakeItem(String magnitude, String area, long timeInMillisecond){
        this.mMagnitude = magnitude;
        this.mArea = area;
        this.mTimeInMilliseconds = timeInMillisecond;
    }

    public String getMagnitude(){
        return mMagnitude;
    }
    public String getArea(){
        return mArea;
    }
    public long getTimeInMilliseconds(){
        return mTimeInMilliseconds;
    }
}
