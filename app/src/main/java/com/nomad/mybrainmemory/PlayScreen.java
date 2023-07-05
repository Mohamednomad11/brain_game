package com.nomad.mybrainmemory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.play.RoundOne;


public class PlayScreen extends AppCompatActivity {

    GameModel gameModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);
        gameModel = new GameModel();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundOne(gameModel)).commit();
    }
}