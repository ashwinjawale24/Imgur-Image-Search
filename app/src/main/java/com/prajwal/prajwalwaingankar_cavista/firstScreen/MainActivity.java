package com.prajwal.prajwalwaingankar_cavista.firstScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.prajwal.prajwalwaingankar_cavista.R;
import com.prajwal.prajwalwaingankar_cavista.secondScreen.KotlinSecondScreen;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        adapter = new ImageAdapter(this);
        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), KotlinSecondScreen.class);
                i.putExtra("id", position);
                startActivity(i);
            }
        });
    }
}