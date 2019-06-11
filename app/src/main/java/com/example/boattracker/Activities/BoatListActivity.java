package com.example.boattracker.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.boattracker.Classes.Container;
import com.example.boattracker.Classes.Containership;
import com.example.boattracker.Classes.BoatItemAdapter;
import com.example.boattracker.Classes.ContainershipType;
import com.example.boattracker.Classes.Port;
import com.example.boattracker.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class BoatListActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 7;
    private static final int RC_SIGN_OUT = 8;
    static String TAG = "BoatListActivity";


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        //if (requestCode == RC_SIGN_OUT){
        //    Task<GoogleSignInAccount> task = Goo
        //}
    }

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


        //writeAllObjects(bato);

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


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount compte = GoogleSignIn.getLastSignedInAccount(this);

        final SignInButton signin = findViewById(R.id.signIn_Button);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.signIn_Button:
                        signIn();
                        break;
                }
            }
        });

        final Button signout = findViewById(R.id.signOut_Button);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.signOut_Button:
                        signOut();
                        break;
                }
            }
        });



    }



    public void writeInDb(Object o, String collection, String document){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        db.collection(collection).document(document).set(o);
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
        writeInDb(bato, "Containership", bato.getBoat_name());
        //writeInDb(bato.getConteneur(), "Container", bato.getBoat_name());
        writeInDb(bato.getDepart(), "Port", bato.getBoat_name());
        writeInDb(bato.getType(), "Type", bato.getBoat_name());
    }

    public boolean getObjectInDB(final String doc){
        FirebaseFirestore ff = FirebaseFirestore.getInstance();
        final boolean exists = false;
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
        if (getObjectInDB(bato.getBoat_name()))
            writeAllObjects(bato);
        else
            updateFieldInDb(bato, "Containership", bato.getBoat_name());
    }


    //Fonctions pour la connexion



    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        //Intent signouintent =
    }

    private void signOut(){
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast toast = Toast.makeText(getApplicationContext(), "Disconnected", Toast.LENGTH_SHORT);
                toast.show();
                updateUI(null);
            }
        });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account){
        if (account != null){
            SignInButton signin = findViewById(R.id.signIn_Button);
            signin.setVisibility(View.INVISIBLE);
            Button signout = findViewById(R.id.signOut_Button);
            signout.setVisibility(View.VISIBLE);

        }
        else{
            SignInButton signin = findViewById(R.id.signIn_Button);
            signin.setVisibility(View.VISIBLE);
            Button signout = findViewById(R.id.signOut_Button);
            signout.setVisibility(View.INVISIBLE);
        }
    }


}
