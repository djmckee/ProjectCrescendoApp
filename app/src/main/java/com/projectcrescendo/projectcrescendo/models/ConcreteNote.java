package com.projectcrescendo.projectcrescendo.models;

/**
 * The note class is a class to model a musical note. It implements NoteInterface.
 * Created by Dylan McKee on 16/11/15.
 */
public class ConcreteNote implements Note {
    // Internal placeholder vars
    private String pitch;
    private Accidental accidental;
    private Intonation intonation;
    private Dynamic dynamic;

    // The length of the note, in seconds.
    private double length;


    public ConcreteNote(String pitch) {
        // Use the pitch value that we've been passed...
        setPitch(pitch);

        // Set up default values.
        accidental = Accidental.Natural;
        intonation = Intonation.None;
        dynamic = Dynamic.MezzoForte;

    }

    @Override
    public void setPitch(String p) {
        pitch = p;
    }

    @Override
    public String getPitch() {
        return pitch;
    }

    @Override
    public void addAccidental(Accidental a) {
        accidental = a;
    }

    @Override
    public Accidental getAccidental() {
        return accidental;
    }

    @Override
    public void removeAccidental() {
        accidental = Accidental.Natural;
    }

    @Override
    public void addIntonation(Intonation i) {
        intonation = i;

    }

    @Override
    public Intonation getIntonation() {
        return intonation;
    }

    @Override
    public void removeIntonation() {
        intonation = Intonation.None;
    }

    @Override
    public void setDynamic(Dynamic d) {
        dynamic = d;
    }

    @Override
    public Dynamic getDynamic() {
        return dynamic;
    }

    @Override
    public void play() {
        // TODO: Play the note!
    }


    @Override
    public void setLength(double l) {
        length = l;

    }
}
