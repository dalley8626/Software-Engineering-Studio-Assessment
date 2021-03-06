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
import java.util.HashMap;
import java.util.Map;

/***************************************************************************************************
Allows doctors to view all received data packet from paired patients
 Author: Xinjun Tan 12935716
 ***************************************************************************************************/

public class DataPacketListFromDoctor extends AppCompatActivity {


    private String patientID;
    private String doctorID;

    private ArrayList<String> dataPacketList;
    private ArrayList<String> dataPacketUserID;
    private ArrayList<String> dataPacketKey;
    private ArrayList<String> timeStamps;

    private ListView packetLV;

    private String mapToString(Map<String, Object> dataPacket) {
        return (String) dataPacket.get("timestamp");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_packet_list_from_doctor);

        FirebaseAuth mAuth;
        DatabaseReference mDatabase;

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        patientID = getIntent().getStringExtra("patientID");
        doctorID = mAuth.getCurrentUser().getUid();

        dataPacketList = new ArrayList<>();
        dataPacketUserID = new ArrayList<>();
        timeStamps = new ArrayList<>();

        packetLV = findViewById(R.id.packetListView2);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataPacketList);


        mDatabase.child(patientID).child("DataPacket").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> dataPacket = (Map<String, Object>) snapshot.getValue();
                    dataPacketList.add(mapToString(dataPacket));
                    timeStamps.add((String) dataPacket.get("timestamp"));
                }
                packetLV.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        packetLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DataPacketViewDoctor.class);
                intent.putExtra("patientID", patientID);
                intent.putExtra("doctorID", doctorID);
                intent.putExtra("timeStamp", timeStamps.get(position));

                startActivity(intent);
            }
        });

        packetLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }
}
