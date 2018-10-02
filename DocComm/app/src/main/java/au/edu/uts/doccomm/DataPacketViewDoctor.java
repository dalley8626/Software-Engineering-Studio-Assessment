package au.edu.uts.doccomm;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DataPacketViewDoctor extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String doctorID;
    private String patientID;
    private String timeStamp;

    private TextView heartRateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_packet_view_doctor);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        doctorID = getIntent().getStringExtra("doctorID");
        patientID = getIntent().getStringExtra("patientID");
        timeStamp = getIntent().getStringExtra("timeStamp");

        heartRateTV = findViewById(R.id.heartRateTV2);

        DatabaseReference m1 = mDatabase.child(doctorID);
        DatabaseReference m2 = m1.child("dataPacket");
        DatabaseReference m3 = m2.child(patientID);
        m3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> dataPacket;
                String packetTimeStamp = "";
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    dataPacket = (Map<String, Object>) snapshot.getValue();
                    packetTimeStamp = (String) dataPacket.get("timestamp");

                    if(timeStamp.equals(packetTimeStamp)) {
                       heartRateTV.setText((String) dataPacket.get("heartRate"));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
