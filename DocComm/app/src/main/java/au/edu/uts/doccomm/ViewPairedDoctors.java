package au.edu.uts.doccomm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/***************************************************************************************************
 Allows for patient to view a list of paired doctors in a listview

 Author: Xinjun Tan 12935716
 ***************************************************************************************************/

public class ViewPairedDoctors extends AppCompatActivity {

    private ArrayList<String> listOfDoctors;
    private ArrayList<String> listOfDoctorsID;

    private String patientID;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private ListView doctorListLV;

    private TextView descriptionTV;

    private boolean fromInteractDoctorActivity;
    private boolean fromViewPairedDoctors;

    private String mapToString(Map<String, Object> doctors) {
        String doctor = doctors.get("firstName") + " " + doctors.get("lastName") + "\n" +
                doctors.get("occupation");
        return doctor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paired_doctors);

        listOfDoctors = new ArrayList<>();
        listOfDoctorsID = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        patientID = mAuth.getCurrentUser().getUid();

        doctorListLV = findViewById(R.id.doctorListLV);

        fromInteractDoctorActivity = getIntent().getBooleanExtra("InteractDoctorActivity", false);
        fromViewPairedDoctors = getIntent().getBooleanExtra("viewPairedDoctors", false);

        descriptionTV = findViewById(R.id.textView19);
        if(fromInteractDoctorActivity) {
            descriptionTV.setText("Click to see doctor's feedback");
        }
        else if(fromViewPairedDoctors) {
            descriptionTV.setText("Click to view the profile of a doctor");
        }


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfDoctors);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.child(patientID).child("doctors").getChildren()) {
                    String doctorID = snapshot.getKey();
                    Map<String, Object> doctor = (Map<String, Object>) dataSnapshot.child(doctorID).getValue();
                    listOfDoctorsID.add(doctorID);
                    listOfDoctors.add(mapToString(doctor));
                }
                doctorListLV.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        doctorListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!fromViewPairedDoctors && !fromInteractDoctorActivity) {
                    Intent intent = new Intent(getApplicationContext(), DataPacketActivity.class);
                    intent.putExtra("doctorID", listOfDoctorsID.get(position));

                    startActivity(intent);

                }
                if(!fromInteractDoctorActivity && fromViewPairedDoctors) {
                    Intent intent = new Intent(getApplicationContext(), DoctorProfileViewActivity.class);
                    intent.putExtra("pairedView", true);
                    intent.putExtra("doctorID", listOfDoctorsID.get(position));
                    intent.putExtra("patientID", mAuth.getCurrentUser().getUid());
                    startActivity(intent);
                }
                else if(fromInteractDoctorActivity && !fromViewPairedDoctors){
                    Intent intent = new Intent(getApplicationContext(), InteractDoctorActivity.class);
                    intent.putExtra("doctorID", listOfDoctorsID.get(position));
                    intent.putExtra("patientID", mAuth.getCurrentUser().getUid());
                    startActivity(intent);
                }
            }
        });

        doctorListLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(!fromInteractDoctorActivity) {
                    Intent intent = new Intent(getApplicationContext(), DataPacketActivity.class);
                    intent.putExtra("doctorID", listOfDoctorsID.get(position));

                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });
    }
}
