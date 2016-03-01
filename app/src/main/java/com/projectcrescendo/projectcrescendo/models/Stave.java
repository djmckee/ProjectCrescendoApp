package com.projectcrescendo.projectcrescendo.models;

/**
 * A stave is a model object made up of a Time Signature and Bar objects.
 * Created by Dylan McKee on 25/02/16.
 */
public class Stave {

    public static int NUMBER_OF_BEATS = 384;
    public static int BEATS_PER_ROW = NUMBER_OF_BEATS / 2;

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
        // Change the time signature of the clefs, re-arranging the bars into the right number of beats
        upperClef.changeBarLength(timeSignatureNumerator);
        upperClef.changeBarLength(timeSignatureNumerator);

        this.timeSignatureNumerator = timeSignatureNumerator;
    }



}
