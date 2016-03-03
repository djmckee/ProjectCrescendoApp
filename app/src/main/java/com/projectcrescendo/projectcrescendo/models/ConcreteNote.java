package com.projectcrescendo.projectcrescendo.models;

/**
 * The note class is a class to model a musical note. It implements NoteInterface.
 * Created by Dylan McKee on 16/11/15.
 */
public class ConcreteNote implements Note {

    /**
     * The pitch of the note as a human readable String (e.g. 'C#')
     */
    private String pitch;

    /**
     * The accidental of the current note.
     */
    private Accidental accidental = Accidental.Natural;

    /**
     * The intonation of the current note (dictated by the intonation of the beat holding the note).
     */
    private Intonation intonation = Intonation.None;

    /**
     * The dynamic of the current note.
     */
    private Dynamic dynamic = Dynamic.MezzoForte;

    /**
     * The length of the note, in terms of the bar.
     */
    private double length = 0.5;

    /**
     * Constructs a note, accepting the note pitch as a human readable string as a paramter.
     *
     * @param pitch the pitch of the note, as a human readable string (for example 'C0').
     */
    public ConcreteNote(String pitch) {
        // Use the pitch value that we've been passed...
        setPitch(pitch);

        // Set up default values.
        accidental = Accidental.Natural;
        intonation = Intonation.None;
        dynamic = Dynamic.MezzoForte;

    }

    /**
     * Returns the pitch of the note.
     *
     * @return a string representing the pitch of the note, human readable.
     */
    @Override
    public String getPitch() {
        return pitch;
    }

    /**
     * Sets the pitch of the note.
     *
     * @param p a new pitch string, describing the human readable pitch of the note (i.e. ('C0').
     */
    @Override
    public void setPitch(String p) {
        pitch = p;
    }

    /**
     * Adds an accidental to the current note.
     *
     * @param a the note's Accidental
     */
    @Override
    public void addAccidental(Accidental a) {
        accidental = a;
    }

    /**
     * Returns the note's accidental
     *
     * @return the note's Accidental value.
     */
    @Override
    public Accidental getAccidental() {
        return accidental;
    }

    /**
     * Removes the note's accidental (setting it back to a Natural - default).
     */
    @Override
    public void removeAccidental() {
        accidental = Accidental.Natural;
    }

    /**
     * Adds an intonation to the note.
     *
     * @param i the intonation to add
     */
    @Override
    public void addIntonation(Intonation i) {
        intonation = i;

    }

    /**
     * Returns the current note's intonation
     *
     * @return the intonation of the current note
     */
    @Override
    public Intonation getIntonation() {
        return intonation;
    }

    /**
     * Removes the note's intonation (setting it back to the default of None).
     */
    @Override
    public void removeIntonation() {
        intonation = Intonation.None;
    }

    /**
     * Returns the dynamic of the current note.
     *
     * @return the note's Dynamic
     */
    @Override
    public Dynamic getDynamic() {
        return dynamic;
    }

    /**
     * Sets a dynamic for the current note.
     *
     * @param d the new Dynamic for the note.
     */
    @Override
    public void setDynamic(Dynamic d) {
        dynamic = d;
    }

    /**
     * Returns the length of the current note.
     *
     * @return the current note's length.
     */
    @Override
    public double getLength() {
        return this.length;
    }

    /**
     * Sets the length of the current note, in terms of bar length
     *
     * @param l the length of the note
     */
    @Override
    public void setLength(double l) {
        length = l;

    }

    /**
     * Returns a human readable representation of the current note for debug purposes.
     *
     * @return a string describing the current note.
     */
    @Override
    public String toString() {
        return "Note - name: " + getPitch() + " length: " + getLength();
    }

}
