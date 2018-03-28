package com.retrospect.retrospect;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mainNav = (BottomNavigationView) findViewById(R.id.main_nav);


        setContentView(R.layout.fragment_reminder);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Log.d("Test", "Here");
        fragmentManager.beginTransaction().add(R.id.FragmentContainer, new ReminderFragment()).commit();
    }
}
