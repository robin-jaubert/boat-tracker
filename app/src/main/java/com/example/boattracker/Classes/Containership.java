package com.example.boattracker.Classes;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Containership implements Serializable {

    private int id;
    private String boat_name;
    private String captain_name;

    private double latitude;
    private double longitude;

    private Port depart;

    private ContainershipType type;

    private Container conteneur;

    public static ArrayList<Containership> allTheContainerships = new ArrayList<>();

    public Containership(String boat_name, String captain_name, double latitude, double longitude) {
        this.boat_name = boat_name;
        this.captain_name = captain_name;
        this.latitude = latitude;
        this.longitude = longitude;
        allTheContainerships.add(this);
        this.id = allTheContainerships.size();
    }

    public Containership(int id, String boat_name, String captain_name, double latitude, double longitude) {
        this.boat_name = boat_name;
        this.captain_name = captain_name;
        this.latitude = latitude;
        this.longitude = longitude;
        allTheContainerships.add(this);
        this.id = id;
    }



    public void toDB(){
        Map<String,Object> item = new HashMap<>();
        item.put("id", this.id);
        item.put("boat_name", this.boat_name);
        item.put("captain_name", this.captain_name);
        item.put("latitude", this.latitude);
        item.put("longitude", this.longitude);
        item.put("Depart", "/Port/"+this.depart.getId());
        item.put("Type", "/Type/"+this.type.getId());

        FirebaseFirestore.getInstance().document("Containership/"+this.id).set(item);
        this.depart.toDB();
        this.type.toDB();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBoat_name() {
        return boat_name;
    }

    public void setBoat_name(String boat_name) {
        this.boat_name = boat_name;
    }

    public String getCaptain_name() {
        return captain_name;
    }

    public void setCaptain_name(String captain_name) {
        this.captain_name = captain_name;
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

    public Port getDepart() {
        return depart;
    }

    public void setDepart(Port depart) {
        this.depart = depart;
    }

    public ContainershipType getType() {
        return type;
    }

    public void setType(ContainershipType type) {
        this.type = type;
    }

    public Container getConteneur() {
        return conteneur;
    }

    public void setConteneur(Container conteneur) {
        this.conteneur = conteneur;
    }

    @Override
    public String toString() {
        return "Containership{" +
                "id=" + id +
                ", boat_name='" + boat_name + '\'' +
                ", captain_name='" + captain_name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", depart=" + depart +
                ", type=" + type +
                ", conteneur=" + conteneur +
                '}';
    }
}


