package com.etiqdor.qrcodescanner;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * @author etiqdor
 */
public class NumActivity extends AppCompatActivity {

//    private ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num);

        ListView list = findViewById(R.id.list_num);

        // Enregistrement du nom et du numéro dans une liste
        ArrayList<String> nums = new ArrayList<>();
        for (TelephoneNum num : TelephoneNum.getAllNum())
            nums.add( (num.getName() + " : " + num.getNum()) );

        // Remplissage de la ListView
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nums);
        list.setAdapter(arrayAdapter);

        // Lorsqu'on clique sur un élement, il est enregistré comme numéro actuel
        list.setOnItemClickListener((parent, view, position, id) -> TelephoneNum.currentNum = TelephoneNum.values()[position]);
    }
}