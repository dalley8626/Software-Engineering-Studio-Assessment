package au.edu.uts.doccomm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendFeedbackActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    private String patientID;
    private String doctorID;

    private EditText sendAddressET;

    public void sendAddress(View view) {
        String address = sendAddressET.getText().toString();
        mDatabase.child(patientID).child("doctors").child(doctorID).child("feedback").child("address").setValue(address);
        Toast.makeText(getApplicationContext(),"Address is sent", Toast.LENGTH_SHORT).show();
        sendAddressET.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        patientID = getIntent().getStringExtra("patientID");
        doctorID = getIntent().getStringExtra("doctorID");

        sendAddressET = findViewById(R.id.sendLocationET);

    }

}
