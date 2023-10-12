package com.nomad.mybrainmemory.play;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nomad.mybrainmemory.DifficultyLevelScreen;
import com.nomad.mybrainmemory.MenuScreen;
import com.nomad.mybrainmemory.PerformanceReport;
import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.SearchReportActivity;
import com.nomad.mybrainmemory.game.InfoBox;
import com.nomad.mybrainmemory.game.ScoreDB;
import com.nomad.mybrainmemory.jigsawpuzzle.puzzle.PuzzleActivity;
import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.model.ScoreModel;
import com.nomad.mybrainmemory.util.FireStoreUtils;
import com.nomad.mybrainmemory.util.StaticConstants;


public class CongratsScreenActivity extends AppCompatActivity {
    Button backBtn, nextLevelBtn, infoBtn;
    TextView finalScore;

    TextView timeSpent;
    GameModel gameModel;
    String fragment_round_num;
    InfoBox infoBox;

    ScoreDB scoreDB;

    ScoreModel lartestScore;

    ProgressBar progressBar;

//    public CongratsScreenActivity(GameModel gameModel, String fragment_round_num){
//        this.gameModel = gameModel;
//        this.fragment_round_num = fragment_round_num;
//
//    }

    public  void saveScore(GameModel gameModel){
        String name = StaticConstants.CURRENT_USER_NAME;
        String score = String.valueOf(gameModel.getScore()) ;
        String time = String.valueOf(gameModel.getTimeSpent()) ;
        Timestamp timestamp = Timestamp.now();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

        float accuracy = gameModel.getAccuracy();
        long avgReactionTime = gameModel.getAverageReactionTime();
        long avgSucReactionTime = gameModel.getAverageSuccessReactionTime();

        long rowId = scoreDB.addScore(uid,name,score,time,StaticConstants.GAME_MATCHING,timestamp,accuracy,avgReactionTime,avgSucReactionTime);
       lartestScore = scoreDB.getScore(rowId);
        Log.e("Congrats Screen", lartestScore.toString());
        if((gameModel.getLevelStatus().get(3)) != null && (gameModel.getLevelStatus().get(3)) == 1){
            FireStoreUtils.saveScore(lartestScore);
        }
    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = requireContext();
        scoreDB = new ScoreDB(context);
        saveScore(gameModel);
        return inflater.inflate(R.layout.fragment_congrats_screen, container, false);
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_congrats_screen);
        backBtn = findViewById(R.id.back_btn);
        nextLevelBtn = findViewById(R.id.next_level_btn);
//        infoBtn = view.findViewById(R.id.info_btn);
        finalScore = findViewById(R.id.final_score);
        timeSpent = findViewById(R.id.time_spent);

        progressBar = findViewById(R.id.progressBar);
        infoBox = new InfoBox();

        Intent intent = getIntent();

        gameModel = intent.getParcelableExtra(StaticConstants.KEY_GAME_SCORE);

        scoreDB = new ScoreDB(this);
        saveScore(gameModel);


        progressBar.setVisibility(View.INVISIBLE);

        nextLevelBtn.setText(R.string.generate_report);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CongratsScreenActivity.this, DifficultyLevelScreen.class);
                startActivity(i);
                finish();
            }
        });

//        infoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                infoBox.infoBox(getContext());
//            }
//        });

            finalScore.setText("Final Score: " + String.valueOf(gameModel.getScore()));
            timeSpent.setText("Time Spent: " + String.valueOf(gameModel.getTimeSpent()));
//            nextLevelBtn.setText(R.string.play_again);

            nextLevelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    finish();
                    Intent i = new Intent(CongratsScreenActivity.this, PerformanceReport.class);
                    i.putExtra(StaticConstants.KEY_SCORE_REPORT,lartestScore);
                    startActivity(i);
                }
            });
    }
}