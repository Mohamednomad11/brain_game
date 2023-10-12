package com.nomad.mybrainmemory.util;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.jigsawpuzzle.puzzle.PuzzleActivity;
import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.play.CongratsScreen;
import com.nomad.mybrainmemory.play.CongratsScreenActivity;
import com.nomad.mybrainmemory.play.FailedScreen;
import com.nomad.mybrainmemory.play.RoundOne;

public class TimerUtils {
    private long timeLeftInMillis;

    private boolean isTimerRunning;

    private long totalTimeInMillis;

    private TextView timerTextView;

    private CountDownTimer countDownTimer;

    private Fragment parentFragment;

    private String roundName;

    private Handler handler;

    private GameModel gameModel;

    private Context context;

    private TimerCallback timerCallback;

    public void setTimerCallback(TimerCallback timerCallback) {
        this.timerCallback = timerCallback;
    }

    public TimerUtils(long totalTimeInMillis, TextView timerTextView) {
        this.totalTimeInMillis = totalTimeInMillis;
        this.timerTextView = timerTextView;
        timeLeftInMillis = totalTimeInMillis;
        isTimerRunning = false;
        handler = new Handler();
    }

    public TimerUtils(long totalTimeInMillis, TextView timerTextView, String roundName, Fragment fragment, GameModel gameModel) {
        this.totalTimeInMillis = totalTimeInMillis;
        this.timerTextView = timerTextView;
        timeLeftInMillis = totalTimeInMillis;
        isTimerRunning = false;
        parentFragment = fragment;
        this.roundName = roundName;
        this.gameModel = gameModel;
        handler = new Handler();
    }

    public TimerUtils(long totalTimeInMillis, TextView timerTextView, String roundName, Context context, GameModel gameModel) {
        this.totalTimeInMillis = totalTimeInMillis;
        this.timerTextView = timerTextView;
        timeLeftInMillis = totalTimeInMillis;
        isTimerRunning = false;
        this.context = context;
        this.roundName = roundName;
        this.gameModel = gameModel;
        handler = new Handler();
    }

//    public  void startTimer(TextView textView) {
//        // Set the total time in milliseconds (120 seconds = 120,000 milliseconds)
//
//
//        // Create a new CountDownTimer
//        CountDownTimer countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {
//
//            // This method will be invoked on each tick of the timer
//            @Override
//            public void onTick(long millisUntilFinished) {
//                // Calculate the seconds remaining
//                long secondsRemaining = millisUntilFinished / 1000;
//
//                // Update the TextView with the remaining time
//                textView.setText("Timer: " + secondsRemaining + " s");
//            }
//
//            // This method will be invoked when the timer finishes (120 seconds)
//            @Override
//            public void onFinish() {
//                // Update the TextView when the timer finishes
//                textView.setText("Timer finished!");
//            }
//        };
//
//        // Start the timer
//        countDownTimer.start();
//
//    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                // Timer finished, handle any required actions here
                isTimerRunning = false;
                updateTimer();

                if(context!=null){
//                  context. getSupportFragmentManager()
                }

                try {
                    parentFragment.getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new FailedScreen(gameModel, roundName)).commit();
                }catch (Exception e){
                    Log.e("Timer Utils",e.toString());

//                    Intent i = new Intent(PuzzleActivity.this, FailedScreen.class);
//                    i.putExtra(StaticConstants.KEY_GAME_SCORE,gameModel);
//                    startActivity(i);
//                    finish();
                }

                if(timerCallback!=null){
                    timerCallback.onTimerFinished();
                }



            }
        };

        countDownTimer.start();
        isTimerRunning = true;
    }

    public void pauseTimer() {
        if (isTimerRunning) {
            countDownTimer.cancel();
            isTimerRunning = false;
        }
    }

    public void resumeTimer() {
        if (!isTimerRunning) {
            startTimer();
        }
    }

    public  void finishTimer(){
            if (isTimerRunning) {
               countDownTimer.cancel();
            }
    }


    private void updateTimer() {
        int seconds = (int) (timeLeftInMillis / 1000);
        String timeLeftFormatted = String.format("%02d", seconds);
        timerTextView.setText(timeLeftFormatted);
    }

    public int getTimeLeftInSeconds() {
        return (int) (timeLeftInMillis / 1000);
    }

    public  int getTimeSpent(){

        long timeSpentMs = totalTimeInMillis - timeLeftInMillis;

        return (int) (timeSpentMs / 1000);
    }

    public void setTimeLeftInMillis(long timeLeftInMillis) {
        this.timeLeftInMillis = timeLeftInMillis;
    }


    public interface TimerCallback{

//        void onTimerPaused();
        void onTimerFinished();

    }
}

