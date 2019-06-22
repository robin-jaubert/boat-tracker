package com.example.boattracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boattracker.R;

public class ActivityCreate extends AppCompatActivity {

    private String newName;
    private String newCapn;
    private String newModel;
    private double newLatPos = 0.0;
    private double newLongPos = 0.0;
    private String newStart;
    private double newLatStart = 0.0;
    private double newLongStart = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        final Button annuler = findViewById(R.id.create_cancel_button);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityCreate.this, BoatListActivity.class);
                startActivity(intent);
            }
        });



        final Button confirmer = findViewById(R.id.create_accept_button);
        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText boatname = findViewById(R.id.create_boat_name);
                EditText capnname = findViewById(R.id.create_captain_name);
                EditText model = findViewById(R.id.create_model_name);
                EditText posLat = findViewById(R.id.create_pos_lat_name);
                EditText posLong = findViewById(R.id.create_pos_long_name);
                EditText startName = findViewById(R.id.create_port_name_name);
                EditText startLat = findViewById(R.id.create_port_lat_name);
                EditText startLong = findViewById(R.id.create_port_long_name);
                if (isEmpty(boatname)
                        ||isEmpty(capnname)
                        ||isEmpty(model)
                        ||isEmpty(posLat)
                        ||isEmpty(posLong)
                        ||isEmpty(startName)
                        ||isEmpty(startLat)
                        ||isEmpty(startLong)){
                    Toast.makeText(getApplicationContext(), "Tous les champs doivent etre remplis", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    Toast.makeText(getApplicationContext(), "check ok", Toast.LENGTH_SHORT).show();
                }

                setNewName(boatname.getText().toString());


                setNewCapn(capnname.getText().toString());


                setNewModel(model.getText().toString());


                setNewLatPos(Double.parseDouble(posLat.getText().toString()));


                setNewLongPos(Double.parseDouble(posLong.getText().toString()));


                setNewStart(startName.getText().toString());


                setNewLatStart(Double.parseDouble(startLat.getText().toString()));


                setNewLongStart(Double.parseDouble(startLong.getText().toString()));



                /*alertVoidDouble(posLat, newLatPos);
                alertVoidDouble(posLong, newLongPos);
                alertVoidDouble(startLat, newLatPos);
                alertVoidDouble(startLong, newLongStart);

                alertVoidString(boatname, newName);
                alertVoidString(capnname, newCapn);
                alertVoidString(model, newModel);
                alertVoidString(startName, newStart);*/


            }
        });
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewCapn() {
        return newCapn;
    }

    public void setNewCapn(String newCapn) {
        this.newCapn = newCapn;
    }

    public String getNewModel() {
        return newModel;
    }

    public void setNewModel(String newModel) {
        this.newModel = newModel;
    }

    public double getNewLatPos() {
        return newLatPos;
    }

    public void setNewLatPos(double newLatPos) {
        this.newLatPos = newLatPos;
    }

    public double getNewLongPos() {
        return newLongPos;
    }

    public void setNewLongPos(double newLongPos) {
        this.newLongPos = newLongPos;
    }

    public double getNewLatStart() {
        return newLatStart;
    }

    public void setNewLatStart(double newLatStart) {
        this.newLatStart = newLatStart;
    }

    public double getNewLongStart() {
        return newLongStart;
    }

    public void setNewLongStart(double newLongStart) {
        this.newLongStart = newLongStart;
    }

    public String getNewStart() {
        return newStart;
    }

    public void setNewStart(String newStart) {
        this.newStart = newStart;
    }

    private boolean isEmpty(EditText eT){
        return eT.getText().toString().trim().length() == 0;
    }
}
