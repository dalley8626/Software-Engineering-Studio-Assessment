package au.edu.uts.doccomm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class newDataPacketsFromDoctor extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ArrayList<String> recentDataPackets;
    private ArrayList<String> dataPacketIDs;
    private ArrayList<String> patientIDs;
    private ArrayList<String> timestamps;

    String patientID;
    String doctorID;

    ListView recentDataPacketsLV;


    private String mapToString(Map<String, Object> dataPacket) {
            return (String) dataPacket.get("name") + " " + "\n" +
                    dataPacket.get("timestamp");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data_packets_from_doctor);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        doctorID = mAuth.getCurrentUser().getUid();

        recentDataPackets = new ArrayList<>();
        patientIDs = new ArrayList<>();
        dataPacketIDs = new ArrayList<>();
        timestamps = new ArrayList<>();

        recentDataPacketsLV = findViewById(R.id.recentDataPacketsLV);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, recentDataPackets);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.child(doctorID).child("recentDataPackets").getChildren()) {
                    String packetID = snapshot.getKey();
                    Map<String, Object> dataPacket = (Map<String, Object>) snapshot.getValue();
                    recentDataPackets.add(mapToString(dataPacket));
                    dataPacketIDs.add(packetID);
                    patientIDs.add((String) dataPacket.get("userID"));
                    timestamps.add((String) dataPacket.get("timestamp"));
                }

                recentDataPacketsLV.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recentDataPacketsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DataPacketViewDoctor.class);
                intent.putExtra("patientID", patientIDs.get(position));
                intent.putExtra("doctorID", doctorID);
                intent.putExtra("timeStamp", timestamps.get(position));

                startActivity(intent);
            }
        });

    }
}
