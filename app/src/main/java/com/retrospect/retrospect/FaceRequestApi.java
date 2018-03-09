package com.retrospect.retrospect;

import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;
import com.microsoft.projectoxford.face.contract.IdentifyResult;
import com.microsoft.projectoxford.face.contract.PersonGroup;

import java.io.InputStream;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Shreyas Niradi on 1/18/2018
 * Implements the Face API through Asynchronous tasks
 * Tasks completed:
 * Detect Face
 * Identify Person
 * Create Person Group
 * List Groups
 * Create Person
 * Delete Person
 * Add Person Face
 * Train Person Group
 */

@SuppressWarnings("unused")
public abstract class FaceRequestApi {
    private static FaceServiceClient faceServiceClient =
            new FaceServiceRestClient("https://westcentralus.api.cognitive.microsoft.com/face/v1.0",
                    "068682577ef84250b24aafbc3b2c8e66");

    static class DetectFace extends AsyncTask<InputStream, String, Face[]> {
        private static final String TAG = "DETECT_FACE";

        public interface DetectFaceResponse {
            void processFinished(Face[] result);
        }

        private DetectFaceResponse detectFaceResponse = null;

        private DetectFace(DetectFaceResponse detectFaceResponse){
            this.detectFaceResponse = detectFaceResponse;
        }

        @Override
        protected void onPostExecute(Face[] faces) {
            detectFaceResponse.processFinished(faces);
        }

        @Override
        protected Face[] doInBackground(InputStream... params) {
            try {
                Face[] result = faceServiceClient.detect(
                        params[0],
                        true,         // returnFaceId
                        false,        // returnFaceLandmarks
                        null           // returnFaceAttributes: a string like "age, gender"
                );
                if (result == null) {
                    Log.d(TAG, "No faces found");
                    return null;
                }
                Log.d(TAG,String.format(Locale.US, "Detection Finished. %d face(s) detected",
                                result.length));
                return result;
            } catch (Exception e) {
                Log.d(TAG,"Detection failed", e);
                return null;
            }
        }
    }

    static class IdentifyPerson extends AsyncTask<Void, Void, IdentifyResult[]> {
        private static final String TAG = "DETECT_FACE";
        private String personGroupId;
        private UUID[] faceIds;
        private int maxNumOfCandidatesReturned;
        IdentifyPerson(String personGroupId, UUID[] faceIds, int maxNumOfCandidatesReturned){
            this.personGroupId = personGroupId;
            this. faceIds = faceIds;
            this.maxNumOfCandidatesReturned = maxNumOfCandidatesReturned;
        }

        @Override
        protected IdentifyResult[] doInBackground(Void... voids){
            try{
                IdentifyResult[] identifyResult = faceServiceClient.identity(
                        personGroupId,
                        faceIds,
                        maxNumOfCandidatesReturned);
                if(identifyResult == null) {
                    Log.d(TAG, "Could not identify anyone");
                    return null;
                } else{
                    Log.d(TAG, String.format(Locale.US,
                            "Identification finished: Identified %d people",
                            identifyResult.length));
                    return identifyResult;
                }

            } catch(Exception e){
                Log.d(TAG, "Identification failed", e);
                return null;
            }
        }
    }

    static class CreatePersonGroup extends AsyncTask<Void, Void, Void> {
        private static final String TAG = "CREATE_GROUP";

        private String personGroupId;
        private String groupName;
        private String userData;

        CreatePersonGroup(String personGroupId, String groupName, String userData){
            this.personGroupId = personGroupId;
            this.groupName = groupName;
            this.userData = userData;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                faceServiceClient.createPersonGroup(personGroupId, groupName, userData);
                Log.d(TAG, "Created group");
                return null;
            } catch (Exception e) {
                Log.d(TAG, "Could not create group", e);
                return null;
            }
        }
    }

    static class ListGroups extends AsyncTask<String, String, PersonGroup[]> {
        private static final String TAG = "LIST_GROUPS";

        public interface ListResponse {
            void processFinished(PersonGroup[] result);
        }

        ListResponse listResponse = null;

        public ListGroups(ListResponse listResponse){
            this.listResponse = listResponse;
        }

        protected PersonGroup[] doInBackground(String... params){
            try{
                PersonGroup[] groups = faceServiceClient.getPersonGroups();
                Log.d(TAG, "Listed available person groups");
                return groups;
            }catch(Exception e){
                Log.d(TAG, "Could not list person groups.");
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
        private static final String TAG = "CREATE_PERSON";

        @Override
        protected String doInBackground(String... params) {
            try {
                String personID = faceServiceClient.createPerson(
                        params[0], //personGroupID
                        params[1], //Name
                        null  //Optional: User Data
                ).personId.toString();
                Log.d(TAG, "Created person with ID: " + personID);
                return personID;
            } catch (Exception e) {
                Log.d(TAG, "Could not create person due to error: " + e);
                return null;
            }
        }
    }

    static class DeletePerson extends AsyncTask<Void, Void, Void> {
        private static final String TAG = "DELETE_PERSON";

        private String personGroupId;
        private UUID personId;

        DeletePerson(String personGroupId, UUID personId){
            this.personGroupId = personGroupId;
            this.personId = personId;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                faceServiceClient.deletePerson(personGroupId, personId);
                Log.d(TAG, "Deleted person with ID: " + personId);
                return null;
            } catch (Exception e) {
                Log.d(TAG, "Could not delete person", e);
                return null;
            }
        }
    }

    static class AddPersonFace extends AsyncTask<String, String, String> {
        private static final String TAG = "ADD_PERSON_FACE";

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
                String persistedFaceID = faceServiceClient.addPersonFace(
                        this.group_ID,
                        this.user_ID,
                        this.img,
                        this.user_data,
                        this.target_face).toString();
                Log.d(TAG,"Added face to Person");
                return persistedFaceID;
            } catch (Exception e) {
                Log.d(TAG,"Could not upload Face to Person", e);
                return null;
            }
        }
    }

    static class TrainPersonGroup extends AsyncTask<String, Void, Void> {
        private static final String TAG = "TRAIN_GROUP";

        private String personGroupId;
        TrainPersonGroup(String personGroupId){
            this.personGroupId = personGroupId;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                faceServiceClient.trainPersonGroup(personGroupId);
                String trainingStatus = faceServiceClient.getPersonGroupTrainingStatus(personGroupId).message;
                Log.d(TAG, trainingStatus);
                return null;
            } catch (Exception e) {
                Log.d(TAG, "Could not train person group", e);
                return null;
            }
        }
    }
}
