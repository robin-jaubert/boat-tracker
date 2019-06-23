package com.example.boattracker.Activities;

import android.content.ComponentName;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.boattracker.Classes.Database;
import com.example.boattracker.Classes.Port;
import com.example.boattracker.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;

public class BoatListActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 7;
    private static final String TAG = "BoatListactivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    final ArrayList<Containership> ListeDesBateaux = new ArrayList<>();
    private Database dbaccess = new Database();
    //private ArrayList<String> idDocuments = dbaccess.getDocumentNameInDb("Containership");

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boat_list_listview);

        final ListView listBoatDisplay = findViewById(R.id.list_boat);



        final BoatItemAdapter adapter = new BoatItemAdapter(getApplicationContext(), ListeDesBateaux);

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

        final Button create = findViewById(R.id.createBoat);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoatListActivity.this, ActivityCreate.class);
                startActivity(intent);
            }
        });


        DocsIntoBoats(db, ListeDesBateaux, listBoatDisplay, adapter);

    }




    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
            Log.w(Database.TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account){
        if (account != null){
            SignInButton signin = findViewById(R.id.signIn_Button);
            signin.setVisibility(View.INVISIBLE);
            Button signout = findViewById(R.id.signOut_Button);
            signout.setVisibility(View.VISIBLE);
            Button create = findViewById(R.id.createBoat);
            create.setVisibility(View.VISIBLE);

        }
        else{
            SignInButton signin = findViewById(R.id.signIn_Button);
            signin.setVisibility(View.VISIBLE);
            Button signout = findViewById(R.id.signOut_Button);
            signout.setVisibility(View.INVISIBLE);
            Button create = findViewById(R.id.createBoat);
            create.setVisibility(View.INVISIBLE);
        }
    }

    public void DocsIntoBoats(final FirebaseFirestore ff, final ArrayList<Containership> listContainerships, final ListView view, final BoatItemAdapter adap){
        ff.collection("Containership").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (final QueryDocumentSnapshot result : task.getResult()){
                    final Containership bato = new Containership.ContainershipBuilder(
                            result.get("boat_name").toString(),result.get("captain_name").toString(),Double.parseDouble(result.get("latitude").toString()), Double.parseDouble(result.get("longitude").toString()))
                            .addId(Integer.parseInt(result.get("id").toString())).build();
                    DocumentReference refType = ff.collection("Containership").document(result.get("type").toString()).collection("Type").document(result.get("type"));
                    refType.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshotType) {
                            try {
                                ContainershipType typeBato = new ContainershipType(documentSnapshotType.get("name").toString());
                                bato.setType(typeBato);
                            }catch (Exception e){
                                Log.e(TAG, "onSuccess createType: " + documentSnapshotType);
                            }

                        }
                    });

                    DocumentReference refPort = ff.document(result.get("depart").toString());
                    refPort.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshotPort) {
                            try {
                                Port port = new Port(documentSnapshotPort.get("nom_port").toString(), Double.parseDouble(documentSnapshotPort.get("latitude").toString()), Double.parseDouble(documentSnapshotPort.get("longitude").toString()));
                                bato.setDepart(port);
                                listContainerships.add(bato);
                                view.setAdapter(adap);
                            }catch (Exception e){
                                Log.e(TAG, "onSuccess createPort: " + documentSnapshotPort );
                            }

                        }
                    });

                    }
                } else
                    Log.d(TAG, "onComplete getDocs: ", task.getException());

            }
        });

    }



}
