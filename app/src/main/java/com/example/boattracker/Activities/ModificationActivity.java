package com.example.boattracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boattracker.Classes.Containership;
import com.example.boattracker.Classes.ContainershipType;
import com.example.boattracker.Classes.Database;
import com.example.boattracker.Classes.Port;
import com.example.boattracker.R;

public class ModificationActivity extends AppCompatActivity {

    private String updateName;
    private String updateCapn;
    private String updateModel;
    private double updateLatPos = 0.0;
    private double updateLongPos = 0.0;
    private String updateStart;
    private double updateLatStart = 0.0;
    private double updateLongStart = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modification);

        final Containership toModif = (Containership) getIntent().getSerializableExtra("toModif");

        final Button annuler = findViewById(R.id.modif_cancel_button);
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModificationActivity.this, DescriptionActivity.class);
                startActivity(intent);
            }
        });



        final Button confirmer = findViewById(R.id.modif_accept_button);
        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText boatname = findViewById(R.id.modif_boat_name);
                EditText capnname = findViewById(R.id.modif_captain_name);
                EditText model = findViewById(R.id.modif_model_name);
                EditText posLat = findViewById(R.id.modif_pos_lat_name);
                EditText posLong = findViewById(R.id.modif_pos_long_name);
                EditText startName = findViewById(R.id.modif_port_name_name);
                EditText startLat = findViewById(R.id.modif_port_lat_name);
                EditText startLong = findViewById(R.id.modif_port_long_name);

                int i = toModif.getId();
                int iType = toModif.getType().getId();
                int iStart = toModif.getDepart().getId();
                updateName = (String) checkEmptyInput(toModif.getBoat_name(), boatname);
                updateCapn = (String) checkEmptyInput(toModif.getCaptain_name(), capnname);
                updateModel = (String) checkEmptyInput(toModif.getType().getName(), model);
                updateStart = (String) checkEmptyInput(toModif.getDepart().getNom_port(), startName);
                updateLatPos = (double) checkEmptyInput(toModif.getLatitude(), posLat);
                updateLongPos = (double) checkEmptyInput(toModif.getLongitude(), posLong);
                updateLatStart = (double) checkEmptyInput(toModif.getDepart().getLatitude(), startLat);
                updateLongStart = (double) checkEmptyInput(toModif.getDepart().getLongitude(), startLong);

                Containership temp = new Containership(i, updateName, updateCapn, updateLatPos, updateLongPos);
                ContainershipType tempType = new ContainershipType(iType, updateModel);
                Port tempPort = new Port(iStart, updateStart, updateLatStart, updateLongStart);

                temp.setDepart(tempPort);
                temp.setType(tempType);

                temp.toDB();

                Intent intent = new Intent(ModificationActivity.this, BoatListActivity.class);
                startActivity(intent);
            }



        });
    }


    public String getupdateName() {
        return updateName;
    }

    public void setupdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getupdateCapn() {
        return updateCapn;
    }

    public void setupdateCapn(String updateCapn) {
        this.updateCapn = updateCapn;
    }

    public String getupdateModel() {
        return updateModel;
    }

    public void setupdateModel(String updateModel) {
        this.updateModel = updateModel;
    }

    public double getupdateLatPos() {
        return updateLatPos;
    }

    public void setupdateLatPos(double updateLatPos) {
        this.updateLatPos = updateLatPos;
    }

    public double getupdateLongPos() {
        return updateLongPos;
    }

    public void setupdateLongPos(double updateLongPos) {
        this.updateLongPos = updateLongPos;
    }

    public String getupdateStart() {
        return updateStart;
    }

    public void setupdateStart(String updateStart) {
        this.updateStart = updateStart;
    }

    public double getupdateLatStart() {
        return updateLatStart;
    }

    public void setupdateLatStart(double updateLatStart) {
        this.updateLatStart = updateLatStart;
    }

    public double getupdateLongStart() {
        return updateLongStart;
    }

    public void setupdateLongStart(double updateLongStart) {
        this.updateLongStart = updateLongStart;
    }

    private Object checkEmptyInput(Object o, EditText eT){
        Object value = new Object();
        if (isEmpty(eT)){
            value = o;
        }
        else {
            if (o.getClass() == String.class) {
                value = eT.getText().toString();
            }
            if (o.getClass() == Double.class){
                value = Double.parseDouble(eT.getText().toString());
            }
        }
        return value;
    }

    private boolean isEmpty(EditText eT){
        return eT.getText().toString().trim().length() == 0;
    }
}
