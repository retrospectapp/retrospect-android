package com.retrospect.retrospect;

/**
 * Created by Shivam on 1/18/2018.
 *
 */

@SuppressWarnings("unused")
public class User {

    private String firstName;
    private String lastName;
    private int age;
    private String firebaseID;
    private String personID;
    private String emailID;
    private String imageURL;
    private boolean isPatient;

    public User(String firstName, String lastName, int age, String personID, String emailID, String imageURL, boolean isPatient){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.personID = personID;
        this.emailID = emailID;
        this.imageURL = imageURL;
        this.isPatient = isPatient;
    }


    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName(){ return getFirstName() + " " + getLastName(); }

    public int getAge(){
        return age;
    }

    public void setAge(int age){ this.age = age; }

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

    public void setImageURL(String imageURL){ this.imageURL = imageURL; }

    public boolean getIsPatient() { return isPatient; }

}
