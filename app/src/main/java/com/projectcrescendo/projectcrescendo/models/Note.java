package com.projectcrescendo.projectcrescendo.models;


/**
 * The note class is a class to model a musical note. It contains all the data about the note -
 * and the data held by this class can be changed by the user in the app.
 * Originally created by Charlie Marcus, added to the app by Dylan McKee on 16/11/15.
 */
public interface Note {

    // Pitch of the note in the form A0, F4 etc...
    // Could be represented as int (only three option) or enum?
    // How loud the note is (again could be enum or int)
    // Accent, Staccato or not (again could enum or int);
    // How long the note should last (relative to the bar?) - (1.0 = a bar)
    // Pitch should be passed in constructor, if no dynamic is given one should be assumed (mf).

    public void setPitch(String p);



    public String getPitch();

    public void addAccidental(Accidental a);

    public Accidental getAccidental();

    public void removeAccidental();

    public void addIntonation(Intonation i);

    public Intonation getIntonation();

    public void removeIntonation();

    public void setDynamic(Dynamic d);

    public Dynamic getDynamic ();

    public void play();

    public void delete();

    public void setLength(double l);

}