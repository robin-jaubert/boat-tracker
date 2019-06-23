package com.example.boattracker.Classes;

import java.io.Serializable;

public class Container implements Serializable {


    private int id;

    private int length;
    private int width;
    private int heigth;

    public Container (int id, int l, int hw){
        this.length = l;
        this.width = hw;
        this.heigth = hw;
        this.id = id;
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
}
