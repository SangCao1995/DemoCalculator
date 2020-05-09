package com.example.democalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RecentCalculationActivity extends AppCompatActivity {
    ArrayList<String> recentCalculationList;
    ListView listView;
    ArrayAdapter<String> recentCalculationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_calculation);

        listView = findViewById(R.id.listView);

        recentCalculationList = new ArrayList<>();

        Intent intent = getIntent();
        recentCalculationList = intent.getExtras().getStringArrayList("recentCalculationList");

        recentCalculationAdapter = new ArrayAdapter<>(RecentCalculationActivity.this, android.R.layout.simple_list_item_1, recentCalculationList);
        listView.setAdapter(recentCalculationAdapter);
    }
}
