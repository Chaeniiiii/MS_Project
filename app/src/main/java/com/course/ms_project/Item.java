package com.course.ms_project;

public class Item {
    String title;
    String description;
    String calorie;

    public String getCalorie(){
        return calorie;
    }
    public void setCalorie(String calorie){
        this.calorie=calorie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
