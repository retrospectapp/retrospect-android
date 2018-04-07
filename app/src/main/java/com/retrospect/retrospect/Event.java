package com.retrospect.retrospect;


import java.util.ArrayList;


/**
 * Created by Shivam on 1/19/2018.
 *
 */

@SuppressWarnings("unused")
public class Event{

    private String title;
    private String date;
    private String details;
    private String location;
    private ArrayList<Connection> peopleInvolved;
    private ArrayList<String> images;
    private boolean isSelected = false;

    public Event(){}

    public Event(String title, String date, String details, String location, ArrayList<Connection> peopleInvolved, ArrayList<String> images){
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

    public ArrayList<Connection> getPeopleInvolved(){
        return peopleInvolved;
    }

    public void setPeopleInvolved(ArrayList<Connection> peopleInvolved){ this.peopleInvolved = peopleInvolved; }

    public ArrayList<String> getImages(){
        return images;
    }

    public void setImages(ArrayList<String> images){
        this.images = images;
    }

    public boolean isSelected(){
        return isSelected;
    }

    public void setIsSelected(boolean bool){
        isSelected = isSelected;
    }

    public String toString(){
        String images = "";
        return "Title: " + title + "\n" + "Date: " + date + "\n" + "Details: " + details + "\n" + "Image URL:" + images + "\n";
    }

}