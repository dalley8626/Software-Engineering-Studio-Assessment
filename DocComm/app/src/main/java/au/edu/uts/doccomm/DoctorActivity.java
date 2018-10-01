package au.edu.uts.doccomm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DoctorActivity extends AppCompatActivity {

    public void viewPatients(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewPatientsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
    }
}
