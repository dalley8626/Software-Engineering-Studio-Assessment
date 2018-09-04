package au.edu.uts.doccomm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import org.w3c.dom.Text;

public class UserProfileActivity extends AppCompatActivity {

    RadioButton maleRB;
    RadioButton femaleRB;
    EditText fullNameET;
    EditText heightET;
    EditText weightET;
    EditText desciptionET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        maleRB = findViewById(R.id.maleRB);
        femaleRB = findViewById(R.id.femaleRB);
        fullNameET = findViewById(R.id.nameText);
        heightET = findViewById(R.id.heightText);
        weightET = findViewById(R.id.weightText);
        desciptionET = findViewById(R.id.descriptionText);

    }
}
