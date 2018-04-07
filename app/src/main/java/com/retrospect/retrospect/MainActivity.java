package com.retrospect.retrospect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camerafragment_activity_main);
        ButterKnife.bind(this);
        if(getIntent().getExtras() != null) {
            userId = (String) getIntent().getExtras().get("uid");
        }
        Bundle bundle = new Bundle();
        bundle.putString("uid", userId);
        Identify identifyFragment = new Identify();
        identifyFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, identifyFragment)
                .commit();
    }



}