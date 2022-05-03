package com.example.the360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button maxPuttsSelect;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseDatabase = FirebaseDatabase.getInstance("https://the360-70adc-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();
        getUserProfile();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
        }


        //list buttons
        TextView mainHeader, puttsMade;

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


    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();

                Map<String, Object> totalsData = (Map<String, Object>) snapshot.child("totals").child(uid).getValue();
                String putts = totalsData.get("total putts").toString();

                TextView welcomeText = (TextView) findViewById(R.id.puttsMade);
                welcomeText.setText("Putts made "+ putts);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void getUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name, email;
            email = user.getEmail();
            name = user.getDisplayName();

            //TextView welcomeText = (TextView) findViewById(R.id.mainHeader);
            //welcomeText.setText("Welcome "+ email + name);

            //String uid = user.getUid();

            Button login = (Button) findViewById(R.id.Login);
            Button register = (Button) findViewById(R.id.Register);
            login.setVisibility(View.INVISIBLE);
            register.setVisibility(View.INVISIBLE);

            getData();


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