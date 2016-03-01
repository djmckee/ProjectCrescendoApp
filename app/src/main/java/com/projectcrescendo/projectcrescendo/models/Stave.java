package com.projectcrescendo.projectcrescendo.models;

/**
 * A stave is a model object made up of a Time Signature and Bar objects.
 * Created by Dylan McKee on 25/02/16.
 */
public class Stave {
    public static int BEATS_PER_STAVE = 4;

    private Bar upperBar = new Bar(BEATS_PER_STAVE);
    private Bar lowerBar = new Bar(BEATS_PER_STAVE);

    private int timeSignatureNumerator = 4;

    private int timeSignatureDenominator = 4;

    public Bar getUpperBar() {
        return upperBar;
    }

    public Bar getLowerBar() {
        return lowerBar;
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
