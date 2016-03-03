package com.projectcrescendo.projectcrescendo.models;

/**
 * A stave is a model object made up of a Time Signature and Bar objects.
 *
 * Created by Dylan McKee on 25/02/16.
 */
public class Stave {

    /**
     * The number of beats for the total Stave. Must be divisible by 4, 6 and 8 to be valid because
     * of possible Bar lengths.
     */
    public static int NUMBER_OF_BEATS = 384;

    /**
     * The number of Clefs in the current stave - always 2 for our purposes - because there's 2
     * hands playing the piano.
     */
    public static int NUMBER_OF_CLEFS = 2;

    /**
     * The number of beats per Clef - half of the number of beats on the total Stave, because there
     * is 2 clefs.
     */
    public static int BEATS_PER_ROW = NUMBER_OF_BEATS / NUMBER_OF_CLEFS;

    /**
     * The upper clef in the stave.
     */
    private Clef upperClef = new Clef(BEATS_PER_ROW);

    /**
     * The lower clef in the stave.
     */
    private Clef lowerClef = new Clef(BEATS_PER_ROW);

    /**
     * The top number in the stave's current time signature, initialised to a default value of 4.
     */
    private int timeSignatureNumerator = 4;

    /**
     * The bottom number in the stave's current time signature, initialised to a default of 4.
     */
    private int timeSignatureDenominator = 4;

    /**
     * Returns the upper clef on the stave.
     * @return the upper Clef of the Stave.
     */
    public Clef getUpperClef() {
        return upperClef;
    }

    /**
     * Returns the lower clef on the stave.
     * @return the lower Clef of the Stave.
     */
    public Clef getLowerClef() {
        return lowerClef;
    }

    /**
     * Returns the lower number in the current stave's time signature.
     * @return the lower number in the stave's time signature.
     */
    public int getTimeSignatureDenominator() {
        return timeSignatureDenominator;
    }

    /**
     * Sets the lower number in the current stave's time signature.
     * @param timeSignatureDenominator the lower number of the stave's time signature.
     */
    public void setTimeSignatureDenominator(int timeSignatureDenominator) {
        this.timeSignatureDenominator = timeSignatureDenominator;

    }

    /**
     * Returns the upper number of the stave's current time signature.
     * @return the upper number of the stave's current time signature.
     */
    public int getTimeSignatureNumerator() {
        return timeSignatureNumerator;
    }

    /**
     * Sets the upper number of the stave's current time signature, changing the bar length of the
     * clefs accordingly too.
     * @param timeSignatureNumerator the upper number of the stave's current time signature.
     */
    public void setTimeSignatureNumerator(int timeSignatureNumerator) {
        // Change the time signature of the clefs, re-arranging the bars into the right number of beats
        upperClef.changeBarLength(timeSignatureNumerator);
        lowerClef.changeBarLength(timeSignatureNumerator);

        this.timeSignatureNumerator = timeSignatureNumerator;
    }



}
