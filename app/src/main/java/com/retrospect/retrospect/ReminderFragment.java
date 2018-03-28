package com.retrospect.retrospect;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.github.clans.fab.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */



public class ReminderFragment extends Fragment {

    private FloatingActionButton reminders;
    private CreateReminderFragment createReminderFragment;


    public ReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reminder, container, false);
        Log.d("onCreate", "onCreateView: Trying to inflate view");

        reminders = (FloatingActionButton) v.findViewById(R.id.create_remind);

        reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createReminderFragment = new CreateReminderFragment();

                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.FragmentContainer, createReminderFragment);
                fragmentTransaction.commit();

            }
        });


        return v;

    }



}
