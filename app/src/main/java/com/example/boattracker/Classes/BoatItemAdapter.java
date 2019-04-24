package com.example.boattracker.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.boattracker.R;

import java.util.ArrayList;

public class BoatItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Containership> listBoats;

    public BoatItemAdapter(Context context, ArrayList<Containership> lb){
        this.context = context;
        listBoats = lb;
    }

    @Override
    public int getCount() {
        return listBoats.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.boat_list_textview, parent, false);
        TextView boat = convertView.findViewById(R.id.nameBoat);
        TextView capn = convertView.findViewById(R.id.nameCaptain);

        boat.setText("Bateau : " + listBoats.get(position).getBoat_name());
        capn.setText("Capitaine : " + listBoats.get(position).getCaptain_name());

        return convertView;
    }
}
