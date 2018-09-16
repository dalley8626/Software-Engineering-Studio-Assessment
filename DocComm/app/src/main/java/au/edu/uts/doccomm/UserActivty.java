package au.edu.uts.doccomm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class UserActivty extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogout;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private TextView tvUsername;

    public static String id;


    public void heartRate(View view) {
        Intent intent = new Intent(getApplicationContext(), HeartRateMonitor.class);
        startActivity(intent);
    }

    public void viewProfile(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewProfileActivity.class);
        startActivity(intent);
    }

    public void nearbyMedicalFacilities(View view) {

        Intent intent2 = new Intent(getApplicationContext(), FacilitiesMapsActivity.class);
        startActivity(intent2);

    }

    public void addPacket(View view) {
        Intent intent = new Intent(getApplicationContext(), DataPacketActivity.class);
        startActivity(intent);
    }

    public void viewPacket(View view) {
        Intent intent = new Intent(getApplicationContext(), DataPacketList.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activty);

        tvUsername = (TextView) findViewById(R.id.tvUsername);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        //If there is no use logged in, redirect them to the login page
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        FirebaseUser user = mAuth.getCurrentUser();

        //TODO: Change to Username
        //tvUsername = (TextView) findViewById(R.id.etEmail);

        tvUsername.setText("Welcome " + user.getEmail());


        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == btnLogout) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
