package au.edu.uts.doccomm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Intent;

public class SendFeedbackActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    private String patientID;
    private String doctorID;

    private EditText sendAddressET;
    private EditText heartRateFeedbackET;
    private EditText filesFeedbackET;

    public void sendAddress(View view) {
        String address = sendAddressET.getText().toString();
        String heartRateFeedback = heartRateFeedbackET.getText().toString();
        String filesFeedback = filesFeedbackET.getText().toString();

        if(address.equals("")) {
            address = "Your doctor has not sent an address yet!";
        }

        if(heartRateFeedback.equals("")) {
            heartRateFeedback = "No feedback";
        }

        if(address.equals("")) {
            filesFeedback = "No feedback";
        }

        DatabaseReference newRef = mDatabase.child(patientID).child("doctors").child(doctorID).child("feedback");
        newRef.child("address").setValue(address);
        newRef.child("heartRateFeedback").setValue(heartRateFeedback);
        newRef.child("files").setValue(filesFeedback);
        Toast.makeText(getApplicationContext(), "Feedback sent", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), DoctorActivity.class);
        startActivity(intent);
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
        heartRateFeedbackET = findViewById(R.id.heartRateFeedbackET);
        filesFeedbackET = findViewById(R.id.filesFeedbackET);
    }

}
