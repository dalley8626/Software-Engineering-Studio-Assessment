package au.edu.uts.doccomm;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
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

    public String getRecommendation() {

      //  System.out.println(addressTV.getText().toString());
        return addressTV.getText().toString();

    }

    public void viewRecommendation(View view) {
        String viewRecommendationClicked = "YES";
            if (!(addressTV.getText().toString().equals("Your doctor has not sent an address yet!"))) {
                Intent intent2 = new Intent(getApplicationContext(), FacilitiesMapsActivity.class);
                intent2.putExtra("address", addressTV.getText().toString());
                intent2.putExtra("viewRecommendationClicked?", viewRecommendationClicked);
                startActivity(intent2);
            }
            else{
                Toast.makeText(this, "Your doctor has not sent you a recommendation yet!", Toast.LENGTH_LONG).show();
            }
    }
}
