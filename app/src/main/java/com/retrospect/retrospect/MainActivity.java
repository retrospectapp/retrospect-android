package com.retrospect.retrospect;

import android.app.Dialog;
import android.app.ListActivity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends ListActivity {

    public static final String TAG = "InspiringQuote";
    public static final String USERTAG = "UserData";
    public static final String PATTAG = "PatientData";
    public static final String DELTAG = "DeleteData";
    public static final String EVENTTAG = "EventData";
    public static final String REMTAG = "ReminderData";
    public static final String CONTAG = "ConnectionData";
    public static final String UPTAG = "UploadData";

    private User user = new User("Shivam Dave", 19, "abcd123", "a1b2c3", "1518732", "shivam.dave@gmail.com", "");
    private Patient patient = new Patient("Shreyas Niradi", 19, "efg456", "1606197", "shreyas.niradi@gmail.com", "");
    private Event event = new Event("CalHacks 4.0", "10/7/17", "We were awarded for Best Use of Microsoft Services", "UC Davis", null, "");
    private Reminder reminder = new Reminder("HackDavis 2018", "1/20/18", "11:00", "AM", "Get hyped and grind");
    private Connection connection = new Connection("Nithin Kumar", "hij789", "2302655", "Friend");
    private Connection connection1 = new Connection("Kunal Patel", "asd3k3", "3298793", "Brother");
    private CollectionReference mUserRef = FirebaseFirestore.getInstance().collection("sampleData/Users/Accounts");
    private CollectionReference mPatientRef = FirebaseFirestore.getInstance().collection("sampleData/Patients/Accounts");
    private CollectionReference mUserEventRef = FirebaseFirestore.getInstance().collection("sampleData/Users/Accounts").document(user.getUUID()).collection("Events");
    private CollectionReference mPatientEventRef = FirebaseFirestore.getInstance().collection("sampleData/Patients/Accounts").document(patient.getUUID()).collection("Events");
    private CollectionReference mUserReminderRef = FirebaseFirestore.getInstance().collection("sampleData/Users/Accounts").document(user.getUUID()).collection("Reminders");
    private CollectionReference mPatientReminderRef = FirebaseFirestore.getInstance().collection("sampleData/Patients/Accounts").document(patient.getUUID()).collection("Reminders");
    private CollectionReference mUserConnectionRef = FirebaseFirestore.getInstance().collection("sampleData/Users/Accounts").document(user.getUUID()).collection("Connections");
    private CollectionReference mPatientConnectionRef = FirebaseFirestore.getInstance().collection("sampleData/Patients/Accounts").document(patient.getUUID()).collection("Connections");

    private StorageReference mProfileImagesRef = FirebaseStorage.getInstance().getReference().child("profileImages");
    private StorageReference mEventImagesRef = FirebaseStorage.getInstance().getReference().child("eventImages");

    private Dialog myDialog;
    private FloatingActionMenu fam;
    private FloatingActionButton people_button, event_button, reminder_button;
    //ImageView imageView = findViewById(R.id.profileImageView);

    ArrayList<Connection> connections;
    ArrayList<Event> events;

    @Override
    protected void onStart(){
        super.onStart();

        //createUser(user);
        //createPatient(patient);
        //createEvent(event);
        //createReminder(reminder);
        //createConnection(connection);

        fetchUser(user.getUUID());
        fetchPatient(patient.getUUID());
        fetchEvent(event.getTitle());
        fetchReminder(reminder.getTitle());
        fetchConnection(connection.getPersonID());

        // Create Timeline rows List
        ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();

        // Create new timeline row (Row Id)
        TimelineRow myRow = new TimelineRow(0);

        // To set the row Date (optional)
        myRow.setDate(new Date(0));  //(new Date());
        // To set the row Title (optional)
        myRow.setTitle("Met with contact: Bob Ross");
        // To set the row Description (optional)
        myRow.setDescription("description (string)");
        // To set the row bitmap image (optional)
        myRow.setImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        // To set row Below Line Color (optional)
        myRow.setBellowLineColor(Color.argb(255, 0, 0, 0));
        // To set row Below Line Size in dp (optional)
        myRow.setBellowLineSize(6);
        // To set row Image Size in dp (optional)
        myRow.setImageSize(40);
        // To set background color of the row image (optional)
        myRow.setBackgroundColor(Color.argb(255, 0, 0, 0));
        // To set the Background Size of the row image in dp (optional)
        myRow.setBackgroundSize(60);
        // To set row Date text color (optional)
        myRow.setDateColor(Color.argb(255, 0, 0, 0));
        // To set row Title text color (optional)
        myRow.setTitleColor(Color.argb(255, 0, 0, 0));
        // To set row Description text color (optional)
        myRow.setDescriptionColor(Color.argb(255, 0, 0, 0));

        // Create the Timeline Adapter
        ArrayAdapter<TimelineRow> myAdapter = new TimelineViewAdapter(this, 0, timelineRowsList,
                //if true, list will be sorted by date
                false);

        // Get the ListView and Bind it with the Timeline Adapter
        ListView myListView = getListView();//(ListView) findViewById(R.id.lis);
        myListView.setAdapter(myAdapter);

//        setContentView(R.layout.activity_timeline_ui);

        fam = (FloatingActionMenu) findViewById(R.id.floatingActionMenu);
        people_button = (FloatingActionButton) findViewById(R.id.peeps);
        event_button = (FloatingActionButton) findViewById(R.id.eve);
        reminder_button = (FloatingActionButton) findViewById(R.id.remi);


        event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        people_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        reminder_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onListItemClick(ListView l, View v, int pos, long id) {
        super.onListItemClick(l, v, pos, id);
        this.ShowPopup(getListView(), pos);//TODO PLAN: have an array of event details (each index is an event), associate 'id' with index
    }

    public void ShowPopup(View v, int index) {
        TextView txtclose;
        myDialog.setContentView(R.layout.details_popup);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        TextView placeholder;
        placeholder = (TextView) myDialog.findViewById(R.id.indexPlaceholder);
        placeholder.setText(index + "");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_ui);
        myDialog = new Dialog(this);

    }
    /*
    private void loadUserProfileImage(String userID){
        Glide.with(this).load(mProfileImagesRef.child(userID)).into(imageView);
    }*/

    private void createUser(User user){
        mUserRef.document(user.getUUID()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "User successfully created!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Failed to create user", e);
            }
        });
    }

    private void fetchUser(String userID){

        mUserRef.document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    User fetchedUser = documentSnapshot.toObject(User.class);
                    fetchedUser.toString();
                    Log.d(USERTAG, "User successfully fetched");
                }
                else{
                    Log.d(USERTAG, "User doesn't exist");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(USERTAG, "Failed to read user", e);
            }
        });
    }

    private void deleteUser(String userID){

        mProfileImagesRef.child(userID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

        mEventImagesRef.child(userID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

        mUserRef.document(userID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

    /*
    private void loadPatientProfileImage(String userID){
        Glide.with(this).load(mProfileImagesRef.child(userID)).into(imageView);
    }*/


    private void createPatient(Patient patient){

        mPatientRef.document(patient.getUUID()).set(patient).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Patient successfully added!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Failed to add patient");
            }
        });
    }

    private void fetchPatient(String userID){

        mPatientRef.document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Patient fetchedPatient = documentSnapshot.toObject(Patient.class);
                    fetchedPatient.toString();
                }
                else{
                    Log.d(PATTAG, "Patient doesn't exist");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(PATTAG, "Failed to fetch patient", e);
            }
        });
    }

    private void deletePatient(String userID){

        mProfileImagesRef.child(userID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Profile image for patient successfully deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete profile image for patient", e);
            }
        });

        mEventImagesRef.child(userID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Event images for patient successfully deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete event images for patient");
            }
        });

        mPatientRef.document(userID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Successfully deleted patient");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete patient", e);
            }
        });
    }

    /*private void loadEventImage(String eventTitle){
        Glide.with(this).load(mEventImagesRef.child(patient.getUUID()).child(eventTitle)).into(imageView);
    }

    private void createEvent(final Event event){

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mEventImagesRef.child(patient.getUUID()).putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadURL = taskSnapshot.getDownloadUrl();
                event.setImageURL(downloadURL.toString());
                Log.d(EVENTTAG, "NEW EVENT IMAGE URL IS" + event.getImageURL());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(EVENTTAG, "Failed to upload event image", e);
            }
        });


        mUserEventRef.document(event.getTitle()).set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(EVENTTAG, "Event successfully created for user!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(EVENTTAG, "Failed to create event for user", e);
            }
        });

        mPatientEventRef.document(event.getTitle()).set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(EVENTTAG, "Event successfully created for patient!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(EVENTTAG, "Failed to create event for patient",e);
            }
        });
    }*/

    private void fetchEvent(String eventTitle){

        mUserEventRef.document(eventTitle).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Event fetchedEvent = documentSnapshot.toObject(Event.class);
                    fetchedEvent.toString();
                }
                else{
                    Log.d(EVENTTAG, "Event doesn't exist");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(EVENTTAG, "Failed to fetch event", e);
            }
        });
    }

    private void fetchEvents(){

        mPatientEventRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot document: task.getResult()){
                        //Do something//
                    }
                }
            }
        });
    }

    private void deleteEvent(String eventTitle){

        mEventImagesRef.child(patient.getUUID()).child(eventTitle).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Event image successfully deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete event image");
            }
        });

        mUserEventRef.document(eventTitle).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Event succesfully deleted for user!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete event for user", e);
            }
        });


        mPatientEventRef.document(eventTitle).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void createReminder(Reminder reminder){

        mUserReminderRef.document(reminder.getTitle()).set(reminder).addOnSuccessListener(new OnSuccessListener<Void>() {
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


        mPatientReminderRef.document(reminder.getTitle()).set(reminder).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(REMTAG, "Reminder successfully created for patient!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(REMTAG, "Failed to create reminder for patient");
            }
        });
    }

    private void fetchReminder(String title){

        mUserReminderRef.document(title).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Reminder fetchedReminder = documentSnapshot.toObject(Reminder.class);
                    fetchedReminder.toString();
                }
                else{
                    Log.d(REMTAG, "Reminder doesn't exist");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(REMTAG, "Failed to fetch reminder", e);
            }
        });
    }

    private void fetchReminders(){

        mPatientReminderRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot document: task.getResult()){
                        //Do something//
                    }
                }
            }
        });
    }

    private void deleteReminder(String title){

        mUserReminderRef.document(title).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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


        mPatientReminderRef.document(title).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Reminder successfully deleted for patient!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete reminder for patient", e);
            }
        });
    }

    private void createConnection(final Connection connection){

        mUserConnectionRef.document(connection.getPersonID()).set(connection).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        mPatientConnectionRef.document(connection.getPersonID()).set(connection).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(CONTAG, "Connection successfully added for patient!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(CONTAG, "Failed to add connection for patient!", e);
            }
        });
    }

    private void fetchConnection(String personID){

        mPatientConnectionRef.document(personID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Connection fetchedConnection = documentSnapshot.toObject(Connection.class);
                    fetchedConnection.toString();
                }
                else{
                    Log.d(CONTAG, "Connection doesn't exist");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(CONTAG, "Unable to fetch connection", e);
            }
        });
    }

    private void fetchConnections(){

        mPatientConnectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot document: task.getResult()){
                        //Do something//
                    }
                }
            }
        });
    }

    private void deleteConnection(String personID){

        mUserConnectionRef.document(personID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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


        mPatientConnectionRef.document(personID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Connection successfully deleted for patient");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Failed to delete connection for patient", e);
            }
        });
    }
























    // Ignore the following. Documentation code. //


    /*
    public static final String AUTHOR_KEY = "author";
    public static final String QUOTE_KEY = "quote";

    TextView mQuoteTextView;

    private CollectionReference root = FirebaseFirestore.getInstance().collection("sampleData");

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/inspiration");



    public void deleteDocument(String docName){

        root.document(docName).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Deletion was successful!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DELTAG, "Deletion was unsuccessful", e);
            }
        });
    }



    public void writeToDatabase(){
        String quoteText = "You either a die hero, or you live long enough to see yourself become the villain";
        String authorText = "Harvey Dent";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(QUOTE_KEY, quoteText);
        map.put(AUTHOR_KEY, authorText);
        mDocRef.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Document has been saved!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Document was not saved!", e);
            }
        });
    }

    private void readFromDatabase(){
        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String quoteText = documentSnapshot.getString(QUOTE_KEY);
                    String authorText = documentSnapshot.getString(AUTHOR_KEY);

                    Map<String, Object> map = documentSnapshot.getData();

                    mQuoteTextView.setText(quoteText + authorText);
                }
            }
        });
    }

    */

}
