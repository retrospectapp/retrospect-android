package com.retrospect.retrospect;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateEvent extends AppCompatActivity {

    private static final String TAG = CreateEvent.class.getSimpleName();
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    //private FirebaseStorage storage = FirebaseStorage.getInstance();
    //private StorageReference storageRef = storage.getReference();
    private ArrayList<String> imageDownloadURLs = new ArrayList<>();
    private String firebaseID;
    Event event;
    FirebaseClient firebaseClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Log.d(TAG, "onCreate: Starting");

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        //Set up the ViewPager with the sections adapter
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /*firebaseID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseClient = new FirebaseClient();
        event = createEvent();
        firebaseClient.createEvent(firebaseID, event, UUID.randomUUID().toString());
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.create_event:
                createEvent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Event createEvent(){
        //Collect data from all sub fragments and construct the event oject and send to firebase
        Event event = new Event();

        EventDetailsFragment eventDetailsFragment = (EventDetailsFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + 0);
        EventImagesFragment eventImagesFragment = (EventImagesFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + 1);
        EventMapFragment eventMapFragment = (EventMapFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + 2);
        EventParticipantsFragment eventParticipantsFragment = (EventParticipantsFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + 3);

        event.setTitle(eventDetailsFragment.getEventTitle());
        event.setDetails(eventDetailsFragment.getEventDetails());
        event.setDate(eventDetailsFragment.getDate());
        event.setLocation(eventMapFragment.getmLocation());

        //uploadImages(eventImagesFragment.getmImageUris(), event.getTitle());

        event.setImages(imageDownloadURLs);
        event.setPeopleInvolved(eventParticipantsFragment.getSelectedConnections());

        return event;
    }

    /*
    private void uploadImages(ArrayList<Uri> images, String eventTitle){
        String firebaseID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        for (int i = 0; i < images.size(); i++){
            if (images.get(i) != null){
                StorageReference eventImagesRef = storageRef.child("images/" + firebaseID + "/" + eventTitle + "/image" + i+1);
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                String downloadURL = eventImagesRef.putFile(images.get(i)).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(CreateEvent.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+(int)progress+"%");
                    }
                }).getSnapshot().getDownloadUrl().toString();
                imageDownloadURLs.add(downloadURL);
            }
        }
    }
    */

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new EventDetailsFragment(), "Details");
        adapter.addFragment(new EventImagesFragment(), "Images");
        adapter.addFragment(new EventMapFragment(), "Map");
        adapter.addFragment(new EventParticipantsFragment(), "People");
        viewPager.setAdapter(adapter);
    }
}
