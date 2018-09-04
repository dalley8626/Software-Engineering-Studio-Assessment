package au.edu.uts.doccomm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static au.edu.uts.doccomm.RegisterActivity.mDatabase;




public class DataPacketActivity extends AppCompatActivity {

    public String timeStamp;

    public String name;
    public String gender;

    public String weight;
    public String height;
    public String medicalCondition;

    public String heartRate;

    String  currentDateTimeString;

    TextView nameTv;
    TextView genderTv;
    TextView heightTv;

    EditText weightTv;

    EditText medicalDataEt;

    public static Map<String, String> dataPacket = new HashMap<>();

    public void addToPacket() {
        currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        dataPacket.put("timestamp", currentDateTimeString);

        if(!name.isEmpty())
            dataPacket.put("name", name);

        if(!gender.isEmpty())
            dataPacket.put("gender", gender);

        if(!weight.isEmpty())
            dataPacket.put("weight", weightTv.getText().toString());

        if(!height.isEmpty())
            dataPacket.put("height", height);

        if(!medicalCondition.isEmpty())
            dataPacket.put("medicalCondition", medicalCondition);

        if(!medicalDataEt.getText().toString().isEmpty())
            dataPacket.put("medicalData", medicalDataEt.getText().toString());

    }


    public void sendPacket(View view) {

        addToPacket();

        mDatabase.child(RegisterActivity.id).push().setValue(dataPacket);

        Toast.makeText(DataPacketActivity.this,"Saved and Sent",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), UserActivty.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_packet);

        nameTv = findViewById(R.id.nameTV);
        genderTv = findViewById(R.id.genderTV);
        heightTv = findViewById(R.id.heightTV);

        weightTv = findViewById(R.id.weightET);
        medicalDataEt = findViewById(R.id.medicalDataET);


        name = User.firstName + " " + User.lastName;
        gender = User.gender;
        weight = User.weight;
        height = User.height;
        medicalCondition = User.medicalCondition;

        nameTv.setText(name);
        genderTv.setText(gender);
        heightTv.setText(height);

        weightTv.setText(weight);




    }
}
