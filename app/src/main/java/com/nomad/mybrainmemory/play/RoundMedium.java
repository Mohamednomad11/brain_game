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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.adapter.gameadapter.CardAdapter;
import com.nomad.mybrainmemory.dragDrop.DListener;
import com.nomad.mybrainmemory.dragDrop.ListAdapterDrag;
import com.nomad.mybrainmemory.game.InfoBox;
import com.nomad.mybrainmemory.game.PopulateCard;
import com.nomad.mybrainmemory.model.CardModel;
import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.util.TimerUtils;

import java.util.ArrayList;
import java.util.List;


public class RoundMedium extends Fragment implements DListener {

//    RecyclerView recyclerView;
    GameModel gameModel;
//    TextView gameScore, animScore;
//    ImageView backBtn, infoBtn;
//    InfoBox infoBox;

//    ---------
//@BindView(R.id.rvTop)
    RecyclerView rvTop;
//    @BindView(R.id.rvBottom)
    RecyclerView rvBottom;
//    @BindView(R.id.tvEmptyListTop)
//    TextView tvEmptyListTop;
//    @BindView(R.id.tvEmptyListBottom)
//    TextView tvEmptyListBottom;

    private Handler firstViewHandler = new Handler();

    public RoundMedium(GameModel gameModel){
        this.gameModel = gameModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.drag_drop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTop = view.findViewById(R.id.rvTop);
        rvBottom = view.findViewById(R.id.rvBottom);
//        tvEmptyListTop = view.findViewById(R.id.tvEmptyListTop);
//        tvEmptyListBottom = view.findViewById(R.id.tvEmptyListBottom);

        initTopRecyclerView();
        initBottomRecyclerView();

//        tvEmptyListTop.setVisibility(View.GONE);
//        tvEmptyListBottom.setVisibility(View.GONE);
//        gameScore = view.findViewById(R.id.game_score);
//        infoBtn = view.findViewById(R.id.info_btn);
//        backBtn = view.findViewById(R.id.back_btn);
//        animScore = view.findViewById(R.id.anim_score);
//        infoBox = new InfoBox();

/*        TextView timerTextView = view.findViewById(R.id.timerTextView);
        TimerUtils timerUtils = new TimerUtils(90000,timerTextView,"Round 1", RoundMedium.this,gameModel);
//        timerUtils.startTimer();


        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 6));
        PopulateCard populateCard = new PopulateCard(36,3);
        ArrayList<CardModel> cardList = populateCard.populateCard();
        alterImages(cardList);
        CardAdapter cardAdapter = new CardAdapter(cardList, getContext(), gameModel, gameScore, animScore, populateCard.getTotalAnimals(), getParentFragmentManager(), "Round 3", timerUtils);
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
                infoBox.createPauseDialog(getContext(),timerUtils, RoundMedium.this,gameModel, "Round 3");
            }
        });*/
    }

    private void initTopRecyclerView() {
        rvTop.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false));

        List<String> topList = new ArrayList<>();
        topList.add("A");
        topList.add("B");
        topList.add("C");
        topList.add("D");

        ListAdapterDrag topListAdapter = new ListAdapterDrag(topList, this);
        rvTop.setAdapter(topListAdapter);
//        tvEmptyListTop.setOnDragListener(topListAdapter.getDragInstance());
//        rvTop.setOnDragListener(topListAdapter.getDragInstance());
    }

    private void initBottomRecyclerView() {
        rvBottom.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false));

        List<String> bottomList = new ArrayList<>();
        bottomList.add("Match A");
        bottomList.add("Match B");
        bottomList.add("Match C");
        bottomList.add("Match D");


        ListAdapterDrag bottomListAdapter = new ListAdapterDrag(bottomList, this);
        rvBottom.setAdapter(bottomListAdapter);
//        tvEmptyListBottom.setOnDragListener(bottomListAdapter.getDragInstance());
//        rvBottom.setOnDragListener(bottomListAdapter.getDragInstance());
    }

    private  void alterImages(ArrayList<CardModel> cardList){
        for (CardModel card:
                cardList) {
            card.alterTouchability();
            card.alterImages();
        }
    }

    @Override
    public void setEmptyListTop(boolean visibility) {
//        tvEmptyListTop.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvTop.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
//        tvEmptyListBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvBottom.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }
}