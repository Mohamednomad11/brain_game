package com.nomad.mybrainmemory.adapter.gameadapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.game.ScoreAnimation;
import com.nomad.mybrainmemory.model.CardModel;
import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.play.CongratsScreen;
import com.nomad.mybrainmemory.util.TimerUtils;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardHolder> {
    private ArrayList<CardModel> mData;
    private ArrayList<EasyFlipView> flipCards;
    private ArrayList<String> names;
    GameModel gameModel;
    Context context;
    TextView gameScore, animScore;
    int totalCard;
    String fragment_round_num;
    FragmentManager fragment;
    ScoreAnimation scoreAnimation;

    TimerUtils timerUtils;

    boolean firstFlip = false;

    public CardAdapter(ArrayList<CardModel> mData, Context context, GameModel gameModel, TextView gameScore, TextView animScore, int totalCard, FragmentManager fragment, String fragment_round_num, TimerUtils timerUtils){
        this.mData = mData;
        this.context = context;
        this.gameModel = gameModel;
        this.gameScore = gameScore;
        this.animScore = animScore;
        this.totalCard = totalCard;
        this.fragment_round_num = fragment_round_num;
        this.fragment = fragment;
        flipCards = new ArrayList<>();
        names = new ArrayList<>();
        scoreAnimation = new ScoreAnimation();
        this.timerUtils = timerUtils;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        CardModel model = mData.get(position);
        holder.getBackImage().setImageResource(model.getBack_img());
        holder.getFrontImage().setImageResource(model.getFront_img());
        Handler handler = new Handler();
        gameLogic(holder.getEasyFlipView(), handler, model, scoreAnimation);
//        Log.e(" onBindViewHolder ---- Flip count",position + " " + getItemCount());
//        if(position == getItemCount()-1){
//            firstFlip = true;
//            Log.e("Flip count","First flip set to true");
//        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public ArrayList<CardModel> getmData(){
        return mData;
    }

    private long touchStartTime;
    private long touchEndTime;
    private int countSuccessfulPass = 0;
    private  int countFailedPass = 0;
    private long reactionTime = 0;
    private long reactionTimeSuccess = 0;
    private long totalReactionTime = 0;

    public void gameLogic(EasyFlipView flipView, Handler handler, CardModel model, ScoreAnimation scoreAnimation){
//        if(!firstFlip){
//            flipView.setFlipDuration(1000);
//            flipView.flipTheView(true);
//            Log.e("Flip anim","set to 1000");
//        }

        if(model.isTouchable()){
            flipView.setFlipOnTouch(true);
        }else{
            flipView.setFlipOnTouch(false);
        }

        flipView.setFlipDuration(1000);

        flipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                if(easyFlipView.isBackSide()){
                    flipCards.add(easyFlipView);
                    names.add(model.getName());
                    easyFlipView.setFlipOnTouch(false);

                    if(flipCards.size() == 1){
                        touchStartTime = System.currentTimeMillis();
                    }else if(flipCards.size() == 2){
                        touchEndTime = System.currentTimeMillis();
                    }





                } else {
                    easyFlipView.setFlipOnTouch(true);
                }

                if (flipCards.size() == 2) {

                    reactionTime = touchEndTime - touchStartTime;

                    totalReactionTime = totalReactionTime + reactionTime;


                    if(names.get(0).equals(names.get(1))){
                        countSuccessfulPass++;
                        reactionTimeSuccess = reactionTimeSuccess + reactionTime;
                        totalCard--;
                        int score =  10;

                        if(reactionTime > 10000){

                            score = 5;

                        }else if(reactionTime > 7000){

                            score = 10;

                        }else if(reactionTime > 3000){

                            score = 15;

                        }else{

                            score = 25;

                        }

                        scoreAnimation.animationScore(animScore, "+" + score);
                        gameModel.setScore(score);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (EasyFlipView view : flipCards) {
                                    view.setFlipEnabled(false);
                                }
                                flipCards.clear();
                                names.clear();
                            }
                        }, 200);
                    } else {
//                        scoreAnimation.animationScore(animScore, "-5");
//                        gameModel.setScore(-5);
                        countFailedPass ++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (EasyFlipView view : flipCards) {
                                    view.flipTheView();
                                }
                                flipCards.clear();
                                names.clear();
                            }
                        }, 200);
                    }

                }
                scoreAnimation.delaySetText(gameScore, String.valueOf(gameModel.getScore()));
                //gameScore.setText(String.valueOf(gameModel.getScore()));

                if(totalCard == 0){

                    long averageReactionTime = (totalReactionTime/(countSuccessfulPass +countFailedPass));
                    float accuracy = (float) countSuccessfulPass /(countSuccessfulPass +countFailedPass);
                    long avrgReactionTimeSuccess = reactionTimeSuccess/4;

                    Log.e("Card Adapter","averageReactionTime " + averageReactionTime + "----" +  "accuracy  " + accuracy  + "averageReactionTimeSuccess " + avrgReactionTimeSuccess);

                    gameModel.setAccuracy(accuracy);
                    gameModel.setAverageReactionTime(averageReactionTime);
                    gameModel.setAverageSuccessReactionTime(avrgReactionTimeSuccess);


                    gameModel.setTimeSpent(timerUtils.getTimeSpent());
                    timerUtils.finishTimer();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(fragment_round_num.equals("Round 1")){


                                gameModel.updateLevelStatus(1,1);
//                                InfoBox infoBox = new InfoBox();
//                                infoBox.addNameScore(context, String.valueOf(gameModel.getScore()),String.valueOf(gameModel.getTimeSpent()), StaticConstants.GAME_MATCHING);
                                fragment.beginTransaction().replace(R.id.fragment_container, new CongratsScreen(gameModel, "Round 1")).commit();
                            }else  if(fragment_round_num.equals("Round 2")){


                                gameModel.updateLevelStatus(2,1);
//                                InfoBox infoBox = new InfoBox();
//                                infoBox.addNameScore(context, String.valueOf(gameModel.getScore()),String.valueOf(gameModel.getTimeSpent()), StaticConstants.GAME_MATCHING);
                                fragment.beginTransaction().replace(R.id.fragment_container, new CongratsScreen(gameModel, "Round 2")).commit();
                            } else if(fragment_round_num.equals("Round 3")){


                                gameModel.updateLevelStatus(3,1);
//                                InfoBox infoBox = new InfoBox();
//                                infoBox.addNameScore(context, String.valueOf(gameModel.getScore()),String.valueOf(gameModel.getTimeSpent()), StaticConstants.GAME_MATCHING);
                                fragment.beginTransaction().replace(R.id.fragment_container, new CongratsScreen(gameModel, "Round 3")).commit();
                            }
                        }
                    }, 800);
                }
            }
        });
    }
}
