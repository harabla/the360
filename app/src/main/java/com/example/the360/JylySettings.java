package com.example.the360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class JylySettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jyly_settings);


        //list buttons

        Button jylyNewRound = findViewById(R.id.jylyNewRound);
        Button jylyExit = findViewById(R.id.jylyExit);

        RadioGroup radioGroupLocation = (RadioGroup) findViewById(R.id.puttLocation);
        RadioGroup radioGroupWindSelectWindSpeed = (RadioGroup) findViewById(R.id.windSpeed);
        RadioGroup radioGroupWindSelectFrontBack = (RadioGroup) findViewById(R.id.windDirectionFrontBack);
        RadioGroup radioGroupWindSelectLeftRight = (RadioGroup) findViewById(R.id.windDirectionLeftRight);

        //lets hide the radiogroups we dont need initially

        radioGroupWindSelectFrontBack.setVisibility(View.INVISIBLE);
        radioGroupWindSelectLeftRight.setVisibility(View.INVISIBLE);
        radioGroupWindSelectWindSpeed.setVisibility(View.INVISIBLE);


        //lets give default checked states for radio buttons



        //What do those buttons do

        //MP new round - should save data to firebase & clear putt count - other settings to stay the same
        jylyNewRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"This should save data to firebase & clear putt count - other settings to stay the same",Toast.LENGTH_SHORT).show();
            }
        });



        //mp exit - should simply exit to the front page
        jylyExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"This should simply exit to the front page",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(JylySettings.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //wind only asked if outside
        radioGroupLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                View checked = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(checked);



                switch (index) {
                    case 0: //indoors
                        Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        radioGroupWindSelectWindSpeed.setVisibility(View.INVISIBLE);
                        radioGroupWindSelectFrontBack.setVisibility(View.INVISIBLE);
                        radioGroupWindSelectLeftRight.setVisibility(View.INVISIBLE);

                        break;
                    case 1: // outdoors

                        Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        radioGroupWindSelectWindSpeed.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });

        //wind direction only if wind speed =! none
        radioGroupWindSelectWindSpeed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View checked = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(checked);

                switch (index) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        radioGroupWindSelectFrontBack.setVisibility(View.INVISIBLE);
                        radioGroupWindSelectLeftRight.setVisibility(View.INVISIBLE);

                        break;
                    case 1:
                    case 2:
                    case 3:
                        Toast.makeText(getApplicationContext(), "Selected button number " + index, 500).show();
                        radioGroupWindSelectFrontBack.setVisibility(View.VISIBLE);
                        radioGroupWindSelectLeftRight.setVisibility(View.VISIBLE);

                        break;
                }
            }
        });
    }
}