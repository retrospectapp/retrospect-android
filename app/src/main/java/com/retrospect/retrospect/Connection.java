package com.retrospect.retrospect;

import com.google.firebase.firestore.DocumentReference;

/**
 * Created by Shivam on 1/19/2018.
 *
 */


@SuppressWarnings("unused")
public class Connection {

    private String personID;
    private String userID;
    private String relation;
    private String imageURL;
    private DocumentReference userRef;

    public Connection(){}

    public Connection( String personID, String userID, String relation, String imageURL, DocumentReference userRef){
        this.userID = userID;
        this.personID = personID;
        this.relation = relation;
        this.imageURL = imageURL;
        this.userRef = userRef;
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

    public void setPersonID(String personID){ this.personID = personID; }

    public String getRelation(){
        return relation;
    }

    public void setRelation(String relation) { this.relation = relation;}

    public String getImageURL(){ return imageURL; }

    public void setImageURL(String imageURL){ this.imageURL = imageURL; }

    public DocumentReference getUserRef() { return userRef; }

    public void setUserRef(DocumentReference userRef) { this.userRef = userRef; }

}
