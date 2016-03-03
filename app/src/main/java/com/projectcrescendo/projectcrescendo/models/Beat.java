package com.projectcrescendo.projectcrescendo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * A model class containing an Intonation, and a list of notes (with a range of 0-5).
 * Created by Dylan McKee on 25/02/16.
 */
public class Beat {

    // TODO: Constrain notes list to holding between 0-5 notes no matter what.
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
     * @return a List of Note instnaces in the current beat.
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
        // TODO: ensure notes has <= 5 notes in it!

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
        String stringRepresentation = new String();

        // Add each note on a new line for display in the grid
        for (Note note : notes) {
            stringRepresentation = stringRepresentation + "\n" + note.getPitch();

        }

        return stringRepresentation;
    }


}
