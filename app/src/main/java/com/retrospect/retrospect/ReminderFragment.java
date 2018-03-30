package com.retrospect.retrospect;



import android.app.Activity;
import android.content.Context;
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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */



public class ReminderFragment extends Fragment {

    private FloatingActionButton reminders;
    private CreateReminderFragment createReminderFragment;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<Reminder> reminderList;


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




        reminders.show(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        reminderList = new ArrayList<>();

        for (int i=0; i<10; i++){
            Reminder card = new Reminder("Reminder " + (i+1), "3/28/2018", "12:00" , "AM", "Details");

            reminderList.add(card);
        }

        adapter = new ReminderCardAdapter(reminderList, getContext());

        recyclerView.setAdapter(adapter);

        reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createReminderFragment = new CreateReminderFragment();
                reminders.hide(true);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contentContainer, createReminderFragment);
                fragmentTransaction.commit();

            }
        });


        return v;

    }



}
