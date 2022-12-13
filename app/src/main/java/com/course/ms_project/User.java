package com.course.ms_project;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class User {

    String F_id;
    String F_date;
    String F_image;
    String F_name;
    String F_time;
    String F_count;
    String F_calorie;
    String F_review;
    String P_latitude;
    String P_longitude;


    public User(String F_id,String F_date ,String F_image,String F_name,String F_time,String F_count,String F_calorie, String F_review, String P_latitude, String P_longitude ){

        this.F_id=F_id;
        this.F_date=F_date;
        this.F_image=F_image;
        this.F_name=F_name;
        this.F_time=F_time;
        this.F_count=F_count;
        this.F_calorie=F_calorie;
        this.F_review=F_review;
        this.P_latitude=P_latitude;
        this.P_longitude=P_longitude;

    }

    public String getF_calorie(){
        return F_calorie;
    }
    public String getF_id(){
        return F_id;
    }


    public String getF_date(){
        return F_date;
    }
    public  String getF_image(){
        return F_image;
    }

    public  String getF_name(){
        return F_name;
    }

    public String getF_time(){
        return F_time;
    }

    public String getF_count(){
        return F_count;
    }

    public String getF_review(){
        return F_review;
    }

    public String getP_latitude(){
        return P_latitude;
    }

    public String getP_longitude(){
        return P_longitude;
    }

}
