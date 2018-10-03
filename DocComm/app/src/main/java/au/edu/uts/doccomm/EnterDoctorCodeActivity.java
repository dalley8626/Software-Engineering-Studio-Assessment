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

public class EnterDoctorCodeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String i;


    public ArrayList<String> doctorList;
    public ArrayList<String> doctorIDList;

    public String doctorString(Map<String, Object> dataPacket) {
        String doctorList = dataPacket.get("firstName") + " " + dataPacket.get("lastName") + "\n" +
                dataPacket.get("occupation");

        return doctorList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_hire);


        doctorListView = findViewById(R.id.doctorLV);
        doctorList = new ArrayList<>();
        doctorIDList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        id = mAuth.getCurrentUser().getUid();


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> user;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    user = (Map<String, Object>) snapshot.getValue();
                    String userType = (String) user.get("userType");
                    if (userType.equals("doctor")) {
                        doctorList.add(doctorString(user));
                        doctorIDList.add((String) user.get("userId"));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
