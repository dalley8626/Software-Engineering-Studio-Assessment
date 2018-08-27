package au.edu.uts.doccomm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivty extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogout;
    private FirebaseAuth mAuth;
    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activty);

        mAuth = FirebaseAuth.getInstance();

        //If there is no use logged in, redirect them to the login page
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        FirebaseUser user = mAuth.getCurrentUser();

        //TODO: Change to Username
        //tvUsername = (TextView) findViewById(R.id.etEmail);
        //tvUsername.setText("Welcome" + user.getEmail());

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
