package au.edu.uts.doccomm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SupplementaryFilesActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplementary_files);

        File file = new File("/data/data/au.edu.uts.doccomm","supplementaryFiles");
        if(!(file.exists())){
        file.mkdir();}

        findViewById(R.id.createFile).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(SupplementaryFilesActivity.this);
                final View dialogView = LayoutInflater.from(SupplementaryFilesActivity.this).inflate(R.layout.dialog_customize,null);
                inputDialog.setTitle("Create text file");
                inputDialog.setView(dialogView);
                inputDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText1 = (EditText) dialogView.findViewById(R.id.textFileTiltle);
                        EditText editText2 = (EditText) dialogView.findViewById(R.id.textFileContent);
                        String fileName = editText1.getText().toString();
                        String fileContent = editText2.getText().toString();
                        if(!(fileName.equals(""))) {
                            File file = new File("/data/data/au.edu.uts.doccomm/supplementaryFiles", fileName+".txt");
                            try {
                                file.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try{
                        FileWriter fileWriter = new FileWriter("/data/data/au.edu.uts.doccomm/supplementaryFiles/"+fileName+".txt");
                        fileWriter.flush();
                        fileWriter.write(fileContent);
                        fileWriter.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                inputDialog.show();
            }
        });


    }
}
