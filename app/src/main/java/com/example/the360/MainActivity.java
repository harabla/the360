package com.example.the360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button maxPuttsSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //list buttons

        //Putting
        Button maxPuttsSelect = findViewById(R.id.maxputts);
        Button jylySelect = findViewById(R.id.jyly);

        //Driving
        Button accuracySelect = findViewById(R.id.accuracy);
        Button distanceSelect = findViewById(R.id.distance);
        Button speedSelect = findViewById(R.id.speed);

        //Round stats
        Button itbSelect = findViewById(R.id.inthebag);
        Button roundsummarySelect = findViewById(R.id.roundsum);

        //What happens on button clicks

        // Max putts selected
        maxPuttsSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,maxPuttsSettings.class);
                startActivity(intent);
            }
        });

        jylySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.shortterm,Toast.LENGTH_SHORT).show();
            }
        });

        accuracySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.midterm,Toast.LENGTH_SHORT).show();
            }
        });

        distanceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.midterm,Toast.LENGTH_SHORT).show();
            }
        });

        speedSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.midterm,Toast.LENGTH_SHORT).show();
            }
        });

        itbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.longterm,Toast.LENGTH_SHORT).show();
            }
        });

        roundsummarySelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.longterm,Toast.LENGTH_SHORT).show();
            }
        });


    }
}

// this is a change