package com.example.the360;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button maxPuttsSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUserProfile();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Toast.makeText(getApplicationContext(),uid,Toast.LENGTH_SHORT).show();


        //list buttons
        TextView mainHeader;

        Button login, register, logout;
        login = findViewById(R.id.Login);
        register = findViewById(R.id.Register);
        logout = findViewById(R.id.logout);

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
                Intent intent = new Intent(MainActivity.this,JylySettings.class);
                startActivity(intent);
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.shortterm,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,login.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),R.string.shortterm,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();

            }
        });




    }

    public void getUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name, email;
            email = user.getEmail();
            name = user.getDisplayName();

            TextView welcomeText = (TextView) findViewById(R.id.mainHeader);
            welcomeText.setText("Welcome "+ email + name);

            //String uid = user.getUid();

            Button login = (Button) findViewById(R.id.Login);
            Button register = (Button) findViewById(R.id.Register);
            login.setVisibility(View.INVISIBLE);
            register.setVisibility(View.INVISIBLE);


        } else {
            Toast.makeText(this, "Why you not logged in brodda?", Toast.LENGTH_SHORT).show();
            Button logout = (Button) findViewById(R.id.logout);
            logout.setVisibility(View.INVISIBLE);
        }
    }

    public void signOut() {
        // [START auth_sign_out]
        FirebaseAuth.getInstance().signOut();
        // [END auth_sign_out]
        Button login = (Button) findViewById(R.id.Login);
        Button register = (Button) findViewById(R.id.Register);
        Button logout = (Button) findViewById(R.id.logout);
        login.setVisibility(View.VISIBLE);
        register.setVisibility(View.VISIBLE);
        logout.setVisibility(View.INVISIBLE);

    }



}

// this is a change