package com.etiqdor.qrcodescanner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NumActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num);

        list = findViewById(R.id.list_num);

        ArrayList<String> nums = new ArrayList<>();

        for (TelephoneNum num : TelephoneNum.getAllNum()){
            nums.add( (num.getName() + " : " + num.getNum()) );
        }

        final List<String> num_list = nums;
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, num_list);

        list.setAdapter(arrayAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TelephoneNum.currentNum = TelephoneNum.values()[position];
            }
        });
    }
}
