package com.nomad.mybrainmemory.play;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nomad.mybrainmemory.PlayScreenMatching;
import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.game.InfoBox;
import com.nomad.mybrainmemory.jigsawpuzzle.puzzle.PuzzleActivity;
import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.util.StaticConstants;


public class FailedScreen extends Fragment {
    Button retryBtn, quitBtn, infoBtn;
    GameModel gameModel;
    String fragment_round_num;
    InfoBox infoBox;

    ProgressBar progressBar;

    public FailedScreen(GameModel gameModel, String fragment_round_num){
        this.gameModel = gameModel;
        this.fragment_round_num = fragment_round_num;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fail_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retryBtn = view.findViewById(R.id.retry_btn);
        quitBtn = view.findViewById(R.id.quit_btn);
//        infoBtn = view.findViewById(R.id.info_btn);
        infoBox = new InfoBox();

        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        quitBtn.setOnClickListener(new View.OnClickListener() {
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
            retryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundOne(gameModel)).commit();
                }
            });
        }else if(fragment_round_num.equals("Round 2")){
//            finalScore.setText(null);
            retryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundTwo(gameModel)).commit();
                }
            });
        }  else if(fragment_round_num.equals("Round Hard Puzzle")){

            retryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundHard(gameModel)).commit();

                    progressBar.setVisibility(View.VISIBLE);

                    Intent i = new Intent(getContext(), PuzzleActivity.class);
//                     Class<?> myClass = (Class<?>) getIntent().getSerializableExtra("class_key");
                    i.putExtra(StaticConstants.KEY_GAME_SCORE,gameModel);
                    i.putExtra(StaticConstants.KEY_DIFFICULTY_LEVEL, StaticConstants.LEVEL_MEDIUM);
                    startActivity(i);
                    getActivity().finish();

                }

            });
        }
    }
}