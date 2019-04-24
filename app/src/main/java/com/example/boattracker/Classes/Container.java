package com.example.boattracker.Classes;

import java.io.Serializable;

public class Container implements Serializable {

    private static int count = 0;

    private int id;

    private int length;
    private int width;
    private int heigth;

    public Container (int l, int hw){
        this.length = l;
        this.width = hw;
        this.heigth = hw;
        /*this.id = count;
        setCount(++count);*/
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

    public static void setCount(int count) {
        Container.count = count;
    }

    public int getId() {
        return id;
    }
}
