package com.example.boattracker.Classes;

import android.util.Log;
import android.widget.ListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Utils {

    public static void DocsIntoBato(final FirebaseFirestore ff, final ArrayList<Containership> list, final ListView view, final BoatItemAdapter adap){
        ff.collection("Containership").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete( Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    System.out.println("WSH LES DOC DANS LA COLLEC " + task);
                    for (final QueryDocumentSnapshot elBato : task.getResult()){
                        final Containership cont = new Containership(Integer.valueOf(elBato.get("id").toString())
                                ,elBato.getString("boat_name")
                                ,elBato.getString("captain_name")
                                ,Double.valueOf(elBato.get("latitude").toString())
                                ,Double.valueOf(elBato.get("longitude").toString()));
                            DocumentReference elType = ff.document(elBato.getString("Type"));
                        elType.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot type) {
                                try {
                                    System.out.println(type + "SNAPTYPE");
                                    ContainershipType typ = new ContainershipType(Integer.valueOf(type.get("id").toString())
                                            , type.getString("name"));
                                    cont.setType(typ);
                                    System.out.println("WSH LE BATO + TYPE " + cont);
                                }catch (Exception e){
                                    Log.e("GET AND CREATE TYPE", "onSuccess: "+type);
                                }
                            }
                        });
                        DocumentReference elPort = ff.document(elBato.getString("Depart"));
                        elPort.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot port) {
                                try {
                                    Port por = new Port(Integer.valueOf(port.get("id").toString())
                                            ,port.getString("name")
                                            ,Double.valueOf(port.get("latitude").toString())
                                            ,Double.valueOf(port.get("longitude").toString()));

                                    cont.setDepart(por);
                                    System.out.println("WSH BATO ET PORT " + cont);
                                    list.add(cont);
                                    System.out.println("LIST " + list);
                                    view.setAdapter(adap);
                                }catch (Exception e){
                                    Log.e("GET AND CREATE PORT", "onSuccess: " + port);
                                    Log.e("C'EST LA HESS", e.toString());
                                }
                            }
                        });
                    }
                }else {
                    Log.d("BD", "Erreur dans getDocs: ", task.getException());
                }
            }
        });
    }

    public static void genericSendInDB(Object o, String collection, String document){
        FirebaseFirestore ff = FirebaseFirestore.getInstance();
        ff.collection(collection).document(document).set(o);
    }



}
