package au.edu.uts.doccomm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class newDataPacketsFromDoctor extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ArrayList<String> recentDataPackets;

    String patientID;
    String doctorID;

    ListView recentDataPacketsLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data_packets_from_doctor);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        doctorID = mAuth.getCurrentUser().getUID();

        recentDataPackets = new ArrayList<>();

        recentDataPacketsLV = findViewById(R.id.recentDataPacketsLV);

        mDatabase.addValueEventListenener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.child(doctorID).child("recentDataPackets")) {
                            Map<String, Object> dataPacket = new HashMap<>();
                            dataPacket = (Map<String, Object>) snapshot.getValue();
                            String userID = dataPacket.get("userID");

                            if(snapshot != null) {
                                recentDataPacketsLV.setText("This is an error. Please Fix");
                            }
                            else {

                                break;
                            }
                        }

                    }

                    @Override
                    public void dataCancelled(DatabaseError databaseError) {
                        //This is meant to do nothing.
                    }
                }
        );

    }
}
