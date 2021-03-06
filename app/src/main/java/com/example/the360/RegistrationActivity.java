package com.example.the360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView, pdganumber, firstname;
    private Button submit;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        databaseReference = FirebaseDatabase.getInstance("https://the360-70adc-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        mAuth = FirebaseAuth.getInstance();

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        pdganumber = findViewById(R.id.pdgaNumber);
        firstname = findViewById(R.id.firstName);
        submit = findViewById(R.id.btnregister);
        progressBar = findViewById(R.id.progressbar);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });


    }

    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);

        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = user.getUid();
                            int baseValue = 0;
                            databaseReference.child("totals").child(uid).child("total putts").setValue(ServerValue.increment(baseValue));


                            String strEmail = email.toString();
                            String strPDGAnumber = pdganumber.getText().toString();
                            String strFirstname = firstname.getText().toString();

                            createBaseUserData(strEmail,strPDGAnumber,strFirstname);
                            createBaseRecordData(uid);

                            // hide the progress bar
                            progressBar.setVisibility(View.GONE);

                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(RegistrationActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                        } else {
                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

    }

    public void createBaseUserData(String email, String pdgaNumber, String firstName){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Long time = System.currentTimeMillis()/1000;
        String ts = time.toString();



        /**String pdganumberText = findViewById(R.id.pdgaNumber).toString();
        String firstnameText = findViewById(R.id.firstName).toString();
        String email = findViewById(R.id.email).toString();*/

        HashMap<String , Object> map = new HashMap<>();
        map.put("PDGA number", pdgaNumber);
        map.put("First name", firstName);
        map.put("Email", email);
        map.put("Last time played", ts);

        databaseReference.child("user Data").child(uid).updateChildren(map);
    }

    public void createBaseRecordData(String uid){


        int mpRecord4m = 0;
        int mpRecord5m = 0;
        int mpRecord6m = 0;
        int mpRecord7m = 0;
        int mpRecord8m = 0;
        int mpRecord9m = 0;
        int mpRecord10m = 0;

        HashMap<String , Object> map = new HashMap<>();
        map.put("4m", mpRecord4m);
        map.put("5m", mpRecord5m);
        map.put("6m", mpRecord6m);
        map.put("7m", mpRecord7m);
        map.put("8m", mpRecord8m);
        map.put("9m", mpRecord9m);
        map.put("10m", mpRecord10m);



        databaseReference.child("Records").child(uid).updateChildren(map);
    }

}


