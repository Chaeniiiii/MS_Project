package com.course.ms_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class STORE extends AppCompatActivity {
    private GoogleMap mMap2;
    SupportMapFragment map2;

    TextView S_F_name;
    TextView S_F_time;
    TextView S_F_count;
    TextView S_F_review;
    TextView S_F_cal;
    ImageView S_F_image;

    ImageButton imageButton;
    Double lati;
    Double longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        imageButton=findViewById(R.id.imageButton);
        S_F_name =findViewById(R.id.S_F_name);
        S_F_time =findViewById(R.id.S_F_time);
        S_F_count =findViewById(R.id.S_F_count);
        S_F_review =findViewById(R.id.S_F_review);
        S_F_cal=findViewById(R.id.calorie);
        S_F_image =findViewById(R.id.S_F_image);


        Bundle extras=getIntent().getExtras();

        String F_cal=(extras.getString("item_cal"));
        String F_name = (extras.getString("item_name"));
        String F_count = (extras.getString("item_count"));
        String F_date = (extras.getString("item_date"));
        String F_id = (extras.getString("item_id"));
        String F_review = (extras.getString("item_review"));
        String F_image = (extras.getString("item_image"));
        String F_time = (extras.getString("item_time"));
        lati = Double.parseDouble(extras.getString("item_latitude"));
        longi = Double.parseDouble(extras.getString("item_longitude"));

        S_F_cal.setText(F_cal);
        S_F_image.setImageURI(Uri.parse(F_image));
        S_F_name.setText(F_name);
        S_F_time.setText(F_time);
        S_F_count.setText(F_count+" 개");
        S_F_review.setText(F_review);

        map2 = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        map2.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap2) {

                mMap2=googleMap2;

                LatLng f_position = new LatLng(lati, longi);
                mMap2.addMarker(new MarkerOptions().position(f_position).title("식사 장소"));
                mMap2.getUiSettings().setZoomGesturesEnabled(true);
                mMap2.moveCamera(CameraUpdateFactory.newLatLng(f_position));
                mMap2.animateCamera(CameraUpdateFactory.zoomTo(18));

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=view.getContext();
                Intent intent =new Intent(view.getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}