package com.nomad.mybrainmemory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nomad.mybrainmemory.util.StaticConstants;

public class MenuScreen extends AppCompatActivity {
    LinearLayout matchingButton, jigsawButton;

    TextView nameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);
        matchingButton = findViewById(R.id.ll_image_matching);
        jigsawButton = findViewById(R.id.ll_jigsaw_puzzle);
        nameText = findViewById(R.id.name_text);

        nameText.setText(StaticConstants.CURRENT_USER_NAME);

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
                Intent i = new Intent(MenuScreen.this, JigsawPuzzleStartScreen.class);
                startActivity(i);
            }
        });
    }
}
