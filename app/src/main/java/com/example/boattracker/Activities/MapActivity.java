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


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        boatMap = googleMap;

        final Containership boat = (Containership) getIntent().getSerializableExtra("position");

        // Add a marker in Sydney and move the camera
        LatLng lebato = new LatLng(boat.getLatitude(), boat.getLongitude());
        LatLng lepor = new LatLng(boat.getDepart().getLatitude(), boat.getDepart().getLongitude());

        boatMap.addMarker(new MarkerOptions().position(lebato).title("Bateau : " + boat.getCaptain_name() + " " +boat.getBoat_name()).snippet(boat.getLatitude() + ", " + boat.getLongitude()).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapboat)));
        boatMap.addMarker(new MarkerOptions().position(lepor).title("Départ : " + boat.getDepart().getNom_port()).snippet(boat.getDepart().getLatitude() + ", " + boat.getDepart().getLongitude()).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapanchor)));
        boatMap.moveCamera(CameraUpdateFactory.newLatLng(lebato));
    }
}
