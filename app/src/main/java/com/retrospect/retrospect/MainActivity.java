package com.retrospect.retrospect;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.github.florent37.camerafragment.listeners.CameraFragmentResultListener;

public class MainActivity extends AppCompatActivity {


    private static final String CAMERA_TAG = "CAMERA";
    private User currUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        currUser = load user object from firebase
        String uid = (String) getIntent().getExtras().get("uid");
        currUser.setFirebaseId(uid);

        setContentView(R.layout.activity_timeline_ui);
        FloatingActionButton test = findViewById(R.id.reminderButton);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    CameraFragment cameraFragment = CameraFragment.newInstance(new Configuration.Builder().build());
                    cameraFragment.takePhotoOrCaptureVideo(new CameraFragmentResultListener() {
                        @Override
                        public void onVideoRecorded(String filePath) {

                        }

                        @Override
                        public void onPhotoTaken(byte[] bytes, String filePath) {

                        }
                    }, "", "");
                    switchFragment(cameraFragment, CAMERA_TAG);
                } else {
                    requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 1);
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        CameraFragment cameraFragment = CameraFragment.newInstance(new Configuration.Builder().build());
                        switchFragment(cameraFragment, CAMERA_TAG);
                    }
                }
            }
        });
    }

    public void switchFragment(Fragment fragment, String TAG){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, fragment, TAG)
                .commit();
    }

}
