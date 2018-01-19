package com.retrospect.retrospect;

import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;
import com.microsoft.projectoxford.face.contract.PersonGroup;

import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Shreyas on 1/18/2018.
 *
 */

public abstract class FaceRequests {

    static class detectFace extends AsyncTask<InputStream, String, Face[]> {
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
                if (result == null) {
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

        public PersonIdentification.ListGroups.ListResponse listResponse = null;

        public ListGroups(PersonIdentification.ListGroups.ListResponse listResponse){
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
            //super.onPostEcxecute(personGroups);
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
