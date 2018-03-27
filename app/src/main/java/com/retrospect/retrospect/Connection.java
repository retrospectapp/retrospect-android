package com.retrospect.retrospect;

/**
 * Created by sniradi on 3/26/18.
 */

import com.google.firebase.firestore.DocumentReference;package com.retrospect.retrospect;

        import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by Shivam on 1/19/2018.
 *
 */


@SuppressWarnings("unused")
public class Connection {

    private User user;
    private String relation;
    private String jingleURL;
    private DocumentReference userReference;

    public Connection(){}

    Connection(User user, String relation, String jingleURL){
        this.user = user;
        this.relation = relation;
        this.jingleURL = jingleURL;
        this.userReference = FirebaseFirestore.getInstance().collection("sampleData/Users").document(user.get);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRelation(){
        return relation;
    }

    public void setRelation(String relation) { this.relation = relation;}

    public String getJingleURL() {
        return jingleURL;
    }

    public void setJingleURL(String jingleURL) {
        this.jingleURL = jingleURL;
    }

}

