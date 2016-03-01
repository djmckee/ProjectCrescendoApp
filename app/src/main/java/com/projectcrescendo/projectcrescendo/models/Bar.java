package com.projectcrescendo.projectcrescendo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * A model class, made up of a list of beat objects.
 * Created by Dylan McKee on 25/02/16.
 */
public class Bar {

    private List<Beat> beats = new ArrayList<Beat>();

    /**
     * Constructs a new Bar, asking for a length in beats.
     * @param length
     */
    public Bar(int length) {
        for (int i = 0; i < length; i++) {
            Beat newBeat = new Beat();
            beats.add(newBeat);
        }

    }

    /**
     * Constructs a new Bar with a blank beats instance.
     */
    public Bar() {

    }

    public void addBeat(Beat newBeat) {
        beats.add(newBeat);
    }

    public List<Beat> getBeats() {
        return beats;
    }

    public List<Note> getAllNotesOnBar() {
        List<Note> notes = new ArrayList<Note>();

        for (Beat beat : beats) {
            notes.addAll(beat.getNotes());
        }

        return notes;

    }

}
