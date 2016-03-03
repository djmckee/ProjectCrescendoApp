package com.projectcrescendo.projectcrescendo.models;


/**
 * The note class is a class to model a musical note. It contains all the data about the note -
 * and the data held by this class can be changed by the user in the app.
 *
 * Pitch of the note in the form A0, F4 etc...
 * Could be represented as int (only three option) or enum?
 * How loud the note is (again could be enum or int)
 * Accent, Staccato or not (again could enum or int);
 * How long the note should last (relative to the bar?) - (1.0 = a bar)
 * Pitch should be passed in constructor, if no dynamic is given one should be assumed (mf).
 *
 * Originally created by Charlie Marcus, added to the app by Dylan McKee on 16/11/15.
 */
public interface Note {

    /**
     * An array of possible note lengths in terms of bar length, expressed as a Double.
     */
    double[] VALID_NOTE_LENGTHS = {0.5, 1.0, 2.0, 3.0, 4.0};

    /**
     * Sets the pitch of the note.
     * @param p the pitch of the note as a human readable String (e.g. 'C0')
     */
    void setPitch(String p);

    /**
     * Returns the pitch of the current note as a String
     * @return the pitch of the current note as a String
     */
    String getPitch();

    /**
     * Adds an accidental to the current note.
     * @param a the note's Accidental
     */
    void addAccidental(Accidental a);

    /**
     * Returns the note's accidental
     * @return the note's Accidental value.
     */
    Accidental getAccidental();

    /**
     * Removes the note's accidental (setting it back to a Natural - default).
     */
    void removeAccidental();

    /**
     * Adds an intonation to the note.
     * @param i the intonation to add
     */
    void addIntonation(Intonation i);

    /**
     * Returns the current note's intonation
     * @return the intonation of the current note
     */
    Intonation getIntonation();

    /**
     * Removes the note's intonation (setting it back to the default of None).
     */
    void removeIntonation();

    /**
     * Sets a dynamic for the current note.
     * @param d the new Dynamic for the note.
     */
    void setDynamic(Dynamic d);

    /**
     * Returns the dynamic of the current note.
     * @return the note's Dynamic
     */
    Dynamic getDynamic();

    /**
     * Sets the length of the current note, in terms of bar length
     * @param l the length of the note
     */
    void setLength(double l);

    /**
     * Returns the length of the current note.
     * @return the current note's length.
     */
    double getLength();

}