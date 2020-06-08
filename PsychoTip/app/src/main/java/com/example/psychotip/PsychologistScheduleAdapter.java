package com.example.psychotip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class PsychologistScheduleAdapter extends
        RecyclerView.Adapter<PsychologistScheduleAdapter.ExampleViewHolder> {
    private ArrayList<PsychologistScheduleData> psychologistScheduleList;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView psychologistScheduleStatus;
        public TextView psychologistScheduleTime;
        public ImageView deleteImage;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            psychologistScheduleStatus = itemView.findViewById(R.id.textView);
            psychologistScheduleTime = itemView.findViewById(R.id.textView2);
            deleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public PsychologistScheduleAdapter(
            ArrayList<PsychologistScheduleData> psychologistScheduleList) {
        this.psychologistScheduleList = psychologistScheduleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_psychologist_schedule, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, clickListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        PsychologistScheduleData currentItem = psychologistScheduleList.get(position);

        holder.psychologistScheduleStatus.setText(currentItem.getScheduleStatus());
        holder.psychologistScheduleTime.setText(currentItem.getScheduleTime());
    }

    @Override
    public int getItemCount() {
        return psychologistScheduleList.size();
    }
}