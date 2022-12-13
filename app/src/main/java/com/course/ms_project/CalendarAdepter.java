package com.course.ms_project;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdepter extends RecyclerView.Adapter<CalendarAdepter.CalendarViewHolder>{

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position, LocalDate day);
    }

    private OnListItemSelectedInterface mListener;

    ArrayList<LocalDate> daylist;
    int selectedPosition = -1;
    RecyclerView recyclerView;

    public CalendarAdepter(ArrayList<LocalDate> daylist , OnListItemSelectedInterface listener) {
        this.daylist = daylist;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);

        return new CalendarViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //날짜 변수에 담기
        LocalDate day = daylist.get(position);

        if(day == null){
            holder.daytext.setText("");
        }
        else{
            holder.daytext.setText(String.valueOf(day.getDayOfMonth()));

            if(day.equals(CalrendarUtil.selectedDate)){
                holder.daytext.setTextColor(Color.parseColor("#FFFCAA"));
            }

            if(selectedPosition == position){
                holder.parentView.setBackgroundResource(R.drawable.select_background);
            }
            else{
                if (! day.equals(CalrendarUtil.selectedDate)) holder.parentView.setBackgroundColor(Color.BLACK);
            }
        }
    }

    @Override
    public int getItemCount() {
        return daylist.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        TextView daytext;
        View parentView;

        public CalendarViewHolder(@NonNull View itemView){
            super(itemView);

            daytext = itemView.findViewById(R.id.dayText);
            parentView = itemView.findViewById(R.id.parentView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        int position = getAdapterPosition();
                        LocalDate day = daylist.get(position);
                        mListener.onItemSelected(v, getAdapterPosition(), day);
                        selectedPosition = position;
                    notifyDataSetChanged();
                }
            });
        }
    }
}