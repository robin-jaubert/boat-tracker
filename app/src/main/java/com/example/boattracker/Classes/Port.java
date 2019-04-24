package com.example.boattracker.Classes;

import android.location.Location;

import java.io.Serializable;

public class Port implements Serializable {

    private static int count = 0;

    private int id;

    private String nom_port;

    private double latitude;
    private double longitude;


    public Port(String nom_port, double latitude, double longitude) {
        setNom_port(nom_port);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public String getNom_port() {
        return nom_port;
    }

    public void setNom_port(String nom_port) {
        this.nom_port = nom_port;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
