package com.retrospect.retrospect;

import java.util.ArrayList;

/**
 * Created by Shivam on 1/19/2018.
 *
 */

@SuppressWarnings("unused")
public class Reminder {

    private String title;
    private String date;
    private String time;
    private String period;
    private String details;
    private String jingleAudioURL;
    private ArrayList<String> imageURLs;

    public Reminder(){}

    public Reminder(String title, String date, String time, String period, String details, String jingleAudioURL, ArrayList<String> imageURLS){
        this.title = title;
        this.date = date;
        this.time = time;
        this.period = period;
        this.details = details;
        this.jingleAudioURL = jingleAudioURL;
        this.imageURLs = imageURLS;
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

    public String getJingleAudioURL() { return jingleAudioURL; }

    public void setJingleAudioURL(String jingleAudioURL) { this.jingleAudioURL = jingleAudioURL; }

    public ArrayList<String> getImageURLs() { return imageURLs; }

    public void setImageURLs(ArrayList<String> imageURLs) { this.imageURLs = imageURLs; }
    public String toString(){
        return "Title: " + title + "\n" + "Date: " + date + "\n" + "Time: " + time + "\n" + "Period: " + period + "\n" + "Details: " + details + "\n";
    }

}
