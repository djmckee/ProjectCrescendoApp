package com.projectcrescendo.projectcrescendo.models;

/**
 * A stave is a model object made up of a Time Signature and Bar objects.
 * Created by Dylan McKee on 25/02/16.
 */
public class Stave {

    public static int BEATS_PER_ROW = 50;

    private Clef upperClef = new Clef(BEATS_PER_ROW);
    private Clef lowerClef = new Clef(BEATS_PER_ROW);

    private int timeSignatureNumerator = 4;

    private int timeSignatureDenominator = 4;

    public Clef getUpperClef() {
        return upperClef;
    }

    public Clef getLowerClef() {
        return lowerClef;
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
