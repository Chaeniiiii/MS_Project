package com.course.ms_project;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.LocaleData;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CreatingDiet extends AppCompatActivity {

    private GoogleMap mMap;
    private Geocoder geocoder;
    SupportMapFragment map;

    ImageView F_image;  // 음식 사진
    ImageButton N_btn; // 사진 넣기 위한 버튼

    ImageButton T_btn; // timepickerdialog 띄우기 위한 버튼
    EditText F_time; // 시간이 표시되는 텍스트

    EditText F_name; //음식 이름 적기 위한 텍스트
    EditText F_review; // 리뷰 적기 위한 텍스트
    EditText F_count; //수량 적기 위한 텍스트
    EditText F_position;
    EditText F_cal;

    String img_dw;
    ImageView M_map;
    ImageButton M_btn2;

    String lati;
    String longi;

    String id;
    String Food_date;

    DatabaseReference mDatabase;

    int count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras=getIntent().getExtras();
        Food_date=extras.getString("food_date");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_diet);

        F_image=findViewById(R.id.F_image);
        N_btn=findViewById(R.id.N_btn);

        T_btn=findViewById(R.id.T_btn);
        F_time=findViewById(R.id.F_time);

        F_name=findViewById(R.id.F_name);
        F_review=findViewById(R.id.F_review);
        F_count=findViewById(R.id.F_count);
        F_position=findViewById(R.id.F_position);
        F_cal = findViewById(R.id.F_cal);

        M_btn2=findViewById(R.id.M_btn2);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                geocoder = new Geocoder(getBaseContext(), Locale.getDefault());


                LatLng seoul = new LatLng(37.555744, 126.970431);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng point) {
                        MarkerOptions mOptions = new MarkerOptions();
                        // 마커 타이틀
                        mOptions.title("마커 좌표");
                        Double latitude = point.latitude; // 위도
                        Double longitude = point.longitude; // 경도
                        // 마커의 스니펫(간단한 텍스트) 설정
                        mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                        // LatLng: 위도 경도 쌍을 나타냄
                        mOptions.position(new LatLng(latitude, longitude));
                        // 마커(핀) 추가
                        googleMap.addMarker(mOptions);
                    }
                });

            }
        });

        M_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = F_position.getText().toString();

                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(str, 20); // 최대 검색 결과 개수

                } catch (IOException e) {
                    Toast toast=Toast.makeText(getApplicationContext(), "정확한 장소를 입력해주세요.", Toast.LENGTH_LONG);
                    toast.show();
                } catch (IndexOutOfBoundsException e) {

                    Toast toast=Toast.makeText(getApplicationContext(), "정확한 장소를 입력해주세요.", Toast.LENGTH_LONG);
                    toast.show();
                }

                if(str.length()!=0 && addressList.size() != 0 && addressList!=null ) {
                    System.out.println(addressList.get(0).toString());
                    // 콤마를 기준으로 split
                    String[] splitStr = addressList.get(0).toString().split(",");
                    String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
                    System.out.println(address);

                    String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                    String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                    System.out.println(latitude);
                    System.out.println(longitude);

                    lati= latitude;
                    longi= longitude;

                    // 좌표(위도, 경도) 생성
                    LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    // 마커 생성
                    MarkerOptions mOptions2 = new MarkerOptions();
                    mOptions2.title(str);
                    mOptions2.snippet(address);
                    mOptions2.position(point);
                    // 마커 추가
                    mMap.addMarker(mOptions2);
                    // 해당 좌표로 화면 줌
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 18));
                }
                else{
                    Toast toast=Toast.makeText(getApplicationContext(), "정확한 장소를 입력해주세요.", Toast.LENGTH_LONG);
                    toast.show();

                }
            }
        });

    }

    //칼로리 조회 버튼
    public void mOnPopupClick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopupActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //데이터 받기
                String result = data.getStringExtra("calorie");
                F_cal.setText(result);
            }
        }
    }


    //사진 넣기 버튼을 눌렀을 시 작업
    public void click_food_name_btn(View view){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityResult.launch(intent);

    }

    //갤러리에 들어가서 사진을 가져오기 위한 함수
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {


                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();



                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            F_image.setImageBitmap(bitmap);
                            N_btn.setVisibility(View.GONE);

                            img_dw= String.valueOf(uri);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


    // T_btn 눌렀을시 작업
    public void click_time_btn(View view){


        showTime();

    }

    //dialog 띄우기 위한한
    void showTime(){

        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                int hour=hourOfDay;
                int min=minute;

                F_time.setText(hour + "시"+"   "+min +"분");
            }
        }, 21,12,true);

        timePickerDialog.show();

    }

    public void writeNewUser(String F_id,String F_date,String F_name,String F_image,String F_time,String F_count,String F_cal, String F_review, String P_latitude, String P_longitude ) {
        User user = new User(F_id,F_date,F_image, F_name,F_time,F_count,F_cal,F_review,P_latitude,P_longitude);


        mDatabase.child("users").child(F_id).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(CreatingDiet.this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        Toast.makeText(CreatingDiet.this, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    // 식단 데이터를 저장하여 넘기기 위한 함수
    public void addFood(View view){

        HashMap userDiet = new HashMap<>();

        String U_F_claorie=F_cal.getText().toString();
        String U_F_name= F_name.getText().toString();
        String U_F_time=F_time.getText().toString();
        String U_F_count=F_count.getText().toString();
        String U_F_review=F_review.getText().toString();

        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(Integer.toString(count))){
                    count++;
                }
                else{
                    userDiet.put("f_id",Integer.toString(count));
                    userDiet.put("f_date",Food_date);
                    userDiet.put("f_image",img_dw);
                    userDiet.put("f_cal",F_cal.getText().toString());
                    userDiet.put("f_name",F_name.getText().toString());
                    userDiet.put("f_time",F_time.getText().toString());
                    userDiet.put("F_count",F_count.getText().toString());
                    userDiet.put("F_review",F_review.getText().toString());
                    userDiet.put("P_latitude",lati.toString());
                    userDiet.put("P_longitude",longi.toString());

                    writeNewUser(Integer.toString(count++),Food_date,U_F_name,img_dw,U_F_time,U_F_count,U_F_claorie,U_F_review,lati,longi);
                    Intent intent = new Intent(CreatingDiet.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "잘못된 접근입니다.", Toast.LENGTH_LONG).show();
            }
        });




    }

}
