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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuakeItemAdapter extends ArrayAdapter<QuakeItem>{

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

        TextView magnitudeTextView = listQuakeItemView.findViewById(R.id.magnitude_text_field);
        magnitudeTextView.setText(currentEarthquake.getMagnitude());//find and display the magnitude

        TextView areaTextView = listQuakeItemView.findViewById(R.id.area_text_field);
        areaTextView.setText(currentEarthquake.getArea());//find and display the place

        // create human readable formate for date and then display it
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());
        String formattedDate = formatDate(dateObject);
        TextView dateTextView = listQuakeItemView.findViewById(R.id.date_text_field);
        dateTextView.setText(formattedDate);

        //create human readable formate for time and then display it
        TextView timeTextView = listQuakeItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeTextView.setText(formattedTime);

        return listQuakeItemView;
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
