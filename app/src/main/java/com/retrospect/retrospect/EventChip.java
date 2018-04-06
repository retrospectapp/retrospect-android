package com.retrospect.retrospect;

/**
 * Created by Shivam on 4/5/2018.
 */
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.pchmn.materialchips.model.ChipInterface;

public class EventChip implements ChipInterface{

    private String id;
    private Uri avatarUri;
    private String eventTitle;
    private String additionalInfo;


    public EventChip(String id, Uri avatarUri, String eventTitle, String additionalInfo){
        this.id = id;
        this.avatarUri = avatarUri;
        this.eventTitle = eventTitle;
        this.additionalInfo = additionalInfo;
    }


    @Override
    public Object getId() {
        return id;
    }

    @Override
    public Uri getAvatarUri() {
        return avatarUri;
    }

    @Override
    public Drawable getAvatarDrawable() {
        return null;
    }

    @Override
    public String getLabel() {
        return eventTitle;
    }

    @Override
    public String getInfo() {
        return additionalInfo;
    }
}
