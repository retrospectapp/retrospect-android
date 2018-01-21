package com.retrospect.retrospect;

/**
 * Created by Shivam on 1/19/2018.
 */

public class Event {

    private String title;
    private String date;
    private String details;
    private String location;
    private String[] peopleInvolved;
    private String imageURL;

    public Event(){}

    public Event(String title, String date, String details, String location, String[] peopleInvolved, String imageURL){
        this.title = title;
        this.date = date;
        this.details = details;
        this.location = location;
        this.peopleInvolved = peopleInvolved;
        this.imageURL = imageURL;
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

    public String[] getPeopleInvolved(){
        return peopleInvolved;
    }

    public void setPeopleInvolved(String[] peopleInvolved){
        this.peopleInvolved = peopleInvolved;
    }

    public String getImageURL(){
        return imageURL;
    }

    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }

    public String toString(){
        return "Title: " + title + "\n" + "Date: " + date + "\n" + "Details: " + details + "\n" + "Image URL: " + imageURL + "\n";
    }

}
