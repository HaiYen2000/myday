package com.fox.myday.models;

public class Tag {

    public int TAG_ID;
    public String TAG_NAME;
    public String TAG_DESCRIPTION;

    public Tag() {}

    public Tag(String TAG_NAME, String TAG_DESCRIPTION) {
        this.TAG_NAME = TAG_NAME;
        this.TAG_DESCRIPTION = TAG_DESCRIPTION;
    }

    public Tag(int TAG_ID, String TAG_NAME, String TAG_DESCRIPTION) {
        this.TAG_ID = TAG_ID;
        this.TAG_NAME = TAG_NAME;
        this.TAG_DESCRIPTION = TAG_DESCRIPTION;
    }

}
