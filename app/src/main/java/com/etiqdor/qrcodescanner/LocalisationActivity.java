package com.etiqdor.qrcodescanner;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.etiqdor.qrcodescanner.sqlite.SQLiteUtil;

import java.util.List;

/**
 * @author etiqdor
 */
public class LocalisationActivity extends AppCompatActivity {

    private ListView listLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localisation);
        listLocation = findViewById(R.id.list_location);

        // Remplissage de la ListView
        final List<String> location_list = SQLiteUtil.getLocation(MainActivity.helper);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, location_list);
        listLocation.setAdapter(arrayAdapter);
    }
}