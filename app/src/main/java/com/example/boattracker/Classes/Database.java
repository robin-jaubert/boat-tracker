package com.example.boattracker.Classes;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database implements Serializable {

    private static boolean exists = false;
    public static final String TAG = "BoatListActivity";


    public Database() {}


    private void writeInDb(Object o, String collection, String document){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collection).document(document).set(o);
    }

    private void updateFieldInDb(Containership bato, String collection, String document) {
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
        writeInDb(bato, "Containership", bato.getBoat_name());
        writeInDb(bato.getDepart(), "Port", bato.getDepart().getNom_port());
        writeInDb(bato.getType(), "Type", bato.getType().getName());
    }

    private boolean getObjectInDB(final String doc){
        FirebaseFirestore ff = FirebaseFirestore.getInstance();
        ff.collection("Containership").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot ds : task.getResult().getDocuments()){
                        if (doc.equals(ds.getId()))
                        {
                            setExists(exists);
                            return;
                        }
                    }
                }
            }
        });
        return exists;
    }

    private void setExists(boolean exists){
        exists = !exists;
    }

    public void controllerWritingBD(Containership bato){
        getObjectInDB(bato.getBoat_name());
        if (!exists){
            writeAllObjects(bato);
        }
        else{
            updateFieldInDb(bato, "Containership", bato.getBoat_name());
        }
    }


    public List<Containership> GetListBoatInDB(String bato){
        FirebaseFirestore ff = FirebaseFirestore.getInstance();
        CollectionReference coll = ff.collection("Containership");
        List<Containership> listBoatDb = new ArrayList<>();
        if (getObjectInDB(bato)){

            return listBoatDb;
        }
        else return null;
    }
}
