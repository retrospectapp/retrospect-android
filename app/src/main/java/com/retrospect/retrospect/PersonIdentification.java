package com.retrospect.retrospect;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.*;

import java.io.InputStream;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Programming on 1/13/2018.
 * Dynamic interface to simulate interaction with Microsoft Face API
 */

public class PersonIdentification extends AppCompatActivity{
    IdentifyTask task;
    @BindView(R.id.button) Button createGroup;
    @BindView(R.id.button2) Button createPerson;
    @BindView(R.id.button3) Button addPerson;
    @BindView(R.id.button4) Button displayDetails;
    @BindView(R.id.button5) Button identify_button;
    @BindView(R.id.textView3) TextView tvname;
    @BindView(R.id.editText) EditText name;

    private final int PICK_IMAGE = 1;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identification);
        ButterKnife.bind(this);

        identify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //task.execute();
            }
        });
    }

}

class IdentifyTask extends AsyncTask<InputStream, String, Face[]> {
    private FaceServiceClient faceServiceClient =
            new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0", "068682577ef84250b24aafbc3b2c8e66");

    @Override
    protected Face[] doInBackground(InputStream... params) {
        try {
            publishProgress("Detecting...");
            Face[] result = faceServiceClient.detect(
                    params[0],
                    true,         // returnFaceId
                    false,        // returnFaceLandmarks
                    null           // returnFaceAttributes: a string like "age, gender"
            );
            if (result == null)
            {
                publishProgress("Detection Finished. Nothing detected");
                return null;
            }
            publishProgress(
                    String.format(Locale.US, "Detection Finished. %d face(s) detected",
                            result.length));
            return result;
        } catch (Exception e) {
            publishProgress("Detection failed");
            return null;
        }
    }
}