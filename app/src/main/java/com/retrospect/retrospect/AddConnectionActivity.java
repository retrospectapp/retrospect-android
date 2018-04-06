package com.retrospect.retrospect;

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

import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class AddConnectionActivity extends AppCompatActivity {

    private static final String TAG = AddConnectionActivity.class.getSimpleName();

    private AddConnectionSectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private String firebaseID;
    Connection connection;
    FirebaseClient firebaseClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_connection);

        Log.d(TAG, "onCreate: Starting.");

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        /*firebaseID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        firebaseClient = new FirebaseClient();
        connection = createConnection();
        firebaseClient.createConnection(firebaseID, connection.getUser().getpersonID(), connection);
        */
    }

    private void setupViewPager(ViewPager viewPager){
        AddConnectionSectionsPageAdapter adapter = new AddConnectionSectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ConnectionSelectorFragment(), "Select Connection");
        adapter.addFragment(new ConnectionAdditionalInfoFragment(), "Additional Info");
        adapter.addFragment(new ConnectionSharedEventsFragment(), "Shared Events");
        viewPager.setAdapter(adapter);
    }

    private Connection createConnection(){
        Connection connection = new Connection();
        ConnectionSelectorFragment connectionSelectorFragment = (ConnectionSelectorFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + 0);
        ConnectionAdditionalInfoFragment connectionAdditionalInfoFragment = (ConnectionAdditionalInfoFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + 1);
        ConnectionSharedEventsFragment connectionSharedEventsFragment = (ConnectionSharedEventsFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + 2);
        //build connection object

        return connection;
    }
}
