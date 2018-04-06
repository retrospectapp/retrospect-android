package com.retrospect.retrospect;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.pchmn.materialchips.ChipsInput;
import com.pchmn.materialchips.model.ChipInterface;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Shivam on 4/5/2018.
 */

public class ConnectionSharedEventsFragment extends Fragment {

    private static final String TAG = ConnectionSharedEventsFragment.class.getSimpleName();

    private View view;
    private FirebaseClient firebaseClient;
    private String mUserFirebaseID;
    private ArrayList<Event> fetchedEvents = new ArrayList<>();
    private ArrayList<EventChip> fetchedEventChips = new ArrayList<>();
    private ChipsInput mChipsInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.connection_shared_events, container, false);
        /*firebaseClient = new FirebaseClient();
        mUserFirebaseID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fetchedEvents = firebaseClient.fetchEvents(mUserFirebaseID);
        */
        Event event1 = new Event("Event1", "1/2/16", "something happened", "Pleasanton, CA", null, null);
        Event event2 = new Event("Event2", "1/2/16", "something happened", "Pleasanton, CA", null, null);
        Event event3= new Event("Event3", "1/2/16", "something happened", "Pleasanton, CA", null, null);
        fetchedEvents.add(event1);
        fetchedEvents.add(event2);
        fetchedEvents.add(event3);
        mChipsInput = view.findViewById(R.id.chips_input);
        initEventChips(fetchedEvents);

        mChipsInput.addChipsListener(new ChipsInput.ChipsListener() {
            @Override
            public void onChipAdded(ChipInterface chipInterface, int i) {
                Log.e(TAG, "eventChip added, " + i);
            }

            @Override
            public void onChipRemoved(ChipInterface chipInterface, int i) {
                Log.e(TAG, "eventChip removed, " + i);
            }

            @Override
            public void onTextChanged(CharSequence charSequence) {
                Log.e(TAG, "text changed: " + charSequence);
            }
        });

        mChipsInput.setFilterableList(fetchedEventChips);
        return view;
    }

    private void initEventChips(ArrayList<Event> fetchedEvents){
        for (int i = 0; i < fetchedEvents.size(); i++){
            Event event = fetchedEvents.get(i);
            Random random = new Random();
            int rand = random.nextInt(event.getImages().size());
            Uri eventImageUri = Uri.parse(event.getImages().get(rand));
            fetchedEventChips.add(new EventChip("" + i, eventImageUri, event.getTitle(), event.getDate() + "    " + event.getLocation()));
        }
    }


    public ArrayList<Event> getSelectedEvents(){
        ArrayList<Event> selectedEvents = new ArrayList<>();
        ArrayList<EventChip> selectedEventChips = (ArrayList<EventChip>) mChipsInput.getSelectedChipList();
        for(int i = 0; i < selectedEventChips.size(); i++){
            selectedEvents.add(fetchedEvents.get(Integer.parseInt(selectedEventChips.get(i).getId().toString())));
        }
        return selectedEvents;
    }

}
