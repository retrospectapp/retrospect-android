package com.retrospect.retrospect;

/**
 * Created by Shivam on 1/18/2018.
 *
 */

@SuppressWarnings("unused")
public class Patient {

    private String firstName;
    private String lastName;
    private int age;
    private String emailID;
    private String imageURL;
    private static final boolean isPatient = true;

    public Patient(){}

    public Patient(String firstName, String lastName, int age, String emailID, String imageURL ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.emailID = emailID;
        this.imageURL = imageURL;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName){ this.lastName = lastName; }

    public String getFullName() { return getFirstName() + " " + getLastName(); }

    public int getAge(){
        return age;
    }

    public void setAge(int age){ this.age = age; }

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

    public boolean getIsPatient() { return isPatient; }


}
