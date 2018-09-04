package au.edu.uts.doccomm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import static au.edu.uts.doccomm.RegisterActivity.mDatabase;




public class DataPacketActivity extends AppCompatActivity {


    public String name;
    public String gender;

    public String weight;
    public String height;
    public String medicalCondition;

    public String heartRate;

    TextView nameTv;
    TextView genderTv;
    TextView heightTv;

    EditText weightTv;

    EditText medicalDataEt;

    Map<String, String> dataPacket = new HashMap<>();


    public void sendPacket(View view) {
        dataPacket.put("name", name);
        dataPacket.put("gender", gender);
        dataPacket.put("weight", weightTv.getText().toString());
        dataPacket.put("height", height);
        dataPacket.put("medicalCondition", medicalCondition);
        dataPacket.put("medicalData", medicalDataEt.getText().toString());

        mDatabase.child(RegisterActivity.id).push().setValue(dataPacket);
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
