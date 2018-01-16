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
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Programming on 1/13/2018.
 * Dynamic interface to simulate interaction with Microsoft Face API
 */

public class PersonIdentification extends AppCompatActivity{
    @BindView(R.id.button) Button createGroup;
    @BindView(R.id.button2) Button createPerson;
    @BindView(R.id.button3) Button addPerson;
    @BindView(R.id.button4) Button displayDetails;
    @BindView(R.id.button5) Button identify_button;
    @BindView(R.id.textView3) TextView tvname;
    @BindView(R.id.editText) EditText group_name_text;
    @BindView(R.id.editText2) EditText person_name_text;
    private final int PICK_IMAGE = 1;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identification);
        ButterKnife.bind(this);

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(group_name_text.getText() != null){
                    String group_name = group_name_text.getText().toString();
                    new CreateGroup().execute(generateID(group_name), group_name, null);
                }
            }
        });
        createPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(group_name_text.getText() != null && person_name_text.getText() != null){
                        String group_name = group_name_text.getText().toString();
                        String person_name = person_name_text.getText().toString();
                        new CreatePerson().execute(generateID(group_name), person_name, null);
                }
            }
        });
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(group_name_text.getText() != null && person_name_text.getText() != null){
                    String group_ID = generateID(group_name_text.getText().toString());
                    UUID person_ID = UUID.fromString(generateID(person_name_text.getText().toString()));
                    String user_data = null; //fix
                    FaceRectangle face = null; //fix
                    String targetFace = null; //fix
                    new AddPersonFace(group_ID,person_ID,user_data, targetFace, face).execute();
                }
            }
        });
    }

    public String generateID(String val){
        return (val + 10); // test ID generator
    }
    static class CreateGroup extends AsyncTask<String, String, String> {
        private FaceServiceClient faceServiceClient =
                new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0", "068682577ef84250b24aafbc3b2c8e66");

        @Override
        protected String doInBackground(String... params) {
            try {
                publishProgress("Creating Group...");
                faceServiceClient.createPersonGroup(
                        params[0],
                        params[1],
                        params[2]
                );
                publishProgress("Created group with name", params[1]);
                return "";
            } catch (Exception e) {
                publishProgress("Creation failed: " + e);
                return null;
            }
        }
    }

    static class CreatePerson extends AsyncTask<String, String, String> {
        private FaceServiceClient faceServiceClient =
                new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0", "068682577ef84250b24aafbc3b2c8e66");

        @Override
        protected String doInBackground(String... params) {
            try {
                publishProgress("Creating Person...");
                faceServiceClient.createPerson(
                        params[0],
                        params[1],
                        params[2]
                );
                publishProgress("Created Person with name", params[1]);
                return "";
            } catch (Exception e) {
                publishProgress("Creation failed: " + e);
                return null;
            }
        }
    }
    static class AddPersonFace extends AsyncTask<String, String, String> {
        private FaceServiceClient faceServiceClient =
                new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0", "068682577ef84250b24aafbc3b2c8e66");
        String group_ID = null;
        UUID user_ID = null;
        String user_data = null;
        String target_face = null;
        FaceRectangle face = null;

        AddPersonFace(String group_ID, UUID user_ID, String user_data, String target_face, FaceRectangle face){
            this.group_ID = group_ID;
            this.user_ID= user_ID;
            this.user_data= user_data;
            this.target_face= target_face;
            this.face= face;
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                publishProgress("Adding face to Person...");
                String persistedFaceID = faceServiceClient.addPersonFace(
                        this.group_ID,
                        this.user_ID,
                        this.user_data,
                        this.target_face,
                        this.face).toString();
                publishProgress("Created Person with ID: " + persistedFaceID);
                return persistedFaceID;
            } catch (Exception e) {
                publishProgress("Creation failed: " + e);
                return null;
            }
        }
    }
}
