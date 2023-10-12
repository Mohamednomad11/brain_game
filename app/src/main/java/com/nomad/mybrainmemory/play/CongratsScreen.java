package com.nomad.mybrainmemory.play;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.color.utilities.Score;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.nomad.mybrainmemory.DifficultyLevelScreen;
import com.nomad.mybrainmemory.MatchingStartScreen;
import com.nomad.mybrainmemory.PlayScreenMatching;
import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.game.InfoBox;
import com.nomad.mybrainmemory.game.ScoreDB;
import com.nomad.mybrainmemory.jigsawpuzzle.puzzle.PuzzleActivity;
import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.model.ScoreModel;
import com.nomad.mybrainmemory.util.FireStoreUtils;
import com.nomad.mybrainmemory.util.StaticConstants;


public class CongratsScreen extends Fragment {
    Button backBtn, nextLevelBtn, infoBtn;
    TextView finalScore;

    TextView timeSpent;
    GameModel gameModel;
    String fragment_round_num;
    InfoBox infoBox;

    ScoreDB scoreDB;

    ProgressBar progressBar;

    public CongratsScreen(GameModel gameModel, String fragment_round_num){
        this.gameModel = gameModel;
        this.fragment_round_num = fragment_round_num;

    }

    public  void saveScore(GameModel gameModel){
        String name = StaticConstants.CURRENT_USER_NAME;
        String score = String.valueOf(gameModel.getScore()) ;
        String time = String.valueOf(gameModel.getTimeSpent()) ;
        Timestamp timestamp = Timestamp.now();
        float accuracy = gameModel.getAccuracy();
        long avgReactionTime = gameModel.getAverageReactionTime();
        long avgSucReactinTime = gameModel.getAverageSuccessReactionTime();

        long rowId = scoreDB.addScore(name,score,time,StaticConstants.GAME_MATCHING,timestamp,accuracy,avgReactionTime,avgSucReactinTime);
        ScoreModel scoreModel = scoreDB.getScore(rowId);
        Log.e("Congrats Screen", scoreModel.toString());
        if(Boolean.TRUE.equals(gameModel.getLevelStatus().get(3))){
            FireStoreUtils.saveScore(scoreModel);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = requireContext();
        scoreDB = new ScoreDB(context);
        saveScore(gameModel);
        return inflater.inflate(R.layout.fragment_congrats_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backBtn = view.findViewById(R.id.back_btn);
        nextLevelBtn = view.findViewById(R.id.next_level_btn);
//        infoBtn = view.findViewById(R.id.info_btn);
        finalScore = view.findViewById(R.id.final_score);
        timeSpent = view.findViewById(R.id.time_spent);
        infoBox = new InfoBox();

        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

//        infoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                infoBox.infoBox(getContext());
//            }
//        });

        if(fragment_round_num.equals("Round 1")){

//            finalScore.setText(null);
            finalScore.setText("Score: " + String.valueOf(gameModel.getScore()));
            timeSpent.setText("Time Spent: " + String.valueOf(gameModel.getTimeSpent()));
            nextLevelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundHard(gameModel)).commit();
                }
            });
        }else if(fragment_round_num.equals("Round 2")){
//            finalScore.setText(null);
            finalScore.setText("Score: " + String.valueOf(gameModel.getScore()));
            timeSpent.setText("Time Spent: " + String.valueOf(gameModel.getTimeSpent()));
            nextLevelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundHard(gameModel)).commit();
                }
            });
        }  else if(fragment_round_num.equals("Round Hard")){
            finalScore.setText("Final Score: " + String.valueOf(gameModel.getScore()));
            timeSpent.setText("Time Spent: " + String.valueOf(gameModel.getTimeSpent()));
//            nextLevelBtn.setText(R.string.play_again);

            nextLevelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    getActivity().finish();
                    Intent i = new Intent(getContext(), PuzzleActivity.class);
//                     Class<?> myClass = (Class<?>) getIntent().getSerializableExtra("class_key");
                    i.putExtra(StaticConstants.KEY_GAME_SCORE,gameModel);
                    i.putExtra(StaticConstants.KEY_DIFFICULTY_LEVEL, StaticConstants.LEVEL_MEDIUM);
                    startActivity(i);
                }
            });
        }
    }
}