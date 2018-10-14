package au.edu.uts.doccomm;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ChangePairingCodeActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    String doctorID;
    String patientID;

    TextInputEditText pairingCodeET;

    public void changePairingCode(View view) {
        String newPairingCode = pairingCodeET.getText().toString();

        Map<String, Object> newPairingCodeMap = new HashMap<>();
        newPairingCodeMap.put("pairingCode", newPairingCode);

        mDatabase.updateChildren(newPairingCodeMap);

        Intent intent = new Intent(getApplicationContext(), DoctorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pairing_code);

        mAuth = FirebaseAuth.getInstance();
        doctorID = mAuth.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(doctorID);

        pairingCodeET = findViewById(R.id.pairingCodeET);
    }
}
