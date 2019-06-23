package com.example.boattracker.Classes;

import android.location.Location;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Port implements Serializable {

    private int id;

    private String nom_port;

    private double latitude;
    private double longitude;


    public Port(int id, String nom_port, double latitude, double longitude) {
        setId(id);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void toDB(){
        Map<String, Object> item = new HashMap<>();
        item.put("id", this.id);
        item.put("name", this.nom_port);
        item.put("latitude", this.latitude);
        item.put("longitude", this.longitude);

        FirebaseFirestore.getInstance().document("Port/"+this.id).set(item);
    }

    @Override
    public String toString() {
        return "Port{" +
                "id=" + id +
                ", nom_port='" + nom_port + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
