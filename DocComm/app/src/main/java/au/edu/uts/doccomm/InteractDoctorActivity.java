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

public class InteractDoctorActivity extends AppCompatActivity {


    TextView addressTV;

    private String patientID;
    private String doctorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact_doctor);

        addressTV = findViewById(R.id.addressTV);

        patientID = getIntent().getStringExtra("patientID");
        doctorID = getIntent().getStringExtra("doctorID");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String patientID = mAuth.getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("users").child(patientID)
                .child("doctors").child(doctorID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("feedback")) {
                            Map<String, Object> addresses = (Map<String, Object>) dataSnapshot.child("feedback").getValue();
                            String address = (String) addresses.get("address");
                            addressTV.setText(address);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }
}
