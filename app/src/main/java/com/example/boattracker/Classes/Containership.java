package com.example.boattracker.Classes;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Containership implements Serializable {

    public static ArrayList<Containership> allTheContainerships = new ArrayList<>();

    private int id;
    private String boat_name;
    private String captain_name;

    private double latitude;
    private double longitude;

    private Port depart;

    private ContainershipType type;

    private Container conteneur;


    public Containership (ContainershipBuilder builder){
        this.boat_name = builder.boat_name;
        this.captain_name = builder.captain_name;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.depart = builder.depart;
        this.type = builder.type;
        this.conteneur = builder.conteneur;
        this.id = builder.id;
    }

    public void toDB(){
        Map<String,Object> item = new HashMap<>();
        item.put("id", this.id);
        item.put("boat_name", this.boat_name);
        item.put("captain_name", this.captain_name);
        item.put("latitude", this.latitude);
        item.put("longitude", this.longitude);
        item.put("depart", "/Port/"+this.getDepart().getNom_port());
        item.put("type", "/Type/"+this.getType().getName());

        FirebaseFirestore.getInstance().document("Containership/"+this.boat_name).set(item);
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

    public void setDepart(Port depart) {
        this.depart = depart;
    }

    public Port getDepart() {
        return depart;
    }

    //public int getId(){return id;}

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
        return id + " " + "Bateau : " + boat_name + "\n"+ "Nom du capitaine : " + captain_name + "\n" + "Depart : " + depart.getNom_port();
    }

    public int getId(){return this.id;}

    public static class ContainershipBuilder{
        private String boat_name;
        private String captain_name;

        private double latitude;
        private double longitude;

        private Port depart;

        private ContainershipType type;

        private Container conteneur;

        private int id = Containership.allTheContainerships.size();

        public ContainershipBuilder (String name, String captain, double lat, double lon){
            this.boat_name = name;
            this.captain_name = captain;
            this.latitude = lat;
            this.longitude = lon;
        }

        public ContainershipBuilder addPort (Port port){
            this.depart = port;
            return this;
        }

        public ContainershipBuilder addType (ContainershipType type){
            this.type = type;
            return this;
        }

        public ContainershipBuilder addContainer (Container conteneur){
            this.conteneur = conteneur;
            return this;
        }

        public ContainershipBuilder addId (int number){
            this.id = number;
            return this;
        }

        public Containership build(){
            return new Containership(this);
        }

    }
}
