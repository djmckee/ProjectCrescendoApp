package com.projectcrescendo.projectcrescendo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * A model class containing an Intonation, and a list of notes (with a range of 0-5).
 * Created by Dylan McKee on 25/02/16.
 */
public class Beat implements Cloneable {

    /**
     * A list consisting of between 0 and 5 notes that belong in this beat.
     */
    private List<Note> notes = new ArrayList<Note>();

    /**
     * The intonation of the current beat, defaults to 'None'.
     */
    private Intonation intonation = Intonation.None;

    /**
     * Returns a list of notes in the current beat.
     *
     * @return a List of Note instances in the current beat.
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Sets the notes in the current beat.
     *
     * @param notes the notes that should be contained in the current beat.
     */
    public void setNotes(List<Note> notes) {
        // Perform a quick bounds check, ensure notes has <= 5 notes in it!
        if (notes.size() > 5) {
            // If there's more than 5 notes, use only the first 5 in the array.
            notes = notes.subList(0, 4);

        }

        this.notes = notes;
    }

    /**
     * Returns the intonation of the current beat.
     *
     * @return the Intonation of the current beat.
     */
    public Intonation getIntonation() {
        return intonation;
    }

    /**
     * Sets the intonation for the current beat, and for all of the notes contained within it.
     *
     * @param intonation the new intonation for the beat.
     */
    public void setIntonation(Intonation intonation) {
        this.intonation = intonation;

        // Ensure all notes in this beat have the same intonation
        for (Note note : notes) {
            note.addIntonation(intonation);
        }

    }

    /**
     * Returns a String containing a textual list of the Notes in the current beat,
     * separated by newline characters, intended for display in the NoteGridViewAdapter.
     *
     * @return a String containing a textual list of the Note pitches in the current beat.
     */
    public String gridStringRepresentation() {
        String stringRepresentation = "";

        // Add each note on a new line for display in the grid
        for (Note note : notes) {
            stringRepresentation = stringRepresentation + "\n" + note.getPitch();

        }

        return stringRepresentation;
    }


    /**
     * Checks whether or not the object 'o' passed to this method is equal in type and in terms of
     * being logically equal to this Beat instance.
     *
     * @param o the object to compare to this Beat instance.
     * @return a boolean indicating whether or not 'o' is equal to this exact Beat instance.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Beat)) return false;

        Beat beat = (Beat) o;

        return getNotes() != null ? getNotes().equals(beat.getNotes()) : beat.getNotes() == null && getIntonation() == beat.getIntonation();

    }

    /**
     * Returns a human readable string representation of the current beat.
     *
     * @return a human readable string representation of the current beat; detailing intonation
     * and notes.
     */
    @Override
    public String toString() {
        return "Beat{" +
                "notes=" + notes +
                ", intonation=" + intonation +
                '}';
    }

    /**
     * Returns an integer that contains a hash to uniquely identify instances of the Beat object,
     * and returns a hash number. If the instances are logically equal, the hashes will be the same.
     *
     * @return an integer containing a hash that represents this Beat instance.
     */
    @Override
    public int hashCode() {
        int result = getNotes() != null ? getNotes().hashCode() : 0;
        result = 31 * result + (getIntonation() != null ? getIntonation().hashCode() : 0);
        return result;
    }

    /**
     * Return a cloned instance of this beat, with the same content but a different memory address.
     *
     * @return a cloned Beat instance; logically identical but with a unique memory address.
     * @throws CloneNotSupportedException
     */
    @Override
    public Beat clone() throws CloneNotSupportedException {
        super.clone();

        Beat clonedBeat = new Beat();
        clonedBeat.setIntonation(this.getIntonation());

        List<Note> clonedNotes = new ArrayList<Note>();

        for (Note note : getNotes()) {
            clonedNotes.add(note.clone());
        }

        clonedBeat.setNotes(clonedNotes);

        return clonedBeat;

    }


}
