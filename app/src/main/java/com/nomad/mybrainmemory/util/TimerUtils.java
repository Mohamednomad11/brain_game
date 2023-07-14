package com.nomad.mybrainmemory.util;

import android.os.CountDownTimer;
import android.widget.TextView;

public class TimerUtils {

    public static void startTimer(TextView textView) {
        // Set the total time in milliseconds (120 seconds = 120,000 milliseconds)
        long totalTimeInMillis = 120000;

        // Create a new CountDownTimer
        CountDownTimer countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {

            // This method will be invoked on each tick of the timer
            @Override
            public void onTick(long millisUntilFinished) {
                // Calculate the seconds remaining
                long secondsRemaining = millisUntilFinished / 1000;

                // Update the TextView with the remaining time
                textView.setText("Timer: " + secondsRemaining + " s");
            }

            // This method will be invoked when the timer finishes (120 seconds)
            @Override
            public void onFinish() {
                // Update the TextView when the timer finishes
                textView.setText("Timer finished!");
            }
        };

        // Start the timer
        countDownTimer.start();
    }
}

