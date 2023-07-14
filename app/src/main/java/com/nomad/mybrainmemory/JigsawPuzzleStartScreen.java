package com.nomad.mybrainmemory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nomad.mybrainmemory.util.StaticConstants;

public class JigsawPuzzleStartScreen extends AppCompatActivity {
    Button startButton;
    ProgressBar progressBar;
    TextView progressText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_jigsaw_puzzle);
        startButton = findViewById(R.id.start_button);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);
//        progressBar.setIndeterminate(true);
//        progressBar.setVisibility(View.VISIBLE);
        // Show the ProgressBar
//        progressBar.setVisibility(View.VISIBLE);

// Hide the ProgressBar
//        progressBar.setVisibility(View.GONE);

// Set the progress value (0-100)
//        progressBar.setProgress(50);

// Set the maximum value of the ProgressBar
        progressBar.setMax(100);

        int progress = 50; // Replace with your desired progress value
        progressBar.setProgress(progress);

        String progressValue = String.valueOf(progress)+"%"; // Convert the progress value to string
        progressText.setText(progressValue);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JigsawPuzzleStartScreen.this, DifficultyLevelScreen.class);
                i.putExtra(StaticConstants.KEY_GAME_NAME, StaticConstants.GAME_JIGSAW);
                startActivity(i);
            }
        });


    }
}
