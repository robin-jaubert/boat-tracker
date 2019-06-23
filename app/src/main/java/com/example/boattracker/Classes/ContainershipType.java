package com.example.boattracker.Classes;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ContainershipType implements Serializable {


    private int id;

    private String name;

    private int width;
    private int length;
    private int heigth;

    public ContainershipType(int id, String name) {
        this.id = id;
        this.name = name;
        determineSize(this.name);
    }

    public void determineSize(String name){
        switch (name){
            case "yacht":
                setWidth(5);
                setHeigth(5);
                setLength(20);
                break;

            case "radeau":
                setHeigth(0);
                setLength(2);
                setWidth(1);
                break;

            case "paquebot":
                setHeigth(30);
                setLength(250);
                setWidth(75);
                break;

            case "pirogue":
                setHeigth(1);
                setLength(7);
                setWidth(1);
                break;

            case "peniche":
                setHeigth(1);
                setLength(40);
                setWidth(2);
                break;

            case "caravelle":
                setHeigth(20);
                setLength(50);
                setWidth(6);
                break;

            case "hydroglisseur":
                setHeigth(1);
                setLength(4);
                setWidth(2);
                break;

            default:
                setWidth(3);
                setHeigth(6);
                setLength(12);
                break;
        }
    }


    public String getName() {
        if (this.name.equals("yacht")
                || this.name.equals("paquebot")
                || this.name.equals("radeau")
                || this.name.equals("hydroglisseur")
                ||this.name.equals("caravelle")
                ||this.name.equals("pirogue"))
            return this.name;
        else
            return "inconnu";
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
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
        item.put("name", this.name);
        item.put("width", this.width);
        item.put("height", this.heigth);
        item.put("lenght", this.length);

        FirebaseFirestore.getInstance().document("Type/"+this.id).set(item);
    }

    @Override
    public String toString() {
        return "ContainershipType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", width=" + width +
                ", length=" + length +
                ", heigth=" + heigth +
                '}';
    }
}
