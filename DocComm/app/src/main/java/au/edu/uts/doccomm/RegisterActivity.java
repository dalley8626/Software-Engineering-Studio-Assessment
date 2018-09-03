package au.edu.uts.doccomm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";

    private Button btnRegister;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvSignIn;
    private EditText etPhoneNumber;
    private EditText etName;
    private TextView tvDateOfBirth;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();




        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),UserActivty.class));
        }

        progressDialog = new ProgressDialog(this);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvSignIn = (TextView) findViewById(R.id.tvSignIn);
        //etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        //etName = (EditText) findViewById(R.id.etName);

        tvDateOfBirth = (TextView) findViewById(R.id.tvDateOfBirth);


        tvDateOfBirth.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);



    }

    /* TODO: Implement if the user has already been signed in
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    */

    private void registerUser(){
        final String emailAddress = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
//        final String name = etName.getText().toString().trim();
//        final String phoneNumber = etPhoneNumber.getText().toString().trim();

        //Validation method that ensures that the user has entered email and password to register
        //When the user did not enter anything to the email field
        //Stop the except and provide an exception error

//        if(TextUtils.isEmpty(name)) {
//            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show();
//            return;
//        }

        if(TextUtils.isEmpty(emailAddress)) {
            Toast.makeText(this,"Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
//
//        if(TextUtils.isEmpty(phoneNumber)) {
//            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show();
//            return;
//        }

        //If the validation is successful, show registration progress
        progressDialog.setMessage("Registering in Process, Please Wait");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(emailAddress,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Sign in success, update UI with the signed-in
                            FirebaseUser user = mAuth.getCurrentUser();
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                            finish();
                            //String userId = mAuth.getCurrentUser().getUid();
//                            var CurrentUser = mDatabase.child("users").child(userId).setValue(userId);

//
//                            HashMap<String, Object> newPost = new HashMap<>();
//                            newPost.put("name", name);
//                            newPost.put("email", emailAddress);
//                            newPost.put("password", password);
//                            newPost.put("phoneNumber", phoneNumber);
//
//                            currentUserDatabase.setValue(newPost);

                            //mDatabase.child("users").child(userId).child("username").setValue(name);

                            startActivity(new Intent(getApplicationContext(), UserActivty.class));

                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this,"Registration Unsuccessful",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        month = month + 1;
        Log.d(TAG, "onDateSet: dd/mm/yyyy " + day + "/" + month + "/" + year);
    }



    @Override
    public void onClick(View view) {
        if (view == btnRegister) {
            registerUser();
        }

        if (view == tvSignIn) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (view == tvDateOfBirth) {

            Calendar cal= Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DATE);

            DatePickerDialog dialog = new DatePickerDialog(
                    RegisterActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year,month,year);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy " + day + "/" + month + "/" + year);
            }
        };

    }




}
