package com.nomad.mybrainmemory.play;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nomad.mybrainmemory.PlayScreenMatching;
import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.game.InfoBox;
import com.nomad.mybrainmemory.model.GameModel;


public class CongratsScreen extends Fragment {
    Button backBtn, nextLevelBtn, infoBtn;
    TextView finalScore;

    TextView timeSpent;
    GameModel gameModel;
    String fragment_round_num;
    InfoBox infoBox;

    public CongratsScreen(GameModel gameModel, String fragment_round_num){
        this.gameModel = gameModel;
        this.fragment_round_num = fragment_round_num;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundTwo(gameModel)).commit();
                }
            });
        }else if(fragment_round_num.equals("Round 2")){
//            finalScore.setText(null);
            finalScore.setText("Score: " + String.valueOf(gameModel.getScore()));
            timeSpent.setText("Time Spent: " + String.valueOf(gameModel.getTimeSpent()));
            nextLevelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundThree(gameModel)).commit();
                }
            });
        }  else if(fragment_round_num.equals("Round 3")){
            finalScore.setText("Final Score: " + String.valueOf(gameModel.getScore()));
            timeSpent.setText("Time Spent: " + String.valueOf(gameModel.getTimeSpent()));
            nextLevelBtn.setText(R.string.play_again);

            nextLevelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                    getActivity().startActivity(new Intent(getContext(), PlayScreenMatching.class));
                }
            });
        }
    }
}