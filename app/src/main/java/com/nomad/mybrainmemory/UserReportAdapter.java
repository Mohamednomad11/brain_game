package com.nomad.mybrainmemory;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
        import androidx.annotation.NonNull;

import com.nomad.mybrainmemory.model.ScoreModel;
import com.nomad.mybrainmemory.util.StaticConstants;

import java.util.List;

public class UserReportAdapter extends RecyclerView.Adapter<UserReportAdapter.UserViewHolder> {

    private Context context;
    private List<ScoreModel> userReports;

    public UserReportAdapter(Context context, List<ScoreModel> users) {
        this.context = context;
        this.userReports = users;
    }

    public void setUserReports(List<ScoreModel> userReports) {
        this.userReports = userReports;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_report, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        String userName = userReports.get(position).getName();
        holder.textViewUserName.setText(userName);
        holder.buttonGenerateView.setText("GENERATE AVERAGE REPORT");
        holder.buttonGenerateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PerformanceReport.class);
                ScoreModel userReportModel = userReports.get(holder.getAdapterPosition());
                Log.e("User Report Adapter", userReportModel.toString());
                i.putExtra(StaticConstants.KEY_SCORE_REPORT,userReportModel);
                context.startActivity(i);
            }
        });

       holder.ll_item_card_holder.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(context, ScoreBoardScreenAdmin.class);
               ScoreModel userReportModel = userReports.get(holder.getAdapterPosition());
               Log.e("User Report Adapter", userReportModel.toString());
               i.putExtra(StaticConstants.KEY_SCORE_REPORT,userReportModel);
               context.startActivity(i);
           }
       });
    }

    @Override
    public int getItemCount() {
        return userReports.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUserName;
        Button buttonGenerateView;

        LinearLayout ll_item_card_holder;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            int itemPos = getAdapterPosition();
            Context context = itemView.getContext();
            ll_item_card_holder = itemView.findViewById(R.id.ll_user_report_holder);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            buttonGenerateView = itemView.findViewById(R.id.btn_generate_report);
//            buttonGenerateView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(context, PerformanceReport.class);
//                    context.startActivity(i);
//                }
//            });
        }
    }
}

