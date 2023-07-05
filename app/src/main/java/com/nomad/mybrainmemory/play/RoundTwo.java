package com.nomad.mybrainmemory.play;

import android.os.Bundle;
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
import com.nomad.mybrainmemory.model.GameModel;


public class RoundTwo extends Fragment {

    RecyclerView recyclerView;
    GameModel gameModel;
    TextView gameScore, animScore;
    ImageView backBtn, infoBtn;
    InfoBox infoBox;

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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        PopulateCard populateCard = new PopulateCard(6);
        CardAdapter cardAdapter = new CardAdapter(populateCard.populateCard(), getContext(), gameModel, gameScore, animScore, populateCard.getTotalAnimals(), getFragmentManager(), "Round 2");
        recyclerView.setAdapter(cardAdapter);
        gameScore.setText(String.valueOf(gameModel.getScore()));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoBox.infoBox(getContext());
            }
        });
    }
}