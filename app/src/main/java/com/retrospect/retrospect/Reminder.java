package com.example.shivamdave.retrospecttest;

/**
 * Created by Shivam on 1/19/2018.
 */

public class Reminder {

    private String title;
    private String date;
    private String time;
    private String period;
    private String details;

    public Reminder(){}

    public Reminder(String title, String date, String time, String period, String details){
        this.title = title;
        this.date = date;
        this.time = time;
        this.period = period;
        this.details = details;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getPeriod(){
        return period;
    }

    public void setPeriod(String period){
        this.period = period;
    }

    public String getDetails(){
        return details;
    }

    public void setDetails(String details){
        this.details = details;
    }

    public String toString(){
        return "Title: " + title + "\n" + "Date: " + date + "\n" + "Time: " + time + "\n" + "Period: " + period + "\n" + "Details: " + details + "\n";
    }

}
