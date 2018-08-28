package au.edu.uts.doccomm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class PatientInformationDisplay extends AppCompatActivity {
    int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information_display);

        EditText editText = findViewById(R.id.patientText);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if(noteId != -1) {
            editText.setText(PatientInformationActivity.patientInformation.get(noteId));
        }
        else {
            PatientInformationActivity.patientInformation.add("");
            noteId = PatientInformationActivity.patientInformation.size() - 1;
            PatientInformationActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PatientInformationActivity.patientInformation.set(noteId, s.toString());
                PatientInformationActivity.arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
