package com.course.ms_project;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    ArrayList<Item> items = new ArrayList<Item>();

    static String item_name;
    static String item_id;
    static String item_count;
    static String item_date;
    static String item_image;
    static String item_review;
    static String item_time;
    static String item_latitude;
    static String item_longitude;
    static String item_cal;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_layout, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Item item = items.get(position);
        viewHolder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void addItem(int position, Item item){
        items.add(position, item);
    }

    public void removeItem(int position){
        items.remove(position);
    }

    public void removeAllItem(){
        items.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title_view;
        TextView description;
        TextView cal_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cal_text=itemView.findViewById(R.id.cal_text);
            title_view = itemView.findViewById(R.id.title_text);
            description = itemView.findViewById(R.id.desc_text);

        }

        public void setItem(Item item){
            cal_text.setText(item.getCalorie()+" kcal");
            title_view.setText(item.getTitle());
            description.setText(item.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DatabaseReference list_food= FirebaseDatabase.getInstance().getReference();

                    list_food.child("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            item_cal=snapshot.child(item.description).child("f_calorie").getValue().toString();
                            item_name=snapshot.child(item.description).child("f_name").getValue().toString();
                            item_count=snapshot.child(item.description).child("f_count").getValue().toString();
                            item_id=snapshot.child(item.description).child("f_id").getValue().toString();
                            item_date=snapshot.child(item.description).child("f_date").getValue().toString();
                            item_review=snapshot.child(item.description).child("f_review").getValue().toString();
                            item_image=snapshot.child(item.description).child("f_image").getValue().toString();
                            item_latitude=snapshot.child(item.description).child("p_latitude").getValue().toString();
                            item_longitude=snapshot.child(item.description).child("p_longitude").getValue().toString();
                            item_time=snapshot.child(item.description).child("f_time").getValue().toString();


                            Context context=view.getContext();
                            Intent intent = new Intent(view.getContext(),STORE.class);
                            intent.putExtra("item_cal",item_cal);
                            intent.putExtra("item_name",item_name);
                            intent.putExtra("item_count",item_count);
                            intent.putExtra("item_date",item_date);
                            intent.putExtra("item_id",item_id);
                            intent.putExtra("item_review",item_review);
                            intent.putExtra("item_image",item_image);
                            intent.putExtra("item_time",item_time);
                            intent.putExtra("item_latitude",item_latitude);
                            intent.putExtra("item_longitude",item_longitude);

                            context.startActivity(intent);


                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println(" ");
                        }



                    });



                }
            });

        }
    }



}
