package com.nomad.mybrainmemory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DifficultyLevelScreen extends AppCompatActivity {
    Button easyButton, mediumButton, hardButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty_level_screen);
        easyButton = findViewById(R.id.level_easy);
        mediumButton = findViewById(R.id.level_medium);
        hardButton = findViewById(R.id.level_hard);

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DifficultyLevelScreen.this, PlayScreen.class);
                startActivity(i);
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DifficultyLevelScreen.this, PlayScreen.class);
                startActivity(i);
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DifficultyLevelScreen.this, PlayScreen.class);
                startActivity(i);
            }
        });
    }
}
