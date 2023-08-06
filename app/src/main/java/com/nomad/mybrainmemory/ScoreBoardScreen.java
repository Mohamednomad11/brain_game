package com.nomad.mybrainmemory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nomad.mybrainmemory.adapter.gameadapter.CardAdapter;
import com.nomad.mybrainmemory.adapter.scoreadapter.ScoreAdapter;
import com.nomad.mybrainmemory.game.PopulateCard;
import com.nomad.mybrainmemory.game.PopulateScore;
import com.nomad.mybrainmemory.jigsawpuzzle.puzzle.PuzzleActivity;
import com.nomad.mybrainmemory.util.StaticConstants;

public class ScoreBoardScreen extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_board);

        recyclerView = findViewById(R.id.score_card);
        backBtn = findViewById(R.id.back_btn);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PopulateScore populateScore = new PopulateScore(this);
        ScoreAdapter scoreAdapter = new ScoreAdapter(populateScore.populateScore());
        recyclerView.setAdapter(scoreAdapter);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
