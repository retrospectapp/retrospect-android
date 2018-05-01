package com.retrospect.retrospect;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//<<<<<<< HEAD
//=======
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
//>>>>>>> reminders2
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mainNav;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton createRemind;
    private ActionBar bar;

    private String accountUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_ui);
//<<<<<<< HEAD
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.contentContainer, new TimelineFragment(),"TIMELINE")
//                .commit();
//=======

        accountUID = getIntent().getStringExtra("uid");

        bar = getActionBar();



        mainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floatingActionMenu);
        createRemind = (FloatingActionButton) findViewById(R.id.remi);



        setFragment(new TimelineFragment());
        mainNav.setItemBackgroundResource(R.color.timeline_nav_color);

        createRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.hideMenuButton(true);
                mainNav.setSelectedItemId(R.id.nav_reminders);
                setFragment(new CreateReminderFragment());
            }
        });

        final Bundle bundle = new Bundle();
        bundle.putString("uid", accountUID);

        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_reminders :
                        mainNav.setItemBackgroundResource(R.color.reminders_nav_color);
                        floatingActionMenu.hideMenuButton(true);
                        ReminderFragment fragment1 = new ReminderFragment();
                        fragment1.setArguments(bundle);
                        setFragment(fragment1);
                        return true;

                    case R.id.nav_events :
                        mainNav.setItemBackgroundResource(R.color.timeline_nav_color);
                        floatingActionMenu.showMenuButton(true);
                        TimelineFragment fragment2 = new TimelineFragment();
                        fragment2.setArguments(bundle);
                        setFragment(new TimelineFragment());
                        return true;

                    case R.id.nav_connections :
                        mainNav.setItemBackgroundResource(R.color.connections_nav_color);
//                        setFragment(connectionsFragment);
                        return true;

                    case R.id.nav_identify :
                        mainNav.setItemBackgroundResource(R.color.identify_nav_color);
//                        setFragment(identifyFragment);
                        return true;

                    case R.id.nav_profile :
                        mainNav.setItemBackgroundResource(R.color.profile_nav_color);
                        return true;


                    default:
                        return false;
                }
            }
        });

    }


    private void setFragment(Fragment fragment) {

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentContainer, fragment);
        fragmentTransaction.commit();
//>>>>>>> reminders2
    }
}