package com.retrospect.retrospect;

import java.util.HashMap;

/**
 * Created by Shivam on 1/19/2018.
 *
 */

@SuppressWarnings("unused")
public class Event {

    private String title;
    private String date;
    private String details;
    private String location;
    private HashMap<String, String[]>peopleInvolved;
    private HashMap<String, String>images;

    public Event(){}

    public Event(String title, String date, String details, String location, HashMap<String, String[]> peopleInvolved, HashMap<String, String> images){
        this.title = title;
        this.date = date;
        this.details = details;
        this.location = location;
        this.peopleInvolved = peopleInvolved;
        this.images = images;
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

    public String getDetails(){
        return details;
    }

    public void setDetails(String details){
        this.details = details;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public HashMap<String, String[]> getPeopleInvolved(){
        return peopleInvolved;
    }

    public void setPeopleInvolved(HashMap<String, String[]> peopleInvolved){ this.peopleInvolved = peopleInvolved; }

    public HashMap<String, String> getImages(){
        return images;
    }

    public void setImage(HashMap<String, String> images){
        this.images = images;
    }

    public String toString(){
        String images = "";
        return "Title: " + title + "\n" + "Date: " + date + "\n" + "Details: " + details + "\n" + "Image URL:" + images + "\n";
    }

}
