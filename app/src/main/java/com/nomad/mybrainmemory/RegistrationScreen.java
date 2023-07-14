package com.nomad.mybrainmemory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.nomad.mybrainmemory.util.StaticConstants;
import com.nomad.mybrainmemory.util.ValidatorUtils;

public class RegistrationScreen extends AppCompatActivity {
    Button signupButton;
    TextView loginText;

    TextInputEditText username,email,password,confirmPassword;

    ProgressBar progressBar;



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

        progressBar.setVisibility(View.INVISIBLE);


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
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    onBackPressed();
                                                } else {
                                                    // Failed to update display name
                                                    Toast.makeText(RegistrationScreen.this, "Username update failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    // User sign-up failed
                                    Toast.makeText(RegistrationScreen.this, "Sign-up failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}
