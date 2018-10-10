package com.francois.patrick.projet.model;

import java.io.Serializable;

/**
 * Created by Patrick Francois on 13/08/2018.
 */
public class Moods implements Serializable {

    public final int mId;
    private int mMood;
    private String mCommentary;

    /* These is for parameter the Mood Object with his mood and commentary */
    public Moods(int id, int mood, String commentary) {
        this.mId = id;
        this.mMood = mood;
        this.mCommentary = commentary;
    }

    /* These is a getter for retrieve the number of mood */
    public int getMood() {
        return this.mMood;
    }

    /* These is a getter for retrieve the commentary link at mood */
    public String getCommentary() {
        return this.mCommentary;
    }
}
