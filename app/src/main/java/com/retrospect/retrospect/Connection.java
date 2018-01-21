package com.example.shivamdave.retrospecttest;

/**
 * Created by Shivam on 1/19/2018.
 */

public class Connection {

    private String fullName;
    private String userID;
    private String personID;
    private String relation;

    public Connection(){}

    public Connection(String fullName, String userID, String personID, String relation){
        this.fullName = fullName;
        this.userID = userID;
        this.personID = personID;
        this.relation = relation;
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public String getUserID(){
        return userID;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public String getPersonID(){
        return personID;
    }

    public void setPersonID(String personID){
        this.personID = personID;
    }

    public String getRelation(){
        return relation;
    }

    public void setRelation(String relation){
        this.relation = relation;
    }

    public String toString(){
        return "Full Name: " + fullName + "\n" + "User ID: " + userID + "\n" + "Face ID: " + personID + "\n" + "Relation: " + relation + "\n";
    }
}
