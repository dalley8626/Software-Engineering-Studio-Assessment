package au.edu.uts.doccomm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class DataPacketActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String id;

    public static Map<String, String> dataPacket;

    public String name;
    public String gender;
    public String weight;
    public String height;
    public String medicalCondition;
    public String heartRate;

    public String packetKey;

    private String currentDateTimeString;

    public  ArrayList<String> dataPacketText;

    TextView nameTv;
    TextView genderTv;
    TextView heightTv;
    TextView weightTv;
    EditText medicalDataEt;

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
    }

    public void sendPacket(View view) {

        addToPacket();

        String id = mAuth.getCurrentUser().getUid();

        currentDateTimeString = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
        dataPacket.put("timestamp", currentDateTimeString);

        DatabaseReference newRef = mDatabase.child(id).child("DataPacket").push();
        newRef.setValue(dataPacket);
        packetKey = newRef.getKey();



        Toast.makeText(DataPacketActivity.this, "Saved and Sent", Toast.LENGTH_SHORT).show();

        //savePacket(view);

        Intent intent = new Intent(getApplicationContext(), UserActivty.class);
        startActivity(intent);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_packet);

        dataPacketText = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        id = mAuth.getCurrentUser().getUid();

        dataPacket = new HashMap<>();

        nameTv = findViewById(R.id.nameTV);
        genderTv = findViewById(R.id.genderTV);
        heightTv = findViewById(R.id.heightTV);

        weightTv = findViewById(R.id.weightTV);
        medicalDataEt = findViewById(R.id.medicalDataET);

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

                nameTv.setText(name);
                genderTv.setText(gender);
                heightTv.setText(height + "cm");
                weightTv.setText(weight + "kg");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
