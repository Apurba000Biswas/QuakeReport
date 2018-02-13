package com.example.android.quakereport;

/*
 * Created by Apurba on 2/14/2018.
 */

public class QuakeItem {

    private String mMagnitude;
    private String mArea;
    private String mDate;

    public QuakeItem(String magnitude, String area, String date){
        this.mMagnitude = magnitude;
        this.mArea = area;
        this.mDate = date;
    }

    public String getMagnitude(){
        return mMagnitude;
    }
    public String getArea(){
        return mArea;
    }
    public String getDate(){
        return mDate;
    }
}
