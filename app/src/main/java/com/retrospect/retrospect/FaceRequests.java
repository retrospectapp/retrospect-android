package com.retrospect.retrospect;

import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.CreatePersonResult;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;
import com.microsoft.projectoxford.face.contract.IdentifyResult;
import com.microsoft.projectoxford.face.contract.PersonGroup;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Shreyas on 1/18/2018.
 *
 */

public abstract class FaceRequests {
    private FaceServiceClient faceServiceClient =
            new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0",
                    "068682577ef84250b24aafbc3b2c8e66");
    Face[] detectFace(InputStream imageStream, boolean returnFaceId, boolean returnFaceLandmarks, FaceServiceClient.FaceAttributeType[] returnFaceAttributes) throws ClientException, IOException {
        try{
            return faceServiceClient.detect(imageStream,returnFaceId, returnFaceLandmarks, returnFaceAttributes);
        } catch(Exception e){
            e.printStackTrace();
            return new Face[0];
        }
    }
    IdentifyResult[] identity(String personGroupId, UUID[] faceIds, int maxNumOfCandidatesReturned) throws ClientException, IOException{
        try{
            return faceServiceClient.identity(personGroupId, faceIds, maxNumOfCandidatesReturned);
        } catch(Exception e){
            e.printStackTrace();
            return new IdentifyResult[0];
        }
    }
    CreatePersonResult createPerson(String personGroupId, String name, String userData) throws ClientException, IOException{
        try{
            return faceServiceClient.createPerson(personGroupId, name, userData);
        } catch(Exception e){
            e.printStackTrace();
            return new CreatePersonResult();
        }
    }
    void addPersonFace(String personGroupId, UUID personId, InputStream imageStream, String userData, FaceRectangle targetFace) throws ClientException, IOException{
        try{
            faceServiceClient.addPersonFace(personGroupId, personId, imageStream, userData, targetFace);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    void trainPersonGroup(String personGroupId) throws ClientException, IOException{
        try{
            faceServiceClient.trainPersonGroup(personGroupId);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    static class DetectFace extends AsyncTask<InputStream, String, Face[]> {
        private FaceServiceClient faceServiceClient =
                new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0",
                        "068682577ef84250b24aafbc3b2c8e66");
        public interface DetectFaceResponse {
            void processFinished(Face[] result);
        }

        private PersonIdentification.DetectFace.DetectFaceResponse detectFaceResponse = null;

        private DetectFace(PersonIdentification.DetectFace.DetectFaceResponse detectFaceResponse){
            this.detectFaceResponse = detectFaceResponse;
        }

        @Override
        protected void onPostExecute(Face[] faces) {
            detectFaceResponse.processFinished(faces);
        }

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
                new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0",
                        "068682577ef84250b24aafbc3b2c8e66");
        String group_ID = null;
        UUID user_ID = null;
        InputStream img = null;
        String user_data = null;
        FaceRectangle target_face = null;

        AddPersonFace(String group_ID, UUID user_ID, InputStream img, String user_data, FaceRectangle target_face){
            this.group_ID = group_ID;
            this.user_ID= user_ID;
            this.img = img;
            this.user_data= user_data;
            this.target_face= target_face;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                publishProgress("Adding face to Person...");
                String persistedFaceID = faceServiceClient.addPersonFace(
                        this.group_ID,
                        this.user_ID,
                        this.img,
                        this.user_data,
                        this.target_face).toString();
                Log.d("API_RESPONSE","Added face to Person with persistedFaceID: " + persistedFaceID);
                return persistedFaceID;
            } catch (Exception e) {
                Log.d("API_RESPONSE","Could not upload Face to Person due to: " + e.toString());
                return null;
            }
        }
    }

    static class TrainPersonGroup extends AsyncTask<String, String, String> {
        private FaceServiceClient faceServiceClient =
                new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0",
                        "068682577ef84250b24aafbc3b2c8e66");

        @Override
        protected String doInBackground(String... params) {
            try {
                publishProgress("Training Person Group...");
                faceServiceClient.trainPersonGroup(params[0]);
                String trainingStatus = faceServiceClient.getPersonGroupTrainingStatus(params[0]).status.toString();
                Log.d("API_RESPONSE", "Person Group Training Status for group with ID: "+  params[0] + " " + trainingStatus);
                return "";
            } catch (Exception e) {
                Log.d("API_RESPONSE", "Could not train person group due to: " + e);
                return null;
            }
        }
    }
}
