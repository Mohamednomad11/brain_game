package com.nomad.mybrainmemory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MenuScreen extends AppCompatActivity {
    LinearLayout matchingButton, jigsawButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);
        matchingButton = findViewById(R.id.ll_image_matching);
        jigsawButton = findViewById(R.id.ll_jigsaw_puzzle);

        matchingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuScreen.this, MatchingStartScreen.class);
                startActivity(i);
            }
        });

        jigsawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuScreen.this, RegistrationScreen.class);
                startActivity(i);
            }
        });
    }
}
