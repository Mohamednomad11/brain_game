package com.nomad.mybrainmemory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.play.RoundHard;
import com.nomad.mybrainmemory.play.RoundMedium;
import com.nomad.mybrainmemory.play.RoundOne;
import com.nomad.mybrainmemory.play.RoundThree;
import com.nomad.mybrainmemory.play.RoundTwo;
import com.nomad.mybrainmemory.util.StaticConstants;


public class PlayScreenMatching extends AppCompatActivity {

    GameModel gameModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        Intent intent = getIntent();

// Retrieve the arguments from the Intent using the same keys
        String difficultyLevel = intent.getStringExtra(StaticConstants.KEY_DIFFICULTY_LEVEL);


        gameModel = new GameModel();

        if(difficultyLevel.equals(StaticConstants.LEVEL_EASY)){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundOne(gameModel)).commit();
        }else if (difficultyLevel.equals(StaticConstants.LEVEL_MEDIUM)){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundTwo(gameModel)).commit();
        }else if (difficultyLevel.equals(StaticConstants.LEVEL_HARD)){
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundThree(gameModel)).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundHard(gameModel)).commit();
        }


    }
}