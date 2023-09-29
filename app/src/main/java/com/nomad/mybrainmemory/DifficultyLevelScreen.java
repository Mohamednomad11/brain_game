package com.nomad.mybrainmemory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.nomad.mybrainmemory.jigsawpuzzle.puzzle.PuzzleActivity;
import com.nomad.mybrainmemory.util.StaticConstants;

public class DifficultyLevelScreen extends AppCompatActivity {
    Button easyButton, mediumButton, hardButton;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.difficulty_level_screen);
        easyButton = findViewById(R.id.level_easy);
        mediumButton = findViewById(R.id.level_medium);
        hardButton = findViewById(R.id.level_hard);
        progressBar = findViewById(R.id.progressBar);

        Intent intent = getIntent();

        progressBar.setVisibility(View.INVISIBLE);

// Retrieve the arguments from the Intent using the same keys
        String gameName = intent.getStringExtra(StaticConstants.KEY_GAME_NAME);

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("DifficultyLevel",gameName);
                progressBar.setVisibility(View.VISIBLE);
                if(StaticConstants.GAME_MATCHING.equals(gameName)){
                    Intent i = new Intent(DifficultyLevelScreen.this, PlayScreenMatching.class);
                    i.putExtra(StaticConstants.KEY_DIFFICULTY_LEVEL, StaticConstants.LEVEL_EASY);
                    startActivity(i);
                }else if(StaticConstants.GAME_JIGSAW.equals(gameName)){
                    Intent i = new Intent(DifficultyLevelScreen.this, PuzzleActivity.class);
                    i.putExtra(StaticConstants.KEY_DIFFICULTY_LEVEL, StaticConstants.LEVEL_EASY);
                    startActivity(i);
                }

            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(gameName.equals(StaticConstants.GAME_MATCHING)){
                    Intent i = new Intent(DifficultyLevelScreen.this, PlayScreenMatching.class);
                    i.putExtra(StaticConstants.KEY_DIFFICULTY_LEVEL, StaticConstants.LEVEL_HARD);
                    startActivity(i);
                }else if(StaticConstants.GAME_JIGSAW.equals(gameName)){
                    Intent i = new Intent(DifficultyLevelScreen.this, PuzzleActivity.class);
                    i.putExtra(StaticConstants.KEY_DIFFICULTY_LEVEL, StaticConstants.LEVEL_MEDIUM);
                    startActivity(i);
                }
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(gameName.equals(StaticConstants.GAME_MATCHING)){
//                    Intent i = new Intent(DifficultyLevelScreen.this, PlayScreenMatching.class);
//                    i.putExtra(StaticConstants.KEY_DIFFICULTY_LEVEL, StaticConstants.LEVEL_HARD);
//                    startActivity(i);
                    Intent i = new Intent(DifficultyLevelScreen.this, PuzzleActivity.class);
                    i.putExtra(StaticConstants.KEY_DIFFICULTY_LEVEL, StaticConstants.LEVEL_MEDIUM);
                    startActivity(i);
                }else if(StaticConstants.GAME_JIGSAW.equals(gameName)){
                    Intent i = new Intent(DifficultyLevelScreen.this, PuzzleActivity.class);
                    i.putExtra(StaticConstants.KEY_DIFFICULTY_LEVEL, StaticConstants.LEVEL_HARD);
                    startActivity(i);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        // Start the specific activity when the back button is pressed
        Intent intent = new Intent(this, MatchingStartScreen.class);
        startActivity(intent);

        // Finish the current activity (optional, depending on your use case)
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
