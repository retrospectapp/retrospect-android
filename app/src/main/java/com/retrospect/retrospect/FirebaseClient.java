package com.retrospect.retrospect;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Shivam on 2/21/2018.
 *
 */

@SuppressWarnings("unused")
public class FirebaseClient {

    private static final String TAG = "InspiringQuote";
    private static final String USERTAG = "UserData";
    private static final String PATTAG = "PatientData";
    private static final String DELTAG = "DeleteData";
    private static final String EVENTTAG = "EventData";
    private static final String REMTAG = "ReminderData";
    private static final String CONTAG = "ConnectionData";
    private static final String UPTAG = "UploadData";

    private CollectionReference mUserRef = FirebaseFirestore.getInstance().collection("Users");
    private StorageReference mProfileImagesRef = FirebaseStorage.getInstance().getReference().child("profileImages");
    private StorageReference mEventImagesRef = FirebaseStorage.getInstance().getReference().child("eventImages");

    public FirebaseClient(){

    }

    CollectionReference getUserRef(){
        return mUserRef;
    }

    public boolean checkIfPatient(String firebaseID){

        Map<String, Object> userData = mUserRef.document(firebaseID).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(USERTAG, "Failed to get user or patient");
            }
        }).getResult().getData();
        return (userData.get("isPatient").equals(true));
    }



    public void createCareTaker(String firebaseCareTakerID, User careTaker){

        mUserRef.document(firebaseCareTakerID).set(careTaker, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(USERTAG, "Successfully created user");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(USERTAG, "Failed to create user", e);
            }
        });
    }



    public void createPatient(String firebaseCareTakerID, String firebasePatientID, User patient){

        mUserRef.document(firebasePatientID).set(patient, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(PATTAG, "Successfully created patient");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(PATTAG, "Failed to create patient", e);
            }
        });

        DocumentReference patientDocRef = mUserRef.document(firebasePatientID);
        Connection newPatient = new Connection(patient.getpersonID(), firebasePatientID, "Patient", patient.getImageURL(), patientDocRef);

        mUserRef.document(firebaseCareTakerID).collection("Patients").document(firebasePatientID).set(newPatient).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(PATTAG, "Patient successfully added to user's patient bank");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(PATTAG, "Failed to add patient to user's patient bank", e);
            }
        });
    }



    public void deleteUser(String firebaseID){

        mProfileImagesRef.child(firebaseID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Profile image successfully deleted for user");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete profile image for user", e);
            }
        });

        mEventImagesRef.child(firebaseID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Event images successfully deleted for user");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete event images for user", e);
            }
        });

        mUserRef.document(firebaseID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Successfully deleted user");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete user", e);
            }
        });
    }


    public void removeUser(String firebaseCareTakerID, String firebasePatientID){

        mUserRef.document(firebasePatientID).collection("Caretakers").document(firebaseCareTakerID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "User successfully removed from user's caretaker collection");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to remove user from caretaker collection", e);
            }
        });
    }



    public void removePatient(String firebaseCareTakerID, String firebasePatientID){

        mUserRef.document(firebaseCareTakerID).collection("Patients").document(firebasePatientID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Successfully removed patient from user's patient collection");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(PATTAG, "Failed to remove patient from user's patient collection", e);
            }
        });
    }



    private List<DocumentSnapshot> fetchCareTakerSnapshots(String firebasePatientID){
        return mUserRef.document(firebasePatientID).collection("Connections").whereEqualTo("relation", "Caretaker").get().getResult().getDocuments();
    }



    public List<User> fetchCareTakers(String firebasePatientID){

        List<User> fetchedCareTakers = new ArrayList<>();
        List<DocumentSnapshot> fetchedCareTakerSnapshots = fetchCareTakerSnapshots(firebasePatientID);
        for(int i = 0; i < fetchedCareTakerSnapshots.size(); i++){
            fetchedCareTakers.add(fetchedCareTakerSnapshots.get(i).toObject(User.class));
        }
        return fetchedCareTakers;
    }




    public User fetchUser(String firebaseUserID){

        return mUserRef.document(firebaseUserID).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(USERTAG, "Failed to fetch user", e);
            }
        }).getResult().toObject(User.class);
    }



    private List<DocumentSnapshot> fetchPatientSnapshots(String firebaseUserID){

        return mUserRef.document(firebaseUserID).collection("Patients").get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(USERTAG, "Failed to fetch user's patients", e);
            }
        }).getResult().getDocuments();

    }



    public List<User> fetchPatients(String firebaseUserID){

        List<User> fetchedPatients = new ArrayList<>();
        List<DocumentSnapshot> patientSnapshots = fetchPatientSnapshots(firebaseUserID);
        for(int i = 0; i < patientSnapshots.size(); i++){
            fetchedPatients.add(patientSnapshots.get(i).toObject(User.class));
        }
        return fetchedPatients;
    }



    public Event fetchEvent(String firebaseCareTakerID, String firebaseEventID){

       return mUserRef.document(firebaseCareTakerID).collection("Events").document(firebaseEventID).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(EVENTTAG, "Failed to fetch event", e);
            }
        }).getResult().toObject(Event.class);
    }



    private List<DocumentSnapshot> fetchEventSnapshots(String firebasePatientID){

        return mUserRef.document(firebasePatientID).collection("Events").get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(EVENTTAG, "Failed to fetch all events", e);
            }
        }).getResult().getDocuments();
    }



    private List<Event> fetchEvents(String firebasePatientID){

        List<Event> fetchedEvents = new ArrayList<>();
        List<DocumentSnapshot> eventSnapshots = fetchEventSnapshots(firebasePatientID);
        for (int i = 0; i < eventSnapshots.size(); i++){
            fetchedEvents.add(eventSnapshots.get(i).toObject(Event.class));
        }
        return fetchedEvents;
    }



    public List<Event> fetchSharedEvents(String firebasePatientID, String firebaseCareTakerID){

        List<Event> sharedEvents = new ArrayList<>();
        List<Event> fetchedEvents = fetchEvents(firebasePatientID);
        for(int i = 0; i < fetchedEvents.size(); i++){
            if (fetchedEvents.get(i).getPeopleInvolved().containsKey(firebaseCareTakerID)){
                sharedEvents.add(fetchedEvents.get(i));
            }
        }
        return sharedEvents;
    }



    public void updateEvent(String firebasePatientID, String firebaseEventID, Event event){

        mUserRef.document(firebasePatientID).collection("Events").document(firebaseEventID).set(event, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(EVENTTAG, "Successfully update event");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(EVENTTAG, "Failed to update event", e);
            }
        });
    }



    public void deleteEvent(String firebasePatientID, String firebaseEventID){

        mEventImagesRef.child(firebasePatientID).child(firebaseEventID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Event image successfully deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete event image", e);
            }
        });

        mUserRef.document(firebasePatientID).collection("Events").document(firebaseEventID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Event successfully deleted for patient!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete event for patient", e);
            }
        });
    }



    public void createReminder(String firebasePatientID,String reminderID, Reminder reminder){

        mUserRef.document(firebasePatientID).collection("Reminders").document(reminderID).set(reminder).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(REMTAG, "Reminder successfully created for user!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(REMTAG, "Failed to create reminder for user", e);
            }
        });
    }



    public Reminder fetchReminder(String firebasePatientID, String reminderID){

       return mUserRef.document(firebasePatientID).collection("Reminders").document(reminderID).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(REMTAG, "Failed to fetch reminder", e);
            }
        }).getResult().toObject(Reminder.class);
    }



    private List<DocumentSnapshot> fetchReminderSnapshots(String firebasePatientID){

        return mUserRef.document(firebasePatientID).collection("Reminders").get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(REMTAG, "Failed to fetch patient's reminders");
            }
        }).getResult().getDocuments();
    }



    public List<Reminder> fetchReminders(String firebasePatientID){

        List<Reminder> fetchedReminders = new ArrayList<>();
        List<DocumentSnapshot> reminderSnapshots = fetchReminderSnapshots(firebasePatientID);
        for(int i = 0; i < reminderSnapshots.size(); i++){
            fetchedReminders.add(reminderSnapshots.get(i).toObject(Reminder.class));
        }
        return fetchedReminders;
    }



    public void updateReminder(String firebasePatientID, String reminderID, Reminder reminder){

        mUserRef.document(firebasePatientID).collection("Reminders").document(reminderID).set(reminder, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(REMTAG, "Successfully updated reminder");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(REMTAG, "Failed to update reminder", e);
            }
        });
    }



    public void deleteReminder(String firebasePatientID, String reminderID){

        mUserRef.document(firebasePatientID).collection("Reminders").document(reminderID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Reminder successfully deleted for user!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete reminder for user", e);
            }
        });
    }



    public void createConnection(String firebasePatientID, String personID, Connection connection){

        mUserRef.document(firebasePatientID).collection("Connections").document(personID).set(connection).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(CONTAG, "Connection successfully added for user!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(CONTAG, "Failed to add connection for user", e);
            }
        });
    }



    public Connection fetchUserConnection(String firebaseUserID, String personID){

        return mUserRef.document(firebaseUserID).collection("Connections").document(personID).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(CONTAG, "Failed to fetch user's connection", e);
            }
        }).getResult().toObject(Connection.class);
    }



    private List<DocumentSnapshot> fetchUserSnapshots(String firebaseUserID){

        return mUserRef.document(firebaseUserID).collection("Connections").get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(CONTAG, "Failed to fetch user's connections", e);
            }
        }).getResult().getDocuments();
    }



    public List<Connection> fetchConnections(String firebaseUserID){

        List<Connection> fetchedConnections = new ArrayList<>();
        List<DocumentSnapshot> connectionSnapshots = fetchUserSnapshots(firebaseUserID);
        for(int i = 0; i < connectionSnapshots.size(); i++){
            fetchedConnections.add(connectionSnapshots.get(i).toObject(Connection.class));
        }
        return fetchedConnections;
    }



    public void deleteUserConnection(String firebaseUserID, String personID){

        mUserRef.document(firebaseUserID).collection("Connections").document(personID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Connection successfully deleted for user!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete connection for user", e);
            }
        });
    }

}
