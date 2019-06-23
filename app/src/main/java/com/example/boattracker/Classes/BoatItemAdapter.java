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


    private TextView boat, capn, depart;
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
        return listBoats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listBoats.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.boat_list_textview, parent, false);
        boat = convertView.findViewById(R.id.nameBoat);
        capn = convertView.findViewById(R.id.nameCaptain);
        depart = convertView.findViewById(R.id.nameDepart);

        boat.setText("Bateau : " + listBoats.get(position).getBoat_name());
        capn.setText("Capitaine : " + listBoats.get(position).getCaptain_name());
        depart.setText("Depart : " + listBoats.get(position).getDepart().getNom_port());
        return convertView;
    }

    public void maj(){
        boat.setText("Bateau : " + listBoats.get(0).getBoat_name());
        capn.setText("Capitaine : " + listBoats.get(0).getCaptain_name());
        depart.setText("Depart : " + listBoats.get(0).getDepart().getNom_port());
    }
}
