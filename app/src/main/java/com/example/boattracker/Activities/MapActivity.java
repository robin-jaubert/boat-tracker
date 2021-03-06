package com.example.boattracker.Activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.boattracker.Classes.Containership;
import com.example.boattracker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap boatMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        boatMap = googleMap;

        final Containership boat = (Containership) getIntent().getSerializableExtra("position");

        LatLng lebato = new LatLng(boat.getLatitude(), boat.getLongitude());
        LatLng lepor = new LatLng(boat.getDepart().getLatitude(), boat.getDepart().getLongitude());

        boatMap.addMarker(new MarkerOptions().position(lebato).title("Bateau : " + boat.getCaptain_name() + " " +boat.getBoat_name()).snippet(boat.getLatitude() + ", " + boat.getLongitude()).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapboat)));
        boatMap.addMarker(new MarkerOptions().position(lepor).title("Départ : " + boat.getDepart().getNom_port()).snippet(boat.getDepart().getLatitude() + ", " + boat.getDepart().getLongitude()).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapanchor)));
        boatMap.moveCamera(CameraUpdateFactory.newLatLng(lebato));
    }

}
