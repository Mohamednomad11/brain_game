package com.nomad.mybrainmemory.adapter.scoreadapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.nomad.mybrainmemory.R;
import com.nomad.mybrainmemory.model.ScoreModel;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreHolder> {
    private ArrayList<ScoreModel> scoreModels;

    public ScoreAdapter(ArrayList<ScoreModel> scoreModels){
        this.scoreModels = scoreModels;
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
        holder.getName().setText(scoreModel.getName());
        holder.getScore().setText(String.valueOf(scoreModel.getScore()));
        holder.getRank().setText(String.valueOf(position + 1));
        holder.getTime().setText(String.valueOf(scoreModel.getTime()));
    }

    @Override
    public int getItemCount() {
        return scoreModels.size();
    }
}
