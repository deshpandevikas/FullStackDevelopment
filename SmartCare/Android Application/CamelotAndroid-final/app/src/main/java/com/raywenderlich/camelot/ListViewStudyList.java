package com.raywenderlich.camelot;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vikas Deshpande on 12/10/2017.
 */

public class ListViewStudyList extends ArrayAdapter<StudiesAttributes> {

    List<StudiesAttributes> Nobjects;
    Context mcontext;
    int mResource;

    public ListViewStudyList(Context context, int resource, List<StudiesAttributes> objects) {
        super(context, resource, objects);

        this.mcontext = context;
        this.Nobjects=objects;
        this.mResource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mResource,parent,false);

        }

        StudiesAttributes tempnews = Nobjects.get(position);

        TextView tvtitle = (TextView) convertView.findViewById(R.id.tvStudyName);
        tvtitle.setText(tempnews.getStudyName());
        tvtitle.setTextColor(Color.BLACK);

        TextView tvdesc = (TextView) convertView.findViewById(R.id.tvStudyDescription);
        tvdesc.setText(tempnews.getStudyDesc());
        tvdesc.setTextColor(Color.BLACK);



        return convertView;

    }
}
