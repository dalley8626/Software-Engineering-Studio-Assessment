package au.edu.uts.doccomm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewPatientProfileFromDoctor extends AppCompatActivity {

    private String patientID;
    TextView nameTV;
    TextView genderTV;
    TextView heightTV;
    TextView weightTV;
    TextView medicalConditionTV;

    public void receivedDataPackets(View view) {
        Intent intent = new Intent(getApplicationContext(), DataPacketListFromDoctor.class);
        intent.putExtra("patientID", patientID);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_profile_from_doctor);

        nameTV = findViewById(R.id.viewNameTV2);
        genderTV = findViewById(R.id.viewGenderTV2);
        heightTV = findViewById(R.id.viewHeightTV2);
        weightTV = findViewById(R.id.viewWeightTV2);
        medicalConditionTV = findViewById(R.id.viewMedicalConditionTV2);

        patientID = getIntent().getStringExtra("patientID");
    }
}
