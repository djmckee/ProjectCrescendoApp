package com.projectcrescendo.projectcrescendo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * A model class, made up of a list of beat objects, to represent one bar of a variable length based
 * on the time signature.
 * <p>
 * Created by Dylan McKee on 25/02/16.
 */
public class Bar {

    private final List<Beat> beats = new ArrayList<Beat>();

    /**
     * Constructs a new Bar, asking for a length in beats.
     *
     * @param length the length of the bar, expressed in terms of beats.
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
     *
     * @param newBeat the new beat instance to add to the bar.
     */
    public void addBeat(Beat newBeat) {
        beats.add(newBeat);
    }

    /**
     * Returns a list containing all of the Beat objects on the bar.
     *
     * @return a list of the Beat objects on the current bar.
     */
    public List<Beat> getBeats() {
        return beats;
    }

    /**
     * Returns all of the notes on the current bar.
     *
     * @return a List of all of the Note instances on the current Bar.
     */
    public List<Note> getAllNotesOnBar() {
        List<Note> notes = new ArrayList<Note>();

        for (Beat beat : beats) {
            notes.addAll(beat.getNotes());
        }

        return notes;

    }

    /**
     * Checks whether or not the object 'o' passed to this method is equal in type and in terms of
     * being logically equal to this Bar instance.
     *
     * @param o the object to compare to this Bar instance.
     * @return a boolean indicating whether or not 'o' is equal to this exact Bar instance.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bar)) return false;

        Bar bar = (Bar) o;

        return getBeats() != null ? getBeats().equals(bar.getBeats()) : bar.getBeats() == null;

    }

    /**
     * Returns an integer that contains a hash to uniquely identify instances of the Bar object,
     * and returns a hash number. If the instances are logically equal, the hashes will be the same.
     *
     * @return an integer containing a hash that represents this Bar instance.
     */
    @Override
    public int hashCode() {
        return getBeats() != null ? getBeats().hashCode() : 0;
    }

    /**
     * Returns a human readable string representation of the current bar.
     * @return a human readable string representation of the current bar; detailing beats.
     */
    @Override
    public String toString() {
        return "Bar{" +
                "beats=" + beats +
                '}';
    }

}
