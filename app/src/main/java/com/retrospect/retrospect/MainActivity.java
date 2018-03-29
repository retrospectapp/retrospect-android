package com.retrospect.retrospect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_ui);


        mainNav = (BottomNavigationView) findViewById(R.id.main_nav);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floatingActionMenu);
        createRemind = (FloatingActionButton) findViewById(R.id.remi);



        setFragment(new TimelineFragment());

        createRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.hideMenuButton(true);
                mainNav.setSelectedItemId(R.id.nav_reminders);
                setFragment(new CreateReminderFragment());
            }
        });
        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_reminders :
//                      mainNav.setItemBackgroundResource(R.color.colorPrimary);
                        floatingActionMenu.hideMenuButton(true);
                        setFragment(new ReminderFragment());
                        return true;

                    case R.id.nav_events :
//                        mainNav.setItemBackgroundResource(R.color.colorPrimary);
                        floatingActionMenu.showMenuButton(true);
                        setFragment(new TimelineFragment());
                        return true;

                    case R.id.nav_connections :
//                        mainNav.setItemBackgroundResource(R.color.colorPrimary);
//                        setFragment(connectionsFragment);
                        return true;

                    case R.id.nav_identify :
//                        mainNav.setItemBackgroundResource(R.color.colorPrimary);
//                        setFragment(identifyFragment);
                        return true;

                    case R.id.nav_profile :
//                        mainNav.setItemBackgroundResource(R.color.colorPrimary);
//                        setFragment(profileFragment);
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
    }
}
