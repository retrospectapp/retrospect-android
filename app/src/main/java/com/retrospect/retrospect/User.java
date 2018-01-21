package com.example.shivamdave.retrospecttest;

/**
 * Created by Shivam on 1/18/2018.
 */

public class User {

    private String fullName;
    private int age;
    private String uuid;
    private String personID;
    private String patientID;
    private String emailID;
    private String imageURL;

    public User(){}

    public User(String fullName, int age, String uuid, String personID, String patientID, String emailID, String imageURL){
        this.fullName = fullName;
        this.age = age;
        this.uuid = uuid;
        this.personID = personID;
        this.patientID = patientID;
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

    public String getPatientID(){
        return patientID;
    }

    public void setPatientID(String patientID){
        this.patientID = patientID;
    }

    public String getPersonID(){
        return personID;
    }

    public void setPersonID(String personID){
        this.personID = personID;
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
        return "Full Name: " + fullName + "\n" + "Age: " + age + "\n" + "UUID: " + uuid + "\n" + "Patient ID: " + patientID + "\n" + "Person ID:" + personID + "\n" + "Email ID" + emailID + "\n" + "Image URL: " + "\n";
    }

}
