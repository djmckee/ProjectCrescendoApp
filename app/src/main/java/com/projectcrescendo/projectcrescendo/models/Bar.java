package com.projectcrescendo.projectcrescendo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * A model class, made up of a list of beat objects, to represent one bar of a variable length based
 * on the time signature.
 *
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

    /**
     * Adds a beat to the bar sequentially in the next available position.
     * @param newBeat the new beat instance to add to the bar.
     */
    public void addBeat(Beat newBeat) {
        beats.add(newBeat);
    }

    /**
     * Returns a list containing all of the Beat objects on the bar.
     * @return a list of the Beat objects on the current bar.
     */
    public List<Beat> getBeats() {
        return beats;
    }

    /**
     * Returns all of the notes on the current bar.
     * @return a List of all of the Note instances on the current Bar.
     */
    public List<Note> getAllNotesOnBar() {
        List<Note> notes = new ArrayList<Note>();

        for (Beat beat : beats) {
            notes.addAll(beat.getNotes());
        }

        return notes;

    }



}
