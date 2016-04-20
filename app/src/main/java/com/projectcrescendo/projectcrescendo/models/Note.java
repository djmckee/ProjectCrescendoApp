package com.projectcrescendo.projectcrescendo.models;


/**
 * The note class is a class to model a musical note. It contains all the data about the note -
 * and the data held by this class can be changed by the user in the app.
 * <p>
 * Pitch of the note in the form A0, F4 etc...
 * Could be represented as int (only three option) or enum?
 * How loud the note is (again could be enum or int)
 * Accent, Staccato or not (again could enum or int);
 * How long the note should last (relative to the bar?) - (1.0 = a bar)
 * Pitch should be passed in constructor, if no dynamic is given one should be assumed (mf).
 * <p>
 * Originally created by Charlie Marcus.
 * Modified by Dylan McKee.
 *
 */
public interface Note {

    /**
     * An array of possible note lengths in terms of bar length, expressed as a Double.
     */
    double[] VALID_NOTE_LENGTHS = {0.5, 1.0, 2.0, 3.0, 4.0};

    /**
     * Returns the pitch of the current note as a String
     *
     * @return the pitch of the current note as a String
     */
    String getPitch();

    /**
     * Sets the pitch of the note.
     *
     * @param p the pitch of the note as a human readable String (e.g. 'C0')
     */
    void setPitch(String p);

    /**
     * Adds an accidental to the current note.
     *
     * @param a the note's Accidental
     */
    void addAccidental(Accidental a);

    /**
     * Returns the note's accidental
     *
     * @return the note's Accidental value.
     */
    Accidental getAccidental();

    /**
     * Removes the note's accidental (setting it back to a Natural - default).
     */
    void removeAccidental();

    /**
     * Adds an intonation to the note.
     *
     * @param i the intonation to add
     */
    void addIntonation(Intonation i);

    /**
     * Returns the current note's intonation
     *
     * @return the intonation of the current note
     */
    Intonation getIntonation();

    /**
     * Removes the note's intonation (setting it back to the default of None).
     */
    void removeIntonation();

    /**
     * Returns the dynamic of the current note.
     *
     * @return the note's Dynamic
     */
    Dynamic getDynamic();

    /**
     * Sets a dynamic for the current note.
     *
     * @param d the new Dynamic for the note.
     */
    void setDynamic(Dynamic d);

    /**
     * Returns the length of the current note.
     *
     * @return the current note's length.
     */
    double getLength();

    /**
     * Sets the length of the current note, in terms of bar length
     *
     * @param l the length of the note
     */
    void setLength(double l);

    /**
     * Return a cloned instance of this Note, with the same content but a different memory address.
     *
     * @return a cloned Note instance; logically identical but with a unique memory address.
     * @throws CloneNotSupportedException
     */
    Note clone() throws CloneNotSupportedException;

}