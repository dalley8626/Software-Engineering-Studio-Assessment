package au.edu.uts.doccomm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class EnterDoctorCodeActivity extends AppCompatActivity {


    private String patientID;

    private TextInputEditText codeTIT;

    private ArrayList<String> pairingCodes;

    public ArrayList<String> doctorList;
    public ArrayList<String> doctorIDList;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public void pairDoctor(View view) {
        final String code = codeTIT.getText().toString();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.hasChild("pairingCode")) {
                        Map<String, Object> user = (Map<String, Object>) snapshot.getValue();
                        String pairingCode = (String) user.get("pairingCode");
                        if(pairingCode.equals(code)) {
                            mDatabase.child((String) user.get("userId")).child("patients").child(patientID).setValue(true);
                            mDatabase.child(patientID).child("doctors").child((String) user.get("userId")).setValue(true);
                            Toast.makeText(getApplicationContext(),"Successfully Paired",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                Toast.makeText(getApplicationContext(),"Invalid code",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String doctorString(Map<String, Object> dataPacket) {
        return dataPacket.get("firstName") + " " + dataPacket.get("lastName") + "\n" +
                dataPacket.get("occupation");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_doctor_code);


        doctorList = new ArrayList<>();
        doctorIDList = new ArrayList<>();

        pairingCodes = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        patientID = mAuth.getCurrentUser().getUid();

        codeTIT = findViewById(R.id.codeTIT);

    }
}
