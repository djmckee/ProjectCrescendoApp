package com.projectcrescendo.projectcrescendo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * A model class, made up of a list of beat objects and a time signature.
 * Created by Dylan McKee on 25/02/16.
 */
public class Bar {

    private List<Beat> beats = new ArrayList<Beat>();

    private int timeSignatureNumerator = 4;

    private int timeSignatureDenominator = 4;

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

    public List<Beat> getBeats() {
        return beats;
    }

    public int getTimeSignatureDenominator() {
        return timeSignatureDenominator;
    }

    public void setTimeSignatureDenominator(int timeSignatureDenominator) {
        this.timeSignatureDenominator = timeSignatureDenominator;
    }

    public int getTimeSignatureNumerator() {
        return timeSignatureNumerator;
    }

    public void setTimeSignatureNumerator(int timeSignatureNumerator) {
        this.timeSignatureNumerator = timeSignatureNumerator;
    }

}
