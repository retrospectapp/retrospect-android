package com.retrospect.retrospect;

/**
 * Created by Shivam on 1/18/2018.
 *
 */

public class User extends FaceRequests {

    private String fullName;
    private int age;
    private String uuid;
    private String personID;
    private String patientID;
    private String emailID;
    private String imageURL;
    private boolean isPatient;
    public User() {
    }

    public User(String fullName, int age, String uuid, String personID, String patientID, String emailID, String imageURL, boolean isPatient) {
        this.fullName = fullName;
        this.age = age;
        this.uuid = uuid;
        this.personID = personID;
        this.patientID = patientID;
        this.emailID = emailID;
        this.imageURL = imageURL;
        this.isPatient = isPatient;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean getIsPatient() {
        return isPatient;
    }

    public void setIsPatient(boolean isPatient) {
        this.isPatient = isPatient;
    }

    public String toString() {
        return "Full Name: " + fullName + "\n" + "Age: " + age + "\n" + "UUID: " + uuid + "\n" + "Patient ID: " + patientID + "\n" + "Person ID:" + personID + "\n" + "Email ID" + emailID + "\n" + "Image URL: " + "\n";
    }
}
//
//    @Override
//    public void addPersonFace(String personGroupId, UUID personId, InputStream imageStream, String userData, FaceRectangle targetFace) throws ClientException, IOException{
//        new FaceRequests.AddPersonFace(personGroupId, personId, imageStream, userData, targetFace).execute();
//    }
//
//    @Override
//    public Face[] detectFace(InputStream imageStream, boolean returnFaceId, boolean returnFaceLandmarks, FaceServiceClient.FaceAttributeType[] returnFaceAttributes) throws ClientException, IOException{
//        Face[] faces;
//        new DetectFace(new DetectFace.DetectFaceResponse() {
//            public void processFinished(Face[] faces) {
//                UUID[] faceIDs = new UUID[faces.length];
//                for(int i = 0; i < faces.length; i++){
//                    faceIDs[i] = faces[i].faceId;
//                }
//                try {
//                    faceServiceClient.identity("Test", faceIDs, 3);
//                } catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).execute(imageStream);
//    }
//
//    @Override
//    public IdentifyResult[] identify() {
//        return new IdentifyResult[0];
//    }
//
//    @Override
//    public String createPerson() {
//        return null;
//    }
//
//    @Override
//    public String trainPersonGroup() {
//        return null;
//    }
//}
