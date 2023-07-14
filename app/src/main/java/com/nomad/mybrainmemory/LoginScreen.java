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
import com.nomad.mybrainmemory.util.StaticConstants;
import com.nomad.mybrainmemory.util.ValidatorUtils;

public class LoginScreen extends AppCompatActivity {
    Button loginButton;
    TextView signupText;

    TextInputEditText email,password;

    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        mAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.login_button);
        signupText = findViewById(R.id.sign_up_text);


        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_pass);


        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                if(!ValidatorUtils.isEmailValid(email.getText().toString())){
                    progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(LoginScreen.this, StaticConstants.WARN_INVALID_EMAIL, Toast.LENGTH_LONG).show();

                }else if(password.getText().length() <1){
                    progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(LoginScreen.this, StaticConstants.WARN_EMPTY_FIELD, Toast.LENGTH_LONG).show();

                }else {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // User sign-in successful
                                    progressBar.setVisibility(View.INVISIBLE);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        StaticConstants.CURRENT_USER_NAME = user.getDisplayName();
                                    }
                                    Intent i = new Intent(LoginScreen.this, MenuScreen.class);
                                    startActivity(i);
                                } else {
                                    // User sign-in failed
                                    Toast.makeText(LoginScreen.this, "Sign-in failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginScreen.this, RegistrationScreen.class);
                startActivity(i);
            }
        });
    }
}
