package com.nomad.mybrainmemory.game;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.model.GameModel;
import com.nomad.mybrainmemory.play.CongratsScreen;
import com.nomad.mybrainmemory.play.RoundOne;
import com.nomad.mybrainmemory.play.RoundThree;
import com.nomad.mybrainmemory.play.RoundTwo;
import com.nomad.mybrainmemory.util.TimerUtils;

public class InfoBox {

    private AlertDialog alertDialog;
    private AlertDialog alertDialogPause;
    ScoreDB scoreDB;

    public void infoBox(Context context){
        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the message show for the Alert time
        builder.setMessage("A image matching game fro memory exercise");

        // Set Alert Title
        builder.setTitle("Animal Match Up");

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click yes button then app will close
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
    }

    // Create the dialog only once
    private void createDialog(Context context, String score, String time, String game) {
        // Create a custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        TextInputLayout editText = dialogView.findViewById(R.id.enter_name_txt);
        TextView alertScore = dialogView.findViewById(R.id.alert_score);
        Button button = dialogView.findViewById(R.id.ok_button);

        final MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(context)
                .setView(dialogView)
                .setCancelable(false);

        alertScore.setText("Your Score: " + score);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getEditText().getText().toString().trim();
                scoreDB = new ScoreDB(context);

                if(name.isEmpty() || name.length() > 10 || name.contains(" ")){
                    editText.setError("Please follow the instructions");
                } else {
                    scoreDB.addScore(name, score,time,game);
                    Toast.makeText(context, "Successfully Saved", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog = dialogBuilder.create();

    }

    public void createPauseDialog(Context context, TimerUtils timerUtils, Fragment parentFragment, GameModel gameModel, String roundName) {
        // Create a custom layout for the dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_layout_pause, null);
        Button resumeButton = dialogView.findViewById(R.id.resume_btn);
        Button restartButton = dialogView.findViewById(R.id.restart_btn);
        Button changeLevelButton = dialogView.findViewById(R.id.change_btn);
        Button quitButton = dialogView.findViewById(R.id.quit_button);

        final MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(context)
                .setView(dialogView)
                .setCancelable(false);


        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                timerUtils.resumeTimer();
            }
        });

        changeLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                parentFragment.getActivity().finish();
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                timerUtils.pauseTimer();
                parentFragment.getActivity().finish();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
//                parentFragment.getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundOne(gameModel)).commit();
                if(roundName.equals("Round 1")){
                    parentFragment.getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundOne(gameModel)).commit();
                }else  if(roundName.equals("Round 2")){
                    parentFragment.getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundTwo(gameModel)).commit();
                } else if(roundName.equals("Round 3")){
                    parentFragment.getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new RoundThree(gameModel)).commit();
                }
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void addNameScore(Context context, String score, String time, String game) {
        // Create the dialog if it doesn't exist
        if (alertDialog == null) {
            createDialog(context, score,time,game);
        }

        // Show the dialog
        alertDialog.show();
    }




}
