package com.example.boattracker.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.boattracker.Classes.Containership;
import com.example.boattracker.R;

public class ModificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modification);

        EditText nom = findViewById(R.id.modif_boat_name);
        String boatname = nom.getText().toString();
        EditText capitaine = findViewById(R.id.modif_captain_name);
        String capnname = capitaine.getText().toString();
        System.out.println(boatname + '\n' + capnname);


    }
}