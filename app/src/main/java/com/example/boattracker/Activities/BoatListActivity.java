package com.example.boattracker.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.boattracker.Classes.Container;
import com.example.boattracker.Classes.Containership;
import com.example.boattracker.Classes.BoatItemAdapter;
import com.example.boattracker.Classes.ContainershipType;
import com.example.boattracker.Classes.Port;
import com.example.boattracker.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoatListActivity extends AppCompatActivity {

    static String TAG = "BoatListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boat_list_listview);

        final ListView listBoatDisplay = findViewById(R.id.list_boat);

        final ArrayList<Containership> ListeDesBateaux = new ArrayList<>();

        //DocumentReference dr = db.collection("Containership").document("bato");

        Containership bato = new Containership.ContainershipBuilder("McBoatface", "Boaty").addPosition(-31.453988, 153.048861).addPort(new Port("Le Havre",49.486518, 0.090639)).addType(new ContainershipType("paquebot")).build();

        Containership batoo = new Containership.ContainershipBuilder("Bacon", "Chris P.").addPosition(61.902974,-8.050389).addPort(new Port ("Dublin", 53.344926, -6.196133)).addType(new ContainershipType("hydroglisseur")).build();

        Containership batooo = new Containership.ContainershipBuilder("Mark", "Oh hi").addPosition(67.656155, -80.170957).addPort(new Port("Key Biscane", 25.687693, -80.155197)).addType(new ContainershipType("girouette")).build();


        writeAllObjects(batooo);


        ListeDesBateaux.add(bato);
        ListeDesBateaux.add(batoo);
        ListeDesBateaux.add(batooo);



        BoatItemAdapter adapter = new BoatItemAdapter(getApplicationContext(), ListeDesBateaux);
        listBoatDisplay.setAdapter(adapter);

        listBoatDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ListeDesBateaux.get(position).toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(BoatListActivity.this, DescriptionActivity.class);
                intent.putExtra("clicked", ListeDesBateaux.get(position));

                startActivity(intent);
            }
        });
    }

    public void writeInDb(Object o, String collection){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        db.collection(collection).add(o).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void updateFieldInDb(Containership bato, String collection, String document) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection(collection).document(document);
        Map<String, Object> fieldBato = new HashMap<>();
        fieldBato.put("boat_name", bato.getBoat_name());
        fieldBato.put("captain_name", bato.getCaptain_name());
        fieldBato.put("conteneur", bato.getConteneur());
        fieldBato.put("depart", bato.getDepart());
        fieldBato.put("latitude", bato.getLatitude());
        fieldBato.put("longitude", bato.getLongitude());
        fieldBato.put("type", bato.getType());
        ref.update(fieldBato).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully updated!");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });


    }

    public void writeAllObjects(Containership bato){
        writeInDb(bato, "Containership");
        //writeInDb(bato.getConteneur(), "Container");
        writeInDb(bato.getDepart(), "Port");
        writeInDb(bato.getType(), "Type");
    }
}
