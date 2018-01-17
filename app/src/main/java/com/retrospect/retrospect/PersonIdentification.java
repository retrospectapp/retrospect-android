package com.retrospect.retrospect;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.*;

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
    @BindView(R.id.editText2) EditText group_name_text;
    @BindView(R.id.editText) EditText person_name_text;
    private final int PICK_IMAGE = 1;
    private final String BTN_TAG = "BTN_PRESS";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identification);
        ButterKnife.bind(this);

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!group_name_text.getText().toString().equals("")){
                    String group_name = group_name_text.getText().toString();
                    Log.d(BTN_TAG, "Attempting to create Person Group");
                    new CreateGroup().execute(generateID(group_name), group_name, null);
                }
            }
        });
        createPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(!group_name_text.getText().toString().equals("") && !person_name_text.getText().toString().equals("")){
                        String group_name = group_name_text.getText().toString();
                        String person_name = person_name_text.getText().toString();
                        new CreatePerson().execute(generateID(group_name), person_name, null);
                }
            }
        });
        addPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!group_name_text.getText().toString().equals("") && !person_name_text.getText().toString().equals("")){
                    String group_ID = generateID(group_name_text.getText().toString());
                    UUID person_ID = UUID.fromString(generateID(person_name_text.getText().toString()));
                    String user_data = null; //fix
                    FaceRectangle face = null; //fix
                    String targetFace = null; //fix
                    new AddPersonFace(group_ID,person_ID,user_data, targetFace, face).execute();
                }
            }
        });

        displayDetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(BTN_TAG, "Attempting to list all groups.");
                new ListGroups(new ListGroups.ListResponse() {
                    @Override
                    public void processFinished(PersonGroup[] result) {
                        for(PersonGroup group: result){
                            Log.d("PERSON GROUP", group.name);
                        }
                    }
                }).execute();
            }
        });
    }

    public String generateID(String val){
        char[] ch = val.toCharArray();
        int sum = 0;
        for (char c : ch) {
            sum += c - 'A' + 1;
        }
        return String.valueOf(sum);
    }

    static class CreateGroup extends AsyncTask<String, String, String> {
        private FaceServiceClient faceServiceClient =
                new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0", "068682577ef84250b24aafbc3b2c8e66");

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.d("API_CALL", "API is being called.");
                faceServiceClient.createPersonGroup(
                        params[0],
                        params[1],
                        params[2]
                );
                Log.d("API_RESPONSE", "Created group with name: " + params[1]);
                return "";
            } catch (Exception e) {
                Log.d("API_CALL", "API call failed due to: " + e);
                return null;
            }
        }
    }

    static class ListGroups extends AsyncTask<String, String, PersonGroup[]> {
        public interface ListResponse {
            void processFinished(PersonGroup[] result);
        }

        public ListResponse listResponse = null;

        public ListGroups(ListResponse listResponse){
            this.listResponse = listResponse;
        }

        private FaceServiceClient faceServiceClient =
                new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0", "068682577ef84250b24aafbc3b2c8e66");
        protected PersonGroup[] doInBackground(String... params){
            try{
                PersonGroup[] groups = faceServiceClient.getPersonGroups();
                Log.d("API_RESPONSE", "Listed available person groups");
                return groups;
            }catch(Exception e){
                Log.d("API_RESPONSE", "Could not list person groups.");
                return null;
            }
        }

        @Override
        protected void onPostExecute(PersonGroup[] personGroups) {
            super.onPostExecute(personGroups);
            listResponse.processFinished(personGroups);
        }
    }

    static class CreatePerson extends AsyncTask<String, String, String> {
        private FaceServiceClient faceServiceClient =
                new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0", "068682577ef84250b24aafbc3b2c8e66");

        @Override
        protected String doInBackground(String... params) {
            try {
                publishProgress("Creating Person...");
                String personID = faceServiceClient.createPerson(
                        params[0], //personGroupID
                        params[1], //Name
                        null  //Optional: User Data
                ).personId.toString();
                Log.d("API_RESPONSE", "Created person with name: " + params[1] + "and ID: " + personID);
                return personID;
            } catch (Exception e) {
                Log.d("API_RESPONSE", "Could not create person due to error: " + e);
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