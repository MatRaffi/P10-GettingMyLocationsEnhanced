package sg.edu.rp.c346.s19024292.p10_gettingmylocationsenhanced;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ListLocation extends AppCompatActivity {
    Button btnRefresh;
    TextView tvCount;
    ListView lvLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_location);

        tvCount = findViewById(R.id.tvRecords);
        lvLoc = findViewById(R.id.lvLocations);
        btnRefresh = findViewById(R.id.btnRefresh);


        String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
        File targetFile = new File(folderLocation_I, "location.txt");
        if (targetFile.exists()){
            StringBuilder data = new StringBuilder();
            try {
                FileReader reader = new FileReader(targetFile); BufferedReader br = new BufferedReader(reader);
                String line = br.readLine(); while (line != null){
                    data.append(line).append("\n");
                    line = br.readLine(); }

                String [] array = data.toString().split("\n");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, array);
                lvLoc.setAdapter(adapter);

                tvCount.setText("Num of Records: " + array.length);
                br.close();
                reader.close();
            } catch (Exception e) {

                Toast.makeText(ListLocation.this, "Failed to read!", Toast.LENGTH_LONG).show();
                e.printStackTrace(); }
            Log.d("Content", data.toString()); }

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
                File targetFile = new File(folderLocation_I, "location.txt");
                if (targetFile.exists()){
                    String data ="";
                    try {
                        FileReader reader = new FileReader(targetFile); BufferedReader br = new BufferedReader(reader);
                        String line = br.readLine(); while (line != null){
                            data += line + "\n";
                            line = br.readLine(); }

                        String [] array = data.split("\n");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, android.R.id.text1, array);
                        lvLoc.setAdapter(adapter);

                        tvCount.setText("Num of Records: " + array.length);
                        br.close();
                        reader.close();
                    } catch (Exception e) {

                        Toast.makeText(ListLocation.this, "Failed to read!", Toast.LENGTH_LONG).show();
                        e.printStackTrace(); }
                    Log.d("Content", data); }
            }
        });
    }
}