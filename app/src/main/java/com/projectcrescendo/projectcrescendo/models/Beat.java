package com.projectcrescendo.projectcrescendo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * A model class containing an Intonation, and a list of notes (with a range of 0-5).
 * Created by Dylan McKee on 25/02/16.
 */
public class Beat {

    /**
     * A list consisting of between 0 and 5 notes that belong in this beat.
     */
    private List<Note> notes = new ArrayList<Note>();

    /**
     * The intonation of the current beat.
     */
    private Intonation intonation;

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

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

    public String gridStringRepresentation() {
        String stringRepresentation = new String();

        // Add each note on a new line for display in the grid
        for (Note note : notes) {
            stringRepresentation = stringRepresentation + "\n" + note.getPitch();

        }

        return stringRepresentation;
    }




}
