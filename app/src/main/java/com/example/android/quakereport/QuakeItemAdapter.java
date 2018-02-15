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
import java.util.ArrayList;

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
        TextView magnitudeTextView = listQuakeItemView.findViewById(R.id.magnitude_text_field);
        TextView areaTextView = listQuakeItemView.findViewById(R.id.area_text_field);
        TextView dateTextView = listQuakeItemView.findViewById(R.id.date_text_field);


        QuakeItem currentQuakeItem = getItem(position);
        magnitudeTextView.setText(currentQuakeItem.getMagnitude());
        areaTextView.setText(currentQuakeItem.getArea());
        dateTextView.setText(currentQuakeItem.getDate());
        return listQuakeItemView;
    }

}
