package au.edu.uts.doccomm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/***************************************************************************************************
 Connects button from doctor main screen to functions
 Updates a textfield to notify the doctor that a data packet was sent

 Author: Xinjun Tan 12935716
 ***************************************************************************************************/

public class DoctorActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private TextView dataPacketNotificationTV;

    public void changePairingCode(View view) {
        Intent intent = new Intent(getApplicationContext(), ChangePairingCodeActivity.class);
        startActivity(intent);
    }

    public void newDataPackets(View view) {
        Intent intent = new Intent(getApplicationContext(), newDataPacketsFromDoctor.class);
        startActivity(intent);
    }

    public void logout(View view) {
        mAuth.signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void viewPatients(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewPatientsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        dataPacketNotificationTV = findViewById(R.id.dataPacketNotification);

        mAuth = FirebaseAuth.getInstance();

        String doctorID = mAuth.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(doctorID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("recentDataPackets")) {
                    dataPacketNotificationTV.setText("You have a new data packet");
                }
                else {
                    dataPacketNotificationTV.setText("You have no new data packet");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}