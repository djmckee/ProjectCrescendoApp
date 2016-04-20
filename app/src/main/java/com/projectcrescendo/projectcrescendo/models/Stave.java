package com.projectcrescendo.projectcrescendo.models;

/**
 * A stave is a model object made up of a Time Signature and Bar objects.
 * <p>
 * Created by Dylan McKee on 25/02/16.
 */
public class Stave {

    /**
     * The number of beats for the total Stave. Must be divisible by 4, 6 and 8 to be valid because
     * of possible Bar lengths.
     */
    private static final int NUMBER_OF_BEATS = 384;

    /**
     * The number of Clefs in the current stave - always 2 for our purposes - because there's 2
     * hands playing the piano.
     */
    private static final int NUMBER_OF_CLEFS = 2;

    /**
     * The number of beats per Clef - half of the number of beats on the total Stave, because there
     * is 2 clefs.
     */
    public static final int BEATS_PER_ROW = NUMBER_OF_BEATS / NUMBER_OF_CLEFS;

    /**
     * The upper clef in the stave.
     */
    private final Clef upperClef = new Clef(BEATS_PER_ROW);

    /**
     * The lower clef in the stave.
     */
    private final Clef lowerClef = new Clef(BEATS_PER_ROW);

    /**
     * The top number in the stave's current time signature, initialised to a default value of 4.
     */
    private int timeSignatureNumerator = 4;

    /**
     * The bottom number in the stave's current time signature, initialised to a default of 4.
     */
    private int timeSignatureDenominator = 4;

    /**
     * An ID of the Stave instance if it has been read in from the database; defaults to -1 until set.
     */
    private int id;

    /**
     * A name for the Stave instance if it has been read in from the database.
     */
    private String name;

    /**
     * Returns the upper clef on the stave.
     *
     * @return the upper Clef of the Stave.
     */
    public Clef getUpperClef() {
        return upperClef;
    }

    /**
     * Returns the lower clef on the stave.
     *
     * @return the lower Clef of the Stave.
     */
    public Clef getLowerClef() {
        return lowerClef;
    }

    /**
     * Returns the lower number in the current stave's time signature.
     *
     * @return the lower number in the stave's time signature.
     */
    public int getTimeSignatureDenominator() {
        return timeSignatureDenominator;
    }

    /**
     * Sets the lower number in the current stave's time signature.
     *
     * @param timeSignatureDenominator the lower number of the stave's time signature.
     */
    public void setTimeSignatureDenominator(int timeSignatureDenominator) {
        this.timeSignatureDenominator = timeSignatureDenominator;

    }

    /**
     * Returns the upper number of the stave's current time signature.
     *
     * @return the upper number of the stave's current time signature.
     */
    public int getTimeSignatureNumerator() {
        return timeSignatureNumerator;
    }

    /**
     * Sets the upper number of the stave's current time signature, changing the bar length of the
     * clefs accordingly too.
     *
     * @param timeSignatureNumerator the upper number of the stave's current time signature.
     */
    public void setTimeSignatureNumerator(int timeSignatureNumerator) {
        // Change the time signature of the clefs, re-arranging the bars into the right number of beats
        upperClef.changeBarLength(timeSignatureNumerator);
        lowerClef.changeBarLength(timeSignatureNumerator);

        this.timeSignatureNumerator = timeSignatureNumerator;
    }


    /**
     * Checks whether or not the object 'o' passed to this method is equal in type and in terms of
     * being logically equal to this Stave instance.
     *
     * @param o the object to compare to this Stave instance.
     * @return a boolean indicating whether or not 'o' is equal to this exact Stave instance.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stave)) return false;

        Stave stave = (Stave) o;

        return getTimeSignatureNumerator() == stave.getTimeSignatureNumerator() && getTimeSignatureDenominator() == stave.getTimeSignatureDenominator() && (getUpperClef() != null ? getUpperClef().equals(stave.getUpperClef()) : stave.getUpperClef() == null && (getLowerClef() != null ? getLowerClef().equals(stave.getLowerClef()) : stave.getLowerClef() == null));

    }

    /**
     * Returns an integer that contains a hash of the fields of the current Stave instance,
     * allowing for a simple check for equality.
     * @return an integer representing the field values of the current Stave instance.
     */
    @Override
    public int hashCode() {
        int result = getUpperClef() != null ? getUpperClef().hashCode() : 0;
        result = 31 * result + (getLowerClef() != null ? getLowerClef().hashCode() : 0);
        result = 31 * result + getTimeSignatureNumerator();
        result = 31 * result + getTimeSignatureDenominator();
        return result;
    }

    /**
     * Returns a human readable string representation of the current Stave.
     * @return a human readable string representation of the current Stave for debug purposes.
     */
    @Override
    public String toString() {
        return "Stave{" +
                "upperClef=" + upperClef +
                ", lowerClef=" + lowerClef +
                ", timeSignatureNumerator=" + timeSignatureNumerator +
                ", timeSignatureDenominator=" + timeSignatureDenominator +
                '}';
    }

    /**
     * Returns the ID of the stave in the app's database, if one exists.
     * Otherwise, returns -1 if the current stave instance has not been read in from the database.
     *
     * @return the database ID of the current stave.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the current stave instance if it has been read in from a database.
     *
     * @param id the database ID of the current stave.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the current stave from the app's database, if set.
     *
     * @return the name of the current stave instance.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name for the current stave instance.
     *
     * @param name the name for the current stave instance.
     */
    public void setName(String name) {
        this.name = name;
    }

}
