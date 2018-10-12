package au.edu.uts.doccomm;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class DataPacketActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    //    private FirebaseStorage firebaseStorage;
    private String id;
    public String url;


    private String doctorID;

    public Map<String, String> dataPacket;

    public String name;
    public String gender;
    public String weight;
    public String height;
    public String medicalCondition;
    public String heartRate;


    public String packetKey;

    private String currentDateTimeString;

    public ArrayList<String> dataPacketText;

    TextView nameTv;
    TextView genderTv;
    TextView heightTv;
    TextView weightTv;
    Button btnSend;
    EditText medicalDataEt;
    Button btnUpload;
    TextView tvUploadUrl;
    public Uri pdfUri; //local storage of url
    Button btnBack;

    String userID;

    TextView heartRateTextView;

    Button heartRateBtn;
    CheckBox understandCB;

    private static final int REQUEST_CODE = 1;
    private String [] permissions = {Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE};
    public void addToPacket() {

        if (!name.isEmpty())
            dataPacket.put("name", name);

        if (!gender.isEmpty())
            dataPacket.put("gender", gender);

        if (!weight.isEmpty())
            dataPacket.put("weight", weightTv.getText().toString());

        if (!height.isEmpty())
            dataPacket.put("height", height);

        if (!medicalCondition.isEmpty())
            dataPacket.put("medicalCondition", medicalCondition);

        if (!medicalDataEt.getText().toString().isEmpty())
            dataPacket.put("medicalData", medicalDataEt.getText().toString());

        if (!heartRateTextView.getText().toString().isEmpty()) {
            dataPacket.put("heartRate", heartRateTextView.getText().toString());
        }

            dataPacket.put("userID", userID);


    }

    public void heartRate(View view) {
            Intent intent = new Intent(getApplicationContext(), HeartRateMonitor.class);
            intent.putExtra("doctorID", doctorID);
            startActivity(intent);
    }

    public void sendPacket(View view) {

        addToPacket();



        String id = mAuth.getCurrentUser().getUid();



        currentDateTimeString = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
        dataPacket.put("timestamp", currentDateTimeString);



        dataPacket.put("url", url);


        DatabaseReference newRef = mDatabase.child(id).child("DataPacket").push();
        newRef.setValue(dataPacket);
        //  mDatabase.child(id).push().setValue(true);
        packetKey = newRef.getKey();

        DatabaseReference doctorRef = mDatabase.child(doctorID).child("dataPacket").child(id).child(packetKey);
        doctorRef.setValue(dataPacket);
        mDatabase.child(doctorID).child("recentDataPackets").child(packetKey).setValue(dataPacket);
        Map<String, Object> isClicked = new HashMap<>();
        isClicked.put("isClicked", "false");
        mDatabase.child(doctorID).child("recentDataPackets").child(packetKey).updateChildren(isClicked);


        Toast.makeText(DataPacketActivity.this, "Saved and Sent", Toast.LENGTH_SHORT).show();

        //savePacket(view);

        Intent intent = new Intent(getApplicationContext(), UserActivty.class);
        startActivity(intent);
    }

    public void uploadFile(Uri pdfUri) {
        String fileName = System.currentTimeMillis() + "";
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        final StorageReference filePath = storageReference.child("files").child(fileName);

        filePath.putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) // THIS SHIT IS THE PROBLEM AS IT REQUIRES THE CODE TO BE SUCCESS FIRST
            {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        url = uri.toString();
                        btnSend.setClickable(true);
                        Toast.makeText(DataPacketActivity.this, "Upload Complete", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                btnSend.setClickable(false);
                Toast.makeText(DataPacketActivity.this, "The Submit button will be available when the file has finished upload", Toast.LENGTH_LONG).show();
            }
        });

    }

//    public void savePacket(View view) {
//        String dataPackets = "Name: " + name + " Gender: " + gender + "\n" +
//                "Height: " + height + " Weight: " + weight + "\n" +
//                "Medical Condition: " + medicalCondition + "\n" +
//                "Addition medical condition: " + medicalDataEt.getText().toString();
//
//        dataPacketText.add(dataPackets);
//
//        Toast.makeText(DataPacketActivity.this, "Saved", Toast.LENGTH_SHORT).show();
//
//        Intent intent = new Intent(getApplicationContext(), UserActivty.class);
//        startActivity(intent);
//    }

    public void boxChecked(View view) {
        if (understandCB.isChecked()) {
            heartRateBtn.setVisibility(View.VISIBLE);
        } else {
            heartRateBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_packet);

        verifyPermission();
        dataPacketText = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        id = mAuth.getCurrentUser().getUid();
        doctorID = getIntent().getStringExtra("doctorID");

        heartRateTextView = findViewById(R.id.heartRateTV);

        dataPacket = new HashMap<>();

        nameTv = findViewById(R.id.nameTV);
        genderTv = findViewById(R.id.genderTV);
        heightTv = findViewById(R.id.heightTV);
        btnSend = findViewById(R.id.sendBtn);

        weightTv = findViewById(R.id.weightTV);
        medicalDataEt = findViewById(R.id.medicalDataET);
        btnUpload = findViewById(R.id.btnUpload);
        btnBack = findViewById(R.id.btnBack);
        tvUploadUrl = findViewById(R.id.tvUploadUrl);


        btnUpload.setOnClickListener(this);
        btnBack.setOnClickListener(this);


        heartRateBtn = findViewById(R.id.button8);
        understandCB = findViewById(R.id.checkBox2);

        int bpm = getIntent().getIntExtra("bpm", 0);
        if (bpm != 0) {
            heartRate = Integer.toString(bpm);
            heartRateTextView.setText(heartRate);
            understandCB.setChecked(true);
        }

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(id).getValue(User.class);

                HashMap<String, String> newMap = new HashMap<>();

                name = user.getFirstName() + " " + user.getLastName();
                gender = user.getGender();
                weight = user.getWeight();
                height = user.getHeight();
                medicalCondition = user.getMedicalCondition();
                userID = user.getUserId();

                nameTv.setText(name);
                genderTv.setText(gender);
                heightTv.setText(height);
                weightTv.setText(weight);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == btnUpload) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                selectPdf();
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
            }
//            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
         //       selectPdf();
//            else {
//                ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
//            }
        }
        if (view == btnBack) {
            finish();
            startActivity(new Intent(this, UserActivty.class));
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            selectPdf();
//        }
//        else {
//            Toast.makeText(this,"Please provide access storage permission", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    public void verifyPermission(){
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED){}
        else{
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }
    }

    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            selectPdf();
//        } else {
//            Toast.makeText(this, "Please provide access storage permission", Toast.LENGTH_SHORT).show();
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPdf();

        }
        else {
            Toast.makeText(this, "Please provide access storage and camera permission", Toast.LENGTH_SHORT).show();
        }
    }


    private void selectPdf() {
        //Select files
        Intent intent = new Intent();
//        intent.setType("application/pdf");
        intent.setType("application/pdf");
//        intent.setType("application/pdf");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT); //to fetch files
        startActivityForResult(intent, 86);
//        startActivityForResult(intent.createChooser(intent,"Select File"), 86);
    }
    


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        super.onActivityResult(requestCode, 86, data);
        //check whether the file has been selected
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();//return the uri of the selected file
            uploadFile(pdfUri);

            Cursor returnCursor =
                    getContentResolver().query(pdfUri, null, null, null, null);

            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String name = returnCursor.getString(nameIndex);

            tvUploadUrl.setText(name);



        } else {
            Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }
}
