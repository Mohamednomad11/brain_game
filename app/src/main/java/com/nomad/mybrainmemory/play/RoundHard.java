package com.nomad.mybrainmemory.play;

import android.content.ClipData;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.game.InfoBox;
import com.nomad.mybrainmemory.game.ScoreAnimation;
import com.nomad.mybrainmemory.model.CardModel;
import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.util.TimerUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class RoundHard extends Fragment{

    GameModel gameModel;
    TextView gameScore, animScore;
    ImageView backBtn, infoBtn;
    InfoBox infoBox;


    private Handler firstViewHandler = new Handler();

    Map<Integer,String> textMap = new HashMap<>();

    private boolean isElephantSet = false;
    private boolean isTigerSet = false;
    private boolean isRhinoSet = false;
    private boolean isGiraffeSet = false;

    TimerUtils timerUtils;

    ScoreAnimation scoreAnimation = new ScoreAnimation();

    Handler handler = new Handler();

    public RoundHard(GameModel gameModel){
        this.gameModel = gameModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matching_hard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        infoBtn = view.findViewById(R.id.info_btn);
        gameScore = view.findViewById(R.id.tvGame_scoreLevel);
        animScore = view.findViewById(R.id.game_score);
        backBtn = view.findViewById(R.id.back_btn);
        infoBox = new InfoBox();

        ImageView image_elephant = view.findViewById(R.id.ivLeftImage1);
        ImageView image_giraffe = view.findViewById(R.id.ivLeftImage2);
        ImageView image_rhino = view.findViewById(R.id.ivLeftImage3);
        ImageView image_tiger = view.findViewById(R.id.ivLeftImage4);


        CardView cardView1 = view.findViewById(R.id.cvRightOne);
        CardView cardView2 = view.findViewById(R.id.cvRightTwo);
        CardView cardView3 = view.findViewById(R.id.cvRightThree);
        CardView cardView4 = view.findViewById(R.id.cvRightFour);


        TextView textView1 = view.findViewById(R.id.tvRightImage1);
        TextView textView2 = view.findViewById(R.id.tvRightImage2);
        TextView textView3 = view.findViewById(R.id.tvRightImage3);
        TextView textView4 = view.findViewById(R.id.tvRightImage4);



        List<String> textList = new ArrayList<>();
        textList.add("Tiger");
        textList.add("Rhino");
        textList.add("Giraffe");
        textList.add("Elephant");

        Collections.shuffle(textList);

        textMap.put(textView1.getId(),textList.get(0));
        textView1.setText(textList.get(0));
        textMap.put(textView2.getId(),textList.get(1));
        textView2.setText(textList.get(1));
        textMap.put(textView3.getId(),textList.get(2));
        textView3.setText(textList.get(2));
        textMap.put(textView4.getId(),textList.get(3));
        textView4.setText(textList.get(3));


        TextView timerTextView = view.findViewById(R.id.timerTextView);
        timerUtils = new TimerUtils(180000,timerTextView,"Round Hard",RoundHard.this,gameModel);
        timerUtils.startTimer();

        image_elephant.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!isElephantSet){
                    ClipData data = ClipData.newPlainText("image_resource", "image_elephant");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                }

                return true;
            }
        });

        image_giraffe.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!isGiraffeSet){
                    ClipData data = ClipData.newPlainText("image_resource", "image_giraffe");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                }

                return true;
            }
        });

        image_rhino.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!isRhinoSet){
                    ClipData data = ClipData.newPlainText("image_resource", "image_rhino");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                }

                return true;
            }
        });

        image_tiger.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!isTigerSet){
                    ClipData data = ClipData.newPlainText("image_resource", "image_tiger");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                }

                return true;
            }
        });

        // Set up drop listeners for CardViews
        cardView1.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                handleDropEvent(cardView1, event,R.id.ivRightImage1,textView1);
                return true;
            }
        });

        cardView2.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                handleDropEvent(cardView2, event,R.id.ivRightImage2,textView2);
                return true;
            }
        });

        cardView3.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                handleDropEvent(cardView3, event,R.id.ivRightImage3,textView3);
                return true;
            }
        });

        cardView4.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                handleDropEvent(cardView4, event,R.id.ivRightImage4,textView4);
                return true;
            }
        });


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
                infoBox.createPauseDialog(getContext(),timerUtils,RoundHard.this,gameModel,"Round Hard");

            }
        });

    }

    private void handleDropEvent(CardView cardView, DragEvent event, int ivRightImageId, TextView textView) {
        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DROP:
                ClipData.Item item = event.getClipData().getItemAt(0);
                String draggedImageResource = item.getText().toString();
                ImageView cardImageView = cardView.findViewById(ivRightImageId); // Replace with your actual ImageView ID
                // Set the image resource for the CardView
                String textName = textMap.get(textView.getId());
                Log.e("RoundHard","  " + draggedImageResource  + " textName of target  " + textName);
                switch (draggedImageResource) {
                    case "image_elephant":
                        if(textName.equals("Elephant") && !isElephantSet){
                            cardImageView.setImageResource(R.drawable.elephant);
                            isElephantSet = true;
                            scoreAnimation.animationScore(animScore, "+10");
                            gameModel.setScore(+10);
                            scoreAnimation.delaySetText(gameScore, String.valueOf(gameModel.getScore()));
                        }else{
                            Toast.makeText(getContext(), "Image does not match", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "image_giraffe":
                        if(textName.equals("Giraffe") && !isGiraffeSet){
                            cardImageView.setImageResource(R.drawable.giraffe);
                            isGiraffeSet = true;
                            scoreAnimation.animationScore(animScore, "+10");
                            gameModel.setScore(+10);
                            scoreAnimation.delaySetText(gameScore, String.valueOf(gameModel.getScore()));
                        }else{
                            Toast.makeText(getContext(), "Image does not match", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "image_rhino":
                        if(textName.equals("Rhino") && !isRhinoSet){
                            cardImageView.setImageResource(R.drawable.rhino);
                            isRhinoSet = true;
                            scoreAnimation.animationScore(animScore, "+10");
                            gameModel.setScore(+10);
                            scoreAnimation.delaySetText(gameScore, String.valueOf(gameModel.getScore()));
                        }else{
                            Toast.makeText(getContext(), "Image does not match", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "image_tiger":
                        if(textName.equals("Tiger") && !isTigerSet){
                            cardImageView.setImageResource(R.drawable.tiger);
                            isTigerSet = true;
                            scoreAnimation.animationScore(animScore, "+10");
                            gameModel.setScore(+10);
                            scoreAnimation.delaySetText(gameScore, String.valueOf(gameModel.getScore()));
                        }else{
                            Toast.makeText(getContext(), "Image does not match", Toast.LENGTH_LONG).show();
                        }
                        break;
                    // Handle other cases for additional ImageViews
                }
                if(gameModel.getScore() == 40){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            gameModel.setTimeSpent(timerUtils.getTimeLeftInSeconds());
                            timerUtils.finishTimer();
                            gameModel.updateLevelStatus(3,true);
//                                InfoBox infoBox = new InfoBox();
//                                infoBox.addNameScore(context, String.valueOf(gameModel.getScore()),String.valueOf(gameModel.getTimeSpent()), StaticConstants.GAME_MATCHING);
                            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new CongratsScreen(gameModel, "Round 3")).commit();

                        }
                    },500);

                }

                break;
        }
    }


    private  void alterImages(ArrayList<CardModel> cardList){
        for (CardModel card:
                cardList) {
            card.alterTouchability();
            card.alterImages();
        }
    }
}