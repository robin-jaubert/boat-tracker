package com.example.boattracker.Activities;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.boattracker.Classes.Containership;
import com.example.boattracker.R;

public class DescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_itemview);

        final Containership boatReceived = (Containership) getIntent().getSerializableExtra("clicked");

        TextView nomBateau = findViewById(R.id.description_info_boat_name);
        TextView nomModele = findViewById(R.id.description_info_boat_model);

        //Toast.makeText(getApplicationContext(), boatReceived.getCurrentLocation().distanceTo(boatReceived.getDepart().getPointDepart()) + "", Toast.LENGTH_SHORT).show();

        //original
        nomBateau.setText("Bateau : " + boatReceived.getBoat_name());
        nomModele.setText("Modele : " + boatReceived.getType().getName());

        final Button distance = findViewById(R.id.description_button_distance);
        distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude, longitude, latitudeport, longitudeport;

                latitude = boatReceived.getLatitude();
                longitude = boatReceived.getLongitude();
                latitudeport = boatReceived.getDepart().getLatitude();
                longitudeport = boatReceived.getDepart().getLongitude();


                Location portDepart = new Location("");
                Location pointActuel = new Location("");

                portDepart.setLatitude(latitudeport);
                portDepart.setLongitude(longitudeport);
                pointActuel.setLatitude(latitude);
                pointActuel.setLongitude(longitude);

                TextView calculDistance = findViewById(R.id.description_button_calculee);
                calculDistance.setText(pointActuel.distanceTo(portDepart)/1000 + " km");


            }
        });

        final Button toMaps = findViewById(R.id.description_toMaps_button);
        toMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, MapActivity.class);
                intent.putExtra("position", boatReceived);

                startActivity(intent);
            }
        });

        final Button toModif = findViewById(R.id.description_modify_button);
        toModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, ModificationActivity.class);

                startActivity(intent);
            }
        });
    }
}
