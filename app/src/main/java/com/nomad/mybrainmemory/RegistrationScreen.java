package com.nomad.mybrainmemory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nomad.mybrainmemory.util.StaticConstants;
import com.nomad.mybrainmemory.util.ValidatorUtils;

import java.util.HashMap;
import java.util.Map;

public class RegistrationScreen extends AppCompatActivity {
    Button signupButton;
    TextView loginText;

    TextInputEditText username,email,password,confirmPassword;

    ProgressBar progressBar;

    String userType = StaticConstants.VAL_USER_TYPE_USER;


    private Spinner spinner;
    private String[] items = {"Please select a user type","User", "Admin"};

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        mAuth = FirebaseAuth.getInstance();

        loginText = findViewById(R.id.login_text);
        signupButton = findViewById(R.id.register_button);

        username = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_pass);
        confirmPassword = findViewById(R.id.user_confirm_pass);

        progressBar = findViewById(R.id.progressBar);

        spinner = findViewById(R.id.spinner);

        progressBar.setVisibility(View.INVISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set a listener for spinner item selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Check if the hint is selected and show a toast message
                if (position == 0) {
                    Toast.makeText(RegistrationScreen.this, "Please select a user type", Toast.LENGTH_SHORT).show();
                }else if(position == 1){
                    userType = StaticConstants.VAL_USER_TYPE_USER;
                }else if(position == 2){
                    userType = StaticConstants.VAL_USER_TYPE_ADMIN;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationScreen.this, LoginScreen.class);
                startActivity(i);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent i = new Intent(WelcomeScreen.this, HighScoreScreen.class);
//                startActivity(i);
//                if(!username.getText().toString().isEmpty() && ValidatorUtils.isEmailValid(email.getText().toString()) )

                progressBar.setVisibility(View.VISIBLE);

                if(username.getText().length() < 1){
                    progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(RegistrationScreen.this, StaticConstants.WARN_INVALID_USER_NAME, Toast.LENGTH_LONG).show();

                }else if(!ValidatorUtils.isEmailValid(email.getText().toString())){
                    progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(RegistrationScreen.this, StaticConstants.WARN_INVALID_EMAIL, Toast.LENGTH_LONG).show();

                }else if(!ValidatorUtils.isPasswordValid(password.getText().toString())){
                    progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(RegistrationScreen.this, StaticConstants.WARN_INVALID_PASS, Toast.LENGTH_LONG).show();

                }else if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                    progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(RegistrationScreen.this, StaticConstants.WARN_INVALID_MATCH_PASS, Toast.LENGTH_LONG).show();

                }else {

                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // User sign-up successful
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    // Do something with the user object
                                    Toast.makeText(RegistrationScreen.this, "Sign-up successful! updating user name.", Toast.LENGTH_SHORT).show();

                                    String displayName = username.getText().toString(); // Replace with the desired display name
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(displayName)
                                            .build();

                                    currentUser.updateProfile(profileUpdates)
                                            .addOnCompleteListener(task2 -> {
                                                if (task2.isSuccessful()) {
                                                    // Display name updated successfully
//                                                    progressBar.setVisibility(View.INVISIBLE);
//                                                    onBackPressed();
                                                    // add user type
                                                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                                                    // Replace "users" with the collection name where user data is stored
                                                    DocumentReference userRef = db.collection("users").document(currentUser.getUid());

                                                    // Create a HashMap to store user data, including the user type
                                                    Map<String, Object> userData = new HashMap<>();
                                                    userData.put("userType", userType);

                                                    // Set the user data in Firestore
                                                    userRef.set(userData)
                                                            .addOnSuccessListener(aVoid -> {
                                                                // User type set successfully in Firestore
                                                                Log.d("TAG", "User type set successfully.");
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                                onBackPressed();
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                // Failed to set user type in Firestore, handle the error
                                                                Log.e("TAG", "Error setting user type: " + e.getMessage());
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                                Toast.makeText(RegistrationScreen.this, "User type update failed.", Toast.LENGTH_SHORT).show();
                                                            });
                                                } else {
                                                    // Failed to update display name
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(RegistrationScreen.this, "Username update failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    // User sign-up failed
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(RegistrationScreen.this, "Sign-up failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

//    private void setUserType(String userId, String userType) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        // Replace "users" with the collection name where user data is stored
//        DocumentReference userRef = db.collection("users").document(userId);
//
//        // Create a HashMap to store user data, including the user type
//        Map<String, Object> userData = new HashMap<>();
//        userData.put("userType", userType);
//
//        // Set the user data in Firestore
//        userRef.set(userData)
//                .addOnSuccessListener(aVoid -> {
//                    // User type set successfully in Firestore
//                    Log.d("TAG", "User type set successfully.");
//                })
//                .addOnFailureListener(e -> {
//                    // Failed to set user type in Firestore, handle the error
//                    Log.e("TAG", "Error setting user type: " + e.getMessage());
//                });
//    }

}
