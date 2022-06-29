package com.eguo.lullabyes.Content;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;


public class TailePostId {
    @Exclude
    public String TailePostId;

    public <T extends TailePostId> T withId(@NonNull final String id) {
        this.TailePostId = id;
        return (T) this;
    }
}
