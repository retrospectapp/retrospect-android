package com.retrospect.retrospect;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class profileAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_account);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_connections) {
                    startActivity(new Intent(profileAccount.this, connectionsAccount.class));
                }
                if(tabId == R.id.tabs_id){
                    startActivity(new Intent(profileAccount.this, connectionsAccount.class));
                }
                if(tabId == R.id.tab_events){
                    startActivity(new Intent(profileAccount.this, MainActivity.class));
                }
            }
        });
    }
}
