package au.edu.uts.doccomm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PatientInformationActivity extends AppCompatActivity {

    //ArrayList to store the information that the patient want to give to doctor
    static ArrayList<String> patientInformation = new ArrayList<>();

    static ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.add_note) {
            Intent intent = new Intent(getApplicationContext(), PatientInformationDisplay.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information);

        ////////////////////////////////////////////////////////////////////////

        //Firebase stuff. Absolute basics right now. Still learning
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        Map<String, String> values = new HashMap<>();
        values.put("name", "bob");
        //dbref.push().setValue(values);

        ////////////////////////////////////////////////////////////////////////

        ListView listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, patientInformation);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), PatientInformationDisplay.class);
                intent.putExtra("noteId", i);
                startActivity(intent);
            }
        });
    }
}
