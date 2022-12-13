package com.course.ms_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CalorieAdapter extends ArrayAdapter<Calorie> {
    public CalorieAdapter(Context context, int resource, List<Calorie> foodList){
        super(context, resource, foodList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Calorie calorie = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_cell, parent, false);
        }
        TextView tv1 = convertView.findViewById(R.id.food_name);
        TextView tv2 = convertView.findViewById(R.id.food_cal);

        tv1.setText(calorie.getName());
        tv2.setText(calorie.getCal()+"kcal");

        return convertView;
    }
}
