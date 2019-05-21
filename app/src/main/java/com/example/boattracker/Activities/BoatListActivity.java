package com.example.boattracker.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BoatListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boat_list_listview);

        final ListView listBoatDisplay = findViewById(R.id.list_boat);

        final ArrayList<Containership> ListeDesBateaux = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Containership bato = new Containership.ContainershipBuilder("McBoatface", "Boaty").addPosition(-31.453988, 153.048861).addPort(new Port("Le Havre",49.486518, 0.090639)).addType(new ContainershipType("paquebot")).build();
        db.collection("bato").document().set();
        Containership batoo = new Containership.ContainershipBuilder("Bacon", "Chris P.").addPosition(61.902974,-8.050389).addPort(new Port ("Dublin", 53.344926, -6.196133)).addType(new ContainershipType("hydroglisseur")).build();

        Containership batooo = new Containership.ContainershipBuilder("Mark", "Oh hi").addPosition(67.656155, -80.170957).addPort(new Port("Key Biscane", 25.687693, -80.155197)).addType(new ContainershipType("girouette")).build();

        
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
}
