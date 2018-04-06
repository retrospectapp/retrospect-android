package com.retrospect.retrospect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by Shivam on 4/4/2018.
 */

public class ConnectionSelectorFragment extends Fragment {

    private static final String TAG = ConnectionSelectorFragment.class.getSimpleName();

    private View view;
    private FirebaseClient firebaseClient;
    private String mUserFirebaseID;
    private User selectedUser;
    private ArrayList<User> fetchedUsers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.connection_selector, container, false);
        /*firebaseClient = new FirebaseClient();
        mUserFirebaseID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fetchedUsers = firebaseClient.fetchUsers();

        initRecyclerView();
        */
        return view;
    }


    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init RecyclerView");
        RecyclerView recyclerView = view.findViewById(R.id.eventParticipantsRecyclerView);
        ConnectionSelectorRecyclerViewAdapter adapter = new ConnectionSelectorRecyclerViewAdapter(this.getActivity(), fetchedUsers);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }


    public User getSelectedUser(){
        return selectedUser;
    }

}
