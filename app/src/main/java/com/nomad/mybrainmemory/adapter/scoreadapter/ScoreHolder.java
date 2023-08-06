package com.nomad.mybrainmemory.adapter.scoreadapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nomad.mybrainmemory.R;


public class ScoreHolder extends RecyclerView.ViewHolder {
    private TextView rank, name, score,time;

    public ScoreHolder(@NonNull View itemView) {
        super(itemView);
        rank = itemView.findViewById(R.id.rank_txt);
        name = itemView.findViewById(R.id.name_txt);
        score = itemView.findViewById(R.id.score_txt);
        time = itemView.findViewById(R.id.time_txt);
    }

    public TextView getRank() {
        return rank;
    }


    public TextView getName() {
        return name;
    }

    public TextView getScore() {
        return score;
    }

    public TextView getTime() {
        return time;
    }
}
