package com.nomad.mybrainmemory.play;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.adapter.gameadapter.CardAdapter;
import com.nomad.mybrainmemory.game.InfoBox;
import com.nomad.mybrainmemory.game.PopulateCard;
import com.nomad.mybrainmemory.model.CardModel;
import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.util.TimerUtils;

import java.util.ArrayList;


public class RoundTwo extends Fragment {

    RecyclerView recyclerView;
    GameModel gameModel;
    TextView gameScore, animScore;
    ImageView backBtn, infoBtn;
    InfoBox infoBox;

    private Handler firstViewHandler = new Handler();

    public RoundTwo(GameModel gameModel){
        this.gameModel = gameModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_round_two, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.animals_card);
        gameScore = view.findViewById(R.id.game_score);
        infoBtn = view.findViewById(R.id.info_btn);
        backBtn = view.findViewById(R.id.back_btn);
        animScore = view.findViewById(R.id.anim_score);
        infoBox = new InfoBox();

        TextView timerTextView = view.findViewById(R.id.timerTextView);
        TimerUtils timerUtils = new TimerUtils(120000,timerTextView,"Round 2",RoundTwo.this,gameModel);
//        timerUtils.startTimer();

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        PopulateCard populateCard = new PopulateCard(16,2);
        ArrayList<CardModel> cardList = populateCard.populateCard();
        alterImages(cardList);
        CardAdapter cardAdapter = new CardAdapter(cardList, getContext(), gameModel, gameScore, animScore, populateCard.getTotalAnimals(), getParentFragmentManager(), "Round 2", timerUtils);
        recyclerView.setAdapter(cardAdapter);
        gameScore.setText(String.valueOf(gameModel.getScore()));

        firstViewHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alterImages(cardAdapter.getmData());
                cardAdapter.notifyDataSetChanged();
                timerUtils.startTimer();
            }
        },30000);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerUtils.finishTimer();
                getActivity().finish();
            }
        });

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerUtils.pauseTimer();
                infoBox.createPauseDialog(getContext(),timerUtils,RoundTwo.this,gameModel, "Round 2");
            }
        });
    }


    private  void alterImages(ArrayList<CardModel> cardList){
        for (CardModel card:
                cardList) {
            card.alterImages();
        }
    }
}