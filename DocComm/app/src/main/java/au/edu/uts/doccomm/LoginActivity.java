package au.edu.uts.doccomm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignIn;
    private EditText etEmailAddress;
    private EditText etPassword;
    private TextView tvSignUp;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        //If the user is already logged in
        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),UserActivty.class));
        }

        progressDialog = new ProgressDialog(this);

        etEmailAddress = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);

        btnSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    private void userLogin(){
        String emailAddress = etEmailAddress.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        //Validation method that ensures that the user has entered email and password to register
        //When the user did not enter anything to the email field
        //Stop the except and provide an exception error
        if(TextUtils.isEmpty(emailAddress)) {
            Toast.makeText(this,"Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        //If the validation is successful, show login progress
        progressDialog.setMessage("Registering in Process, Please Wait");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(emailAddress,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isCanceled()) {
                            Toast.makeText(getBaseContext(),"Something went wrong in the system", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(!task.isSuccessful()) {
                            Toast.makeText(getBaseContext(),"Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),UserActivty.class));
                        }


                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == btnSignIn) {
            userLogin();
        }

        if(view == tvSignUp) {
            finish();
            startActivity(new Intent(this,RegisterActivity.class));
        }
    }
}
