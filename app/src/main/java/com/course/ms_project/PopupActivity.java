package com.course.ms_project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class PopupActivity extends Activity {

    public static ArrayList<Calorie> calorieList = new ArrayList<Calorie>();

    ListView listiView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_activity);

        searchCal();
        setUpData();
        setUpList();
        setUpOnClickListener();
    }

    private void searchCal(){
        SearchView searchView = findViewById(R.id.food_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Calorie> filterCalorie = new ArrayList<>();
                for (int i = 0; i < calorieList.size(); i++){
                    Calorie calorie = calorieList.get(i);

                    if(calorie.getName().toLowerCase().contains(newText.toLowerCase())){
                        filterCalorie.add(calorie);
                    }
                }
                CalorieAdapter adapter = new CalorieAdapter(getApplicationContext(), 0, filterCalorie);
                listiView.setAdapter(adapter);

                return false;
            }
        });
    }

    private void setUpData(){
        calorieList.clear();

        Calorie food0 = new Calorie("0","쌀밥", "300");
        calorieList.add(food0);

        Calorie food1 = new Calorie("1","된장찌개", "128");
        calorieList.add(food1);

        Calorie food2 = new Calorie("2","김치찌개", "209");
        calorieList.add(food2);

        Calorie food3 = new Calorie("3","순두부찌개", "115");
        calorieList.add(food3);

        Calorie food4 = new Calorie("4","부대찌개", "340");
        calorieList.add(food4);

        Calorie food5 = new Calorie("5","해물탕", "82");
        calorieList.add(food5);

        Calorie food6 = new Calorie("6","콩나물국", "15");
        calorieList.add(food6);

        Calorie food7 = new Calorie("7","갈비찜", "220");
        calorieList.add(food7);

        Calorie food8 = new Calorie("8","갈비탕", "630");
        calorieList.add(food8);

        Calorie food9 = new Calorie("9","칼국수", "545");
        calorieList.add(food9);

        Calorie food10 = new Calorie("10","물냉면", "520");
        calorieList.add(food10);

        Calorie food11 = new Calorie("11","비빔냉면", "578");
        calorieList.add(food11);

        Calorie food12 = new Calorie("12","라면", "450");
        calorieList.add(food12);

        Calorie food13 = new Calorie("13","떡볶이", "482");
        calorieList.add(food13);

        Calorie food14 = new Calorie("14","낙지볶음", "106");
        calorieList.add(food14);

        Calorie food15 = new Calorie("15","비빔밥", "500");
        calorieList.add(food15);

        Calorie food16 = new Calorie("16","후라이드치킨", "1680");
        calorieList.add(food16);

        Calorie food17 = new Calorie("17","양념치킨", "1954");
        calorieList.add(food17);

        Calorie food18 = new Calorie("18","햄버거", "1437");
        calorieList.add(food18);

        Calorie food19 = new Calorie("19","피자", "2763");
        calorieList.add(food19);

    }

    private void setUpList(){
        listiView = findViewById(R.id.listView);

        CalorieAdapter adapter = new CalorieAdapter(getApplicationContext(), 0, calorieList);
        listiView.setAdapter(adapter);
    }

    private void setUpOnClickListener() {
        listiView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Calorie selectFood = (Calorie) listiView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra("calorie", selectFood.getCal());
                setResult(RESULT_OK,intent);

                finish();
            }
        });
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}


