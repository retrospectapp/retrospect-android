package com.retrospect.retrospect;

/**
 * Created by Shivam on 1/18/2018.
 *
 */

public class Patient extends FaceRequests {

    private String fullName;
    private int age;
    private String uuid;
    private String careTakerID;
    private String emailID;
    private String imageURL;

    public Patient(){}

    public Patient(String fullName, int age, String uuid, String careTakerID, String emailID, String imageURL ){
        this.fullName = fullName;
        this.age = age;
        this.uuid = uuid;
        this.careTakerID = careTakerID;
        this.emailID = emailID;
        this.imageURL = imageURL;
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getUUID(){
        return uuid;
    }

    public void setUUID(String uuid){
        this.uuid = uuid;
    }

    public String getCareTakerID(){
        return careTakerID;
    }

    public void setCareTakerID(String careTakerID){
        this.careTakerID = careTakerID;
    }

    public String getEmailID(){
        return emailID;
    }

    public void setEmailID(String emailID){
        this.emailID = emailID;
    }

    public String getImageURL(){
        return imageURL;
    }

    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }

    public String toString(){
        return "Full Name: " + fullName + "\n" + "Age: " + age + "\n" + "UUID: " + uuid + "\n" + "CareTaker ID: " + careTakerID + "\n" + "Email ID" + emailID + "\n" + "Image URL: " + "\n";
    }

}
