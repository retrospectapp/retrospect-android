package com.retrospect.retrospect;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shivam on 3/26/2018.
 */

public class EventParticipantsFragment extends Fragment {

    private static final String TAG = EventParticipantsFragment.class.getSimpleName();
    private View view;
    private FirebaseClient firebaseClient;
    private String mUserFirebaseID;
    private CheckBox checkBox;
    private List<Connection> fetchedConnections;
    private ArrayList<Connection> connections = new ArrayList<>();
    private ArrayList<Connection> selectedConnections = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.event_participants_fragment, container, false);

        /*firebaseClient = new FirebaseClient();
        mUserFirebaseID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fetchedConnections = firebaseClient.fetchConnections(mUserFirebaseID);
        connections = new ArrayList<>(fetchedConnections);
        */
        User user1 = new User("Shivam", "Dave", 19, "123456", "shivam.dave@gmail.com", null, false);
        User user2 = new User("Shreyas", "Niradi", 20, "456789", "shreyas.niradi@gmail.com", null, false);
        User user3 = new User("Nithin", "Dave", 21, "123789", "nithin.kumar@gmail.com", null, true);

        Connection connection1 = new Connection(user1, "Sibling", null);
        Connection connection2 = new Connection(user2, "Spouse", null);
        Connection connection3 = new Connection(user3, "Child", null);
        connections.add(connection1);
        connections.add(connection2);
        connections.add(connection3);
        initRecyclerView();

        return view;
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = view.findViewById(R.id.eventParticipantsRecyclerView);
        EventParticipantsRecyclerViewAdapter adapter = new EventParticipantsRecyclerViewAdapter(this.getActivity(), connections, new ConnectionsInvolvedClickListener(){
           @Override
            public void onItemClick(View v, int position){
               Log.d(TAG, "clicked position: " + position);
               if (connections.get(position).isSelected()){
                   selectedConnections.remove(connections.get(position));
                   connections.get(position).setIsSelected(false);
               }
               else{
                   selectedConnections.add(connections.get(position));
                   connections.get(position).setIsSelected(true);
               }
           }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    public ArrayList<Connection> getSelectedConnections(){
        return selectedConnections;
    }
}
