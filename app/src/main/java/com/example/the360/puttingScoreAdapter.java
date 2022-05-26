package com.example.the360;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class puttingScoreAdapter extends RecyclerView.Adapter<puttingScoreAdapter.MyViewHolder> {

    Context context;

    ArrayList<puttingScore> list;

    public puttingScoreAdapter(Context context, ArrayList<puttingScore> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.scorerecyler,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        puttingScore puttingScore = list.get(position);
        holder.gameType.setText(puttingScore.getGameType());
        holder.score.setText(puttingScore.getScore());
        holder.location.setText(puttingScore.getLocation());
        holder.distance.setText(puttingScore.getDistance() + "m");



        if (puttingScore.getLocation().equals("Indoors")) {

            TableRow.LayoutParams hideParams = new TableRow.LayoutParams(
                    0,
                    100,
                    0);


            holder.windHeader.setVisibility(View.INVISIBLE);
            holder.wind.setVisibility(View.INVISIBLE);
            holder.windHeader.setLayoutParams(hideParams);
            holder.wind.setLayoutParams(hideParams);
        }

        if (puttingScore.getGameType().equals("Max Putts")) {
            holder.scoreHeader.setText("Made:");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView gameType, score, scoreHeader, location, windHeader, wind, distance, ts;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            gameType = itemView.findViewById(R.id.recyclerGameType);
            score = itemView.findViewById(R.id.recyclerScore);
            scoreHeader = itemView.findViewById(R.id.recyclerScoreHeader);
            location = itemView.findViewById(R.id.recyclerLocation);
            windHeader = itemView.findViewById(R.id.recyclerWindHeader);
            wind = itemView.findViewById(R.id.recyclerWind);
            distance = itemView.findViewById(R.id.recyclerDistance);

        }
    }


}
