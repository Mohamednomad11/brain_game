package com.nomad.mybrainmemory;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
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
        holder.buttonGenerateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PerformanceReport.class);
                ScoreModel userReportMpdel = userReports.get(holder.getAdapterPosition());
                i.putExtra(StaticConstants.KEY_SCORE_REPORT,userReportMpdel);
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

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            int itemPos = getAdapterPosition();
            Context context = itemView.getContext();
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

