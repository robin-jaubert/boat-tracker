package com.example.boattracker.Classes;

import java.io.Serializable;

public class ContainershipType implements Serializable {

    private static int count = 0;

    private int id;

    private String name;
    private int length, width, heigth;

    public ContainershipType(String name) {
        this.name = name;
        this.id = count;
        determineSize(name);
        //setCount(++count);
    }

    public void determineSize(String name){
        switch (name){
            case "yacht":
                setHeigth(5);
                setLength(20);
                setWidth(5);
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

            case "aeroglisseur":
                setHeigth(1);
                setLength(4);
                setWidth(2);
                break;

            default:
                new ContainershipType("yacht");
                break;
        }
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        ContainershipType.count = count;
    }

    public String getName() {
        return name;
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
}
