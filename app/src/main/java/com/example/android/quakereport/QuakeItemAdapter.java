package com.example.android.quakereport;

/*
 * Created by Apurba on 2/14/2018.
 */

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuakeItemAdapter extends ArrayAdapter<QuakeItem>{

    //Constructor
    public QuakeItemAdapter(Activity context, ArrayList<QuakeItem> quakeItems){
        super(context, 0, quakeItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listQuakeItemView = convertView;
        if(listQuakeItemView == null) {
            listQuakeItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_quake_item, parent, false);
        }
        QuakeItem currentEarthquake = getItem(position);//get the current item to create view
        setAllViews(currentEarthquake, listQuakeItemView);
        return listQuakeItemView;
    }
    /**
     * Sets all views in the current position of the list view with
     * formated views
     *
     */
    private void setAllViews(QuakeItem currentEarthquake, View listQuakeItemView){
        //find and Set the magnitude
        setATextView((TextView) listQuakeItemView.findViewById(R.id.magnitude_text_field), formatedMagnitude(currentEarthquake.getMagnitude()));

        //find and set the location offset
        currentEarthquake.splitLocation();
        setATextView((TextView) listQuakeItemView.findViewById(R.id.location_offset), currentEarthquake.getLocationOffset());

        //find and set the primary location
        setATextView((TextView) listQuakeItemView.findViewById(R.id.primary_location), currentEarthquake.getPrimaryLocation());

        // create human readable formate for date and then display it
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());
        setATextView((TextView) listQuakeItemView.findViewById(R.id.date_text_field), formatDate(dateObject));

        //create human readable formate for time and then display it
        setATextView((TextView) listQuakeItemView.findViewById(R.id.time), formatTime(dateObject));
    }
    /**
     * Set the Specified Text view with a given text
     */
    private void setATextView(TextView textView, String text){
        textView.setText(text);
    }
    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatedMagnitude(double mag){
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        return decimalFormatter.format(mag);
    }
    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
