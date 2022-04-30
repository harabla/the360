package com.example.the360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class maxPuttsSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_putts_settings);


        //list buttons

        Button mpNewRound = findViewById(R.id.mpNewRound);
        Button mpSaveExit = findViewById(R.id.mpFinishExit);
        Button mpExit = findViewById(R.id.mpExit);

        //What do those buttons do

        //MP new round - should save data to firebase & clear putt count - other settings to stay the same
        mpNewRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"This should save data to firebase & clear putt count - other settings to stay the same",Toast.LENGTH_SHORT).show();
            }
        });


        //MP save exit = should save data to firebase and exit to the front page
        mpSaveExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"This should save data to firebase and exit to the front page",Toast.LENGTH_SHORT).show();
            }
        });

        //mp exit - should simply exit to the front page
        mpExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"This should simply exit to the front page",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(maxPuttsSettings.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}