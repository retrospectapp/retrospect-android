package com.retrospect.retrospect;

import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.configuration.Configuration;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "CAMERA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_ui);
        // update fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.FragmentContainer, new TimelineFragment()).commit();

        FloatingActionButton test = findViewById(R.id.reminderButton);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    CameraFragment cameraFragment = CameraFragment.newInstance(new Configuration.Builder().build());

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentContainer, cameraFragment, FRAGMENT_TAG)
                            .commit();
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                }
            }
        });
    }
}
