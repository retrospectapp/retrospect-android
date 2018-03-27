package com.retrospect.retrospect;

import android.os.Parcel;
import android.os.Parcelable;

import com.retrospect.retrospect.model.TimeLineCircle;

import java.util.HashMap;

/**
 * Created by shivam
 */

@SuppressWarnings("unused")
public class Event implements Parcelable {

    private String title;
    private String date;
    private TimeLineCircle mStatus; //whether the circle is filled or not
    private String details;
    private String location;
    private HashMap<String, String[]>peopleInvolved;
    private HashMap<String, String>images;

    public Event() {}

    public Event(String title, String date, TimeLineCircle mStatus, String details, String location, HashMap<String, String[]> peopleInvolved, HashMap<String, String> images) {
        this.title = title;
        this.date = date;
        this.mStatus = mStatus;
        this.details = details;
        this.location = location;
        this.peopleInvolved = peopleInvolved;
        this.images = images;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TimeLineCircle getStatus() {
        return mStatus;
    }

    public void setStatus(TimeLineCircle mStatus) {
        this.mStatus = mStatus;
    }

    public String getDetails(){
        return details;
    }

    public void setDetails(String details){
        this.details = details;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public HashMap<String, String[]> getPeopleInvolved(){
        return peopleInvolved;
    }

    public void setPeopleInvolved(HashMap<String, String[]> peopleInvolved){ this.peopleInvolved = peopleInvolved; }

    public HashMap<String, String> getImages(){
        return images;
    }

    public void setImage(HashMap<String, String> images){
        this.images = images;
    }

    public String toString(){
        String images = "";
        return "Title: " + title + "\n" + "Date: " + date + "\n" + "Details: " + details + "\n" + "Image URL:" + images + "\n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeInt(this.mStatus == null ? -1 : this.mStatus.ordinal());
    }

    Event(Parcel in) {
        this.title = in.readString();
        this.date = in.readString();
        int tmpMStatus = in.readInt();
        this.mStatus = tmpMStatus == -1 ? null : TimeLineCircle.values()[tmpMStatus];
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
