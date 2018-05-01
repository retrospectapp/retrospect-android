package com.retrospect.retrospect;



import android.os.Bundle;
import com.github.clans.fab.FloatingActionButton;

import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */



public class ReminderFragment extends Fragment {

    private FloatingActionButton reminders;
    private CreateReminderFragment createReminderFragment;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private String uid;

    private ArrayList<Reminder> reminderList;
    private FirebaseClient client;


    public ReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reminder, container, false);
        Log.d("onCreate", "onCreateView: Trying to inflate view");

        reminders = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.create_remind);
        recyclerView = (RecyclerView) v.findViewById(R.id.reminders_recycler_view);

//        uid = getArguments().getString("uid");


        reminders.show(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //TODO: pull reminders from database and populate list


//        Log.d("UserID: ", uid);


//        try {
//                reminderList = client.fetchReminders(uid);
//                adapter = new ReminderCardAdapter(reminderList, getContext());
//                recyclerView.setAdapter(adapter);
//
//        }
//        catch(NullPointerException e){
//            Toast.makeText(getActivity(), "There are no reminders", Toast.LENGTH_LONG).show();
//        }



//        for (int i=0; i<10; i++){
//            client.fetc
//            Reminder card = new Reminder("Reminder " + (i+1), "3/28/2018", "12:00" , "AM", "Details");
//
//            reminderList.add(card);
//        }



        reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("uid", uid);
                createReminderFragment = new CreateReminderFragment();
                createReminderFragment.setArguments(bundle);
                reminders.hide(true);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom,R.anim.slide_in_top, R.anim.slide_out_bottom);
                fragmentTransaction.replace(R.id.contentContainer, createReminderFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        return v;

    }



}
