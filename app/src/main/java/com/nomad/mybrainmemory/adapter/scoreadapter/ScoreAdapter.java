package com.nomad.mybrainmemory.adapter.scoreadapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.nomad.mybrainmemory.PerformanceReport;
import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.jigsawpuzzle.puzzle.PuzzleActivity;
import com.nomad.mybrainmemory.model.ScoreModel;
import com.nomad.mybrainmemory.util.StaticConstants;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreHolder> {
    private ArrayList<ScoreModel> scoreModels;

    private Context context;

    public ScoreAdapter(Context context,ArrayList<ScoreModel> scoreModels){
        this.scoreModels = scoreModels;
        this.context = context;
        Log.e("Score Adapter", " "+scoreModels.size());
    }

    @NonNull
    @Override
    public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_card, parent, false);
        return new ScoreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreHolder holder, int position) {
        ScoreModel scoreModel = scoreModels.get(position);
        Log.e("Score Model",scoreModel.toString());

        // Convert Firebase Timestamp to LocalDateTime
        // Convert Firebase Timestamp to java.util.Date
        long seconds = scoreModel.getTimeStamp().getSeconds();
        long nanoseconds = scoreModel.getTimeStamp().getNanoseconds();

        long milliseconds = (seconds * 1000) + (nanoseconds / 1000000);
        Date date = new Date(milliseconds);

        // Format the Date to obtain date and time strings
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTimeString = dateFormat.format(date);

        holder.getName().setText(dateTimeString);
        holder.getScore().setText(String.valueOf(scoreModel.getScore()));
        holder.getRank().setText(String.valueOf(position + 1));
        holder.getTime().setText(String.valueOf(scoreModel.getTime()));



        holder.getScoreCard().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PerformanceReport.class);
                i.putExtra(StaticConstants.KEY_SCORE_REPORT,scoreModel);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return scoreModels.size();
    }
}
