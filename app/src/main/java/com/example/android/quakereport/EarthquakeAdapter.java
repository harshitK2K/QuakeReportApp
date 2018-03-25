package com.example.android.quakereport;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by harshit on 19/1/18.
 */

public class EarthquakeAdapter extends ArrayAdapter<EarthquakeClass> {

    ArrayList<EarthquakeClass> list ;
    public EarthquakeAdapter(EarthquakeActivity context, ArrayList<EarthquakeClass> words) {
        super(context, 0, words);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        EarthquakeClass earthquake = getItem(position);
        TextView tx = (TextView) listItemView.findViewById(R.id.magnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) tx.getBackground();
        int color = findColor(earthquake.getMagnitude());
        magnitudeCircle.setColor(color);
        String magnitude = Double.toString(earthquake.getMagnitude());
        tx.setText(magnitude);
        String place = earthquake.getPlace();
        String str[] = new String[10];
        if(!place.contains("of")){
            str[0] = "Near Of";
            str[1] = place;
        }
        else{
            str = place.split("of");
            str[0] += "of";
        }
        TextView dx = (TextView) listItemView.findViewById(R.id.distance);
        dx.setText(str[0]);
        TextView px = (TextView) listItemView.findViewById(R.id.place);
        px.setText(str[1]);
        Date date = new Date(earthquake.getDate());
        TextView hx = (TextView) listItemView.findViewById(R.id.date);
        String dateFormat = formatDate(date);
        hx.setText(dateFormat);
        TextView gx = (TextView) listItemView.findViewById(R.id.time);
        String timeFormat = formattime(date);
        gx.setText(timeFormat);
        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    private String formattime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private int findColor(Double val){
        int color;
        int magnitude = (int) Math.floor(val);
        switch (magnitude){
            case 0:color = ContextCompat.getColor(getContext(), R.color.magnitude1);
                    break;
            case 1:color = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            case 2:color = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 3:color = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 4:color = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 5:color = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 6:color = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 7:color = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 8:color = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 9:color = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            case 10:color = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                break;
            default:color = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                    break;

        }
        return color;
    }


}
