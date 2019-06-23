package com.example.boattracker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.boattracker.Classes.Containership;
import com.example.boattracker.Classes.BoatItemAdapter;
import com.example.boattracker.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import static com.example.boattracker.Classes.Utils.DocsIntoBato;


public class BoatListActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 7;
    private static final String TAG = "BoatListactivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


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
        final ArrayList<Containership> ListeDesBateaux = new ArrayList<>();




        final BoatItemAdapter adapter = new BoatItemAdapter(getApplicationContext(), ListeDesBateaux);


        DocsIntoBato(db, ListeDesBateaux, listBoatDisplay, adapter);

        listBoatDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Bateau : " + ListeDesBateaux.get(position).getBoat_name() + "\n Capitaine : " + ListeDesBateaux.get(position).getCaptain_name(), Toast.LENGTH_SHORT).show();

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



    }


    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut(){
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
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





}
