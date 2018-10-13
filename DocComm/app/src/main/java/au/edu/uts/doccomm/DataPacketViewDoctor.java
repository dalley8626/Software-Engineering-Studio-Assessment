package au.edu.uts.doccomm;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class DataPacketViewDoctor extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private FirebaseStorage mFirebaseStorage;



    private String doctorID;
    private String patientID;
    private String timeStamp;

    private String url;
    private TextView heartRateTV;
    private LinearLayout llUploadName;
    private TextView tvUploadName;

    public void sendFeedback(View view) {
        Intent intent = new Intent(getApplicationContext(), SendFeedbackActivity.class);
        intent.putExtra("patientID", patientID);
        intent.putExtra("doctorID", doctorID);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_packet_view_doctor);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        doctorID = getIntent().getStringExtra("doctorID");
        patientID = getIntent().getStringExtra("patientID");
        timeStamp = getIntent().getStringExtra("timeStamp");


        heartRateTV = findViewById(R.id.heartRateTV2);


        tvUploadName = findViewById(R.id.tvUploadName);
        llUploadName = findViewById(R.id.lluploadName);

        boolean isClicked = getIntent().getBooleanExtra("isClicked", false);
        String dataPacketID = getIntent().getStringExtra("packetID");
        if(isClicked) {
            Map<String, Object> updatedObject = new HashMap<>();
            updatedObject.put("isClicked", "true");
            mDatabase.child(doctorID).child("recentDataPackets").child(dataPacketID).updateChildren(updatedObject);
        }

        DatabaseReference m1 = mDatabase.child(doctorID);
        DatabaseReference m2 = m1.child("dataPacket");
        DatabaseReference m3 = m2.child(patientID);
        m3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> dataPacket;
                String packetTimeStamp = "";
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    dataPacket = (Map<String, Object>) snapshot.getValue();
                    packetTimeStamp = (String) dataPacket.get("timestamp");
                    url = (String) dataPacket.get("url");

                    if(timeStamp.equals(packetTimeStamp)) {
                       heartRateTV.setText((String) dataPacket.get("heartRate"));
                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDownloadFile() {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference httpReference = storage.getReferenceFromUrl(url);

        mStorageRef.child("files").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url = uri.toString();
                tvUploadName.setText(url);
            }
        });


    }
}
