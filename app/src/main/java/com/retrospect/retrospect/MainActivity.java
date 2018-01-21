package com.retrospect.retrospect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;


import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import android.graphics.Bitmap;
import android.net.Uri;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    SignInButton button;
    FirebaseAuth mAuth;
    private final static int RC_SIGN_IN =2;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth.AuthStateListener mAuthListener;

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

    ImageView imageView = findViewById(R.id.profileImageView);

    @Override
    protected void onStart(){
        super.onStart();

        createUser(user);
        createPatient(patient);
        createEvent(event);
        createReminder(reminder);
        createConnection(connection);

        mAuth.addAuthStateListener(mAuthListener);

        fetchUser(user.getUUID());
        fetchPatient(patient.getUUID());
        fetchEvent(event.getTitle());
        fetchReminder(reminder.getTitle());
        fetchConnection(connection.getPersonID());

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (SignInButton) findViewById(R.id.googleBtn);
        mAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(MainActivity.this, TimelineUI.class));
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

    }


//    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build();

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else{
                Toast.makeText(MainActivity.this, "Auth went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("Tag", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }

    private void loadUserProfileImage(String userID){
        Glide.with(this).load(mProfileImagesRef.child(userID)).into(imageView);
    }

    private void createUser(User user){

        mUserRef.document(user.getUUID()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "User successfully added!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Unable to successfully add user", e);
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
                }
                else{
                    Log.d(USERTAG, "User doesn't exist");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(USERTAG, "Unable to read user", e);
            }
        });
    }

    private void deleteUser(String userID){

        mProfileImagesRef.child(userID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DELTAG, "Successfully deleted profile image for user");
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
                Log.d(DELTAG, "Successfully deleted event images for user");
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

    private void loadPatientProfileImage(String userID){
        Glide.with(this).load(mProfileImagesRef.child(userID)).into(imageView);
    }

    private void createPatient(Patient patient){

        mPatientRef.document(patient.getUUID()).set(patient).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Patient successfully added!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Unable to successfully add patient");
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
                Log.w(PATTAG, "Unable to fetch patient", e);
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


    private void loadEventImage(String eventTitle){
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
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(UPTAG, "Failed to upload event image", e);
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
    }



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
                Log.w(EVENTTAG, "Unable to fetch event", e);
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
                Log.w(REMTAG, "Unabled to successfully fetch reminder", e);
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


