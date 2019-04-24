package com.example.boattracker.Classes;

import java.io.Serializable;

public class Containership implements Serializable {

    private static int count = 0;

    private int id;

    private String boat_name;
    private String captain_name;

    private double latitude;
    private double longitude;

    private Port depart;

    private ContainershipType type;


    public Containership(String boat_name, String captain_name, double latitude, double longitude, Port port/*, String model*/) {
        this.setBoat_name(boat_name);
        this.setCaptain_name(captain_name);
        setLatitude(latitude);
        setLongitude(longitude);
        setDepart(port);
        //this.type = new ContainershipType(model);
        /*this.id = count;
        setCount(++count);*/
    }

    /*private void setCount(int i) {
        count = i;
    }*/

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

    public int getId(){return id;}

    @Override
    public String toString() {
        return "Bateau : " + boat_name + "\n"+ "Nom du capitaine : " + captain_name;
    }
}
