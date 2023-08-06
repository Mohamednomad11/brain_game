package com.nomad.mybrainmemory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nomad.mybrainmemory.jigsawpuzzle.adapter.StorePreference;
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

                                    FirebaseUser user = mAuth.getCurrentUser();

                                    if (user != null) {
                                        StaticConstants.CURRENT_USER_NAME = user.getDisplayName();
                                    }



                                    if (user != null) {
                                        String userId = user.getUid();
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                                        // Replace "users" with the collection name where user data is stored
                                        DocumentReference userRef = db.collection("users").document(userId);

                                        userRef.get().addOnCompleteListener(task2 -> {
                                            if (task2.isSuccessful()) {
                                                DocumentSnapshot document = task2.getResult();
                                                if (document.exists()) {
                                                    // Get the user type from the document
                                                    String userType = document.getString("userType");
                                                    if (userType != null) {
                                                        // You have the user type, do something with it
                                                        Log.d("TAG", "User type: " + userType);
                                                        StorePreference storePreference = new StorePreference(getApplicationContext());
                                                        storePreference.setString(StaticConstants.KEY_USER_TYPE,userType);

                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        Intent i = new Intent(LoginScreen.this, MenuScreen.class);
                                                        startActivity(i);

                                                    } else {
                                                        // The user type is not available or not set
                                                        Log.d("TAG", "User type not available.");
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        // User sign-in failed
                                                        Toast.makeText(LoginScreen.this, "Sign-in failed.", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    // The user document does not exist
                                                    Log.d("TAG", "User document does not exist.");
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    // User sign-in failed
                                                    Toast.makeText(LoginScreen.this, "Sign-in failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                // Failed to get user document, handle the error
                                                Log.e("TAG", "Error getting user document: " + task2.getException().getMessage());
                                                progressBar.setVisibility(View.INVISIBLE);
                                                // User sign-in failed
                                                Toast.makeText(LoginScreen.this, "Sign-in failed.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
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
