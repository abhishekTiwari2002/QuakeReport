package com.example.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.List;

public class customarrayadapter extends ArrayAdapter {
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem = convertView;
        if (listitem == null) {
            listitem = LayoutInflater.from(mContext).inflate(R.layout.listview, parent, false);
        }
        EarthQuake info = al.get(position);
        TextView forMag = (TextView) listitem.findViewById(R.id.textview);
        GradientDrawable circle = (GradientDrawable) forMag.getBackground();
        int f = getMagnitudeColor((int) info.getMagnitude());
        circle.setColor(f);
        DecimalFormat dm = new DecimalFormat("0.0");
        String d = dm.format(info.getMagnitude());
        forMag.setText(d);
        String ss = info.getQuakeName();
        TextView forName = (TextView) listitem.findViewById(R.id.textview1);;
        TextView forsecond = (TextView) listitem.findViewById(R.id.fosecond);;
        if(ss.contains("of")){
        String array[] = ss.split("of");
        forName.setText(array[0] + "of");
        forsecond.setText(array[1]);
    }else{
            forName.setText("O Km of");
            forsecond.setText(ss);
        }
        TextView forData=(TextView)listitem.findViewById(R.id.textview2);
       forData.setText(info.getDate());
        return listitem;
    }

    Context mContext;
    List<EarthQuake> al;
    public customarrayadapter(@NonNull Context context, List al) {
        super(context,0,al);
        mContext=context;
        this.al=al;
    }
    public int getMagnitudeColor(int a){
        int color=0;
        switch (a){
            case 1: color=R.color.magnitude1;
            break;
            case 2:color=R.color.magnitude2;
            break;
            case 3: color=R.color.magnitude3;
                break;
            case 4:color=R.color.magnitude4;
                break;
            case 5: color=R.color.magnitude5;
                break;
            case 6:color=R.color.magnitude6;
                break;
            case 7: color=R.color.magnitude7;
                break;
            case 8:color=R.color.magnitude8;
                break;
            case 9: color=R.color.magnitude9;
                break;
            default:color=R.color.magnitude10plus;
        }
        return ContextCompat.getColor(getContext(),color);
    }
}
