package com.course.ms_project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Queue;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements CalendarAdepter.OnListItemSelectedInterface{
    TextView monthYearText;
    ItemAdapter itemAdapter;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    CardView cardView;
    TextView infoText;
    ImageButton insert_btn;
    String foodDate;
    TextView allcal;
    Double lati;
    Double longi;

    ArrayList f_cal_day=new ArrayList<>();
    ArrayList<Item> items = new ArrayList<Item>();
    DatabaseReference Food_db=FirebaseDatabase.getInstance().getReference();

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //초기화
        allcal=findViewById(R.id.all_cal);
        monthYearText = findViewById(R.id.monthYearText);
        ImageButton preBtn = findViewById(R.id.pre_btn);
        ImageButton nextBtn = findViewById(R.id.next_btn);
        recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView2 = findViewById(R.id.recyclerView2);
        cardView = findViewById(R.id.cardView);
        infoText = findViewById(R.id.info_text);
        insert_btn = findViewById(R.id.insert_btn);

        itemAdapter = new ItemAdapter();
        recyclerView2.setAdapter(itemAdapter);


        //현재 날짜
        CalrendarUtil.selectedDate = LocalDate.now();

        //화면 설정
        setMonthview();

        //이전 달 버튼 이벤트
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //-1한 월을 넣어준다.
                cardView.setVisibility(View.GONE);
                infoText.setVisibility(View.VISIBLE);
                CalrendarUtil.selectedDate = CalrendarUtil.selectedDate.minusMonths(1);
                setMonthview();
            }
        });

        //다음 달 버튼 이벤트
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //+1한 월을 넣어준다
                cardView.setVisibility(View.GONE);
                infoText.setVisibility(View.VISIBLE);
                CalrendarUtil.selectedDate = CalrendarUtil.selectedDate.plusMonths(1);
                setMonthview();
            }
        });

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreatingDiet.class);
                intent.putExtra("food_date",foodDate);
                startActivity(intent);
            }
        });
    }//onCreate

    //날짜 타입 설정
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String yearMonthFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월");

        return date.format(formatter);
    }

    //화면 설정
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthview(){
        //년월 텍스트뷰 셋팅
        monthYearText.setText(yearMonthFromDate(CalrendarUtil.selectedDate));

        //해당 월 날짜 가져오기
        ArrayList<LocalDate> daylist = daysInMonthArray(CalrendarUtil.selectedDate);

        //어댑터 데이터 적용
        CalendarAdepter adapter = new CalendarAdepter(daylist, this);

        //레이아웃 설정
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 7);

        //레이아웃 적용
        recyclerView1.setLayoutManager(manager);

        //어댑터 적용
        recyclerView1.setAdapter(adapter);
    }

    //날짜 생성
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<LocalDate> daysInMonthArray(LocalDate date){

        ArrayList<LocalDate> dayList = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(date);

        //해당 월의 마지막 날짜 가져오기
        int lastDay = yearMonth.lengthOfMonth();

        //해당 월의 첫 번째 날 가져오기
        LocalDate firstDay = CalrendarUtil.selectedDate.withDayOfMonth(1);

        //첫 번째 날 요일 가져오기
        int dayOfWeek = firstDay.getDayOfWeek().getValue();

        //날짜 생성
        for (int i = 1; i < 44; i++){
            if (i <= dayOfWeek || i > lastDay + dayOfWeek){
                dayList.add(null);
            }
            else{
                dayList.add(LocalDate.of(CalrendarUtil.selectedDate.getYear(), CalrendarUtil.selectedDate.getMonth(), i - dayOfWeek));
            }
        }
        return dayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemSelected(View v, int position, LocalDate day) {
        itemAdapter.removeAllItem();

        ArrayList user_list=new ArrayList<>();
        ArrayList f_list=new ArrayList<>();
        ArrayList<Integer> c_list=new ArrayList<>();

        foodDate = day.toString();



        cardView.setVisibility(View.VISIBLE);
        infoText.setVisibility(View.GONE);
        //itemAdapter.removeAllItem();


        Food_db.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()==0){
                    itemAdapter.notifyDataSetChanged();
                    recyclerView2.startLayoutAnimation();
                }
                else{

                    itemAdapter.notifyDataSetChanged();
                    recyclerView2.startLayoutAnimation();

                    for( int i=1; i < snapshot.getChildrenCount()+1; i++){
                        if(snapshot.child(Integer.toString(i)).child("f_date").getValue().equals(foodDate)){
                            f_list.add(snapshot.child(Integer.toString(i)).getValue());

                            Integer cal= Integer.parseInt((String) snapshot.child(Integer.toString(i)).child("f_calorie").getValue());
                            Integer count=Integer.parseInt((String) snapshot.child(Integer.toString(i)).child("f_count").getValue());

                            Integer to_all_cal= Integer.valueOf(cal*count);
                            String today_cal= String.valueOf(to_all_cal);
                            c_list.add(to_all_cal);
                            add_item(user_list , (String) snapshot.child(Integer.toString(i)).child("f_name").getValue(), today_cal,i);
                        }
                        else{
                            System.out.println(" ");
                        }
                    }
                    System.out.println(c_list+"ffffffffffffff");
                    int sum=0;
                    for(int num : c_list){
                        sum+=num;
                    }
                    allcal.setText(String.valueOf(sum)+" kcal");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(" ");
            }
        });

    }

    public void add_item(ArrayList userList,String foodName,String foodcalorie,Integer j){
        Item item=new Item();

       if(userList==null){
           item.setTitle(foodName);
           item.setCalorie(foodcalorie);
           item.setDescription(j.toString());

           itemAdapter.addItem(item);
           userList.add(j);
       }
       else {
           if(userList.contains(j)==false){

               item.setTitle(foodName);
               item.setCalorie(foodcalorie);
               item.setDescription(j.toString());

               itemAdapter.addItem(item);
               userList.add(j);
           }
       }
    }
}//MainActivity