package au.edu.uts.doccomm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DataPacketList extends AppCompatActivity {

    ListView packetLV;

    public static ArrayList<String> dataPacketText = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_packet_list);

        packetLV = findViewById(R.id.dataPacketLV);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataPacketText);
        packetLV.setAdapter(arrayAdapter);


    }
}
