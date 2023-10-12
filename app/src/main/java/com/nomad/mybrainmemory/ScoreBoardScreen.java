package com.nomad.mybrainmemory;

import android.content.Context;
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
import com.nomad.mybrainmemory.model.ScoreModel;
import com.nomad.mybrainmemory.util.StaticConstants;

import java.util.ArrayList;

public class ScoreBoardScreen extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView backBtn;

    Button avgPerformanceButton;

    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_board);

        mcontext = this;

        recyclerView = findViewById(R.id.score_card);
        backBtn = findViewById(R.id.back_btn);

        avgPerformanceButton = findViewById(R.id.btn_avg_performance);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PopulateScore populateScore = new PopulateScore(this);




        ScoreAdapter scoreAdapter = new ScoreAdapter(this,populateScore.populateScore());
        recyclerView.setAdapter(scoreAdapter);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        avgPerformanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(populateScore.populateScore().size() > 0){

                    ScoreModel averageScoreModel = getAverageScoreModel(populateScore.populateScore());
                    Intent i = new Intent(mcontext, PerformanceReport.class);
                    i.putExtra(StaticConstants.KEY_SCORE_REPORT,averageScoreModel);
                    mcontext.startActivity(i);
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private ScoreModel getAverageScoreModel(ArrayList<ScoreModel> scoreList){

        // Initialize variables to store sums
        int totalScore = 0;
        long totalAvgReactionTime = 0;
        long totalAvgSucReactionTime = 0;
        long totalTimeInSseconds = 0; // Assuming time is in HH:mm format
        float totalAccuracy = 0f;

        // Calculate sums
        for (ScoreModel scoreModel : scoreList) {
            totalScore += scoreModel.getScore();
            totalAvgReactionTime += scoreModel.getAvgReactionTime();
            totalAvgSucReactionTime += scoreModel.getAvgSucReactionTime();
            totalAccuracy += scoreModel.getAccuracy();

            // Parse and sum the time in milliseconds
            totalTimeInSseconds +=  Integer.valueOf(scoreModel.getTime()) ;
        }

        // Calculate averages
        int averageScore = (int) totalScore / scoreList.size();
        float averageAccuracy = (float) totalAccuracy / scoreList.size();
        long averageAvgReactionTime = (long) totalAvgReactionTime / scoreList.size();
        long averageAvgSucReactionTime = (long) totalAvgSucReactionTime / scoreList.size();

        // Convert total time back to HH:mm format
        long averageTimeInSeconds = totalTimeInSseconds / scoreList.size();
        ;

        System.out.println("Average Score: " + averageScore);
        System.out.println("Average Avg Reaction Time: " + averageAvgReactionTime);
        System.out.println("Average Avg Suc Reaction Time: " + averageAvgSucReactionTime);
        System.out.println("Average Time: " + averageTimeInSeconds);

        ScoreModel avgScoreModel = new ScoreModel(averageScore,String.valueOf(averageTimeInSeconds) ,averageAccuracy,averageAvgReactionTime,averageAvgSucReactionTime);

        return avgScoreModel;


    }
}
