package com.projectcrescendo.projectcrescendo.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to model a Clef, which contains a list of Bars of a variable length, which themselves
 * contain a finite number of beats (passed into this class in the constructor method).
 * <p/>
 * The Clef class handles dynamic resizing of the Bars, moving Beats around between bars as required
 * depending upon the length of their time signature.
 * <p/>
 * Created by Dylan McKee on 01/03/16.
 */
public class Clef {

    /**
     * A beat is 0.5 Bars long.
     */
    private static final double LENGTH_OF_BEAT = 0.5;
    /**
     * The length of the Clef (i.e. the number of Beats on the Clef), set in the constructor of the
     * class and never changed afterwards.
     */
    final private int lengthOfClef;
    /**
     * The current length of the Bar (based on the selected time signature),
     * initially set to a default value of 4.
     */
    private int barLength = 4;

    /**
     * A list containing the Bars on the Clef.
     */
    private List<Bar> bars = new ArrayList<Bar>();

    /**
     * The constructor accepts an integer containing the number of Beats for the Clef.
     *
     * @param lengthOfClef the length of the Clef in Beats (i.e. the number of total Beats)
     */
    public Clef(int lengthOfClef) {
        this.lengthOfClef = lengthOfClef;

        changeBarLength(barLength);
    }

    /**
     * This method allows the length of a bar to be changed to whatever is required by the current
     * time signature, it sets the bar length variable to the new value, and then re-arranges the
     * beats so that they are on the correct bar for the new time signature.
     *
     * @param barLength the new bar length.
     */
    public void changeBarLength(int barLength) {
        this.barLength = barLength;

        // Rearrange beats into bars.
        List<Beat> beatHolder = new ArrayList<Beat>();

        for (Bar bar : bars) {
            beatHolder.addAll(bar.getBeats());

        }

        int newNumberOfBeatsPerBar = (int) ((barLength / LENGTH_OF_BEAT) + 0.5);
        Log.d("Clef", "newNumberOfBeatsPerBar: " + newNumberOfBeatsPerBar);

        int numberOfBars = (int) ((lengthOfClef / newNumberOfBeatsPerBar) + 0.5);

        Log.d("Clef", "numberOfBars: " + numberOfBars);

        bars = new ArrayList<Bar>();

        int counter = 0;

        for (int i = 0; i < numberOfBars; i++) {
            Bar newBar = new Bar();

            int nextIncrement = counter + newNumberOfBeatsPerBar;

            for (int j = counter; j < nextIncrement; j++) {
                // Does the beat definitely exist? If so, transfer it, if not, use a blank one.
                if (j < beatHolder.size()) {
                    // Add beat at 'j' in beatHolder to the new bar
                    Beat beat = beatHolder.get(j);

                    newBar.addBeat(beat);


                } else {
                    newBar.addBeat(new Beat());
                }
            }

            counter = nextIncrement;

            bars.add(newBar);
        }


    }

    /**
     * Returns all of the Bars on the current Clef.
     *
     * @return a List containing all of the Bars on the current Clef.
     */
    public List<Bar> getBars() {
        return bars;
    }

    /**
     * Set the bars List to a new list of bars for the current Clef.
     *
     * @param bars the new list of Bar objects for this clef.
     */
    public void setBars(List<Bar> bars) {
        this.bars = bars;
    }

    /**
     * Returns a list containing all of the Beats on all of the Bars on the current Clef, in
     * sequential order.
     *
     * @return a List containing all of the Beats on all of the Bars on the Clef.
     */
    public List<Beat> getBeats() {
        List<Beat> beats = new ArrayList<Beat>();

        for (Bar bar : bars) {
            beats.addAll(bar.getBeats());
        }

        return beats;

    }

    /**
     * Checks whether or not the object 'o' passed to this method is equal in type and in terms of
     * being logically equal to this Clef instance.
     *
     * @param o the object to compare to this Clef instance.
     * @return a boolean indicating whether or not 'o' is equal to this exact Clef instance.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clef)) return false;

        Clef clef = (Clef) o;

        return lengthOfClef == clef.lengthOfClef && barLength == clef.barLength && (getBars() != null ? getBars().equals(clef.getBars()) : clef.getBars() == null);

    }

    /**
     * Returns an integer that contains a hash to uniquely identify instances of the Clef object,
     * and returns a hash number. If the instances are logically equal, the hashes will be the same.
     *
     * @return an integer containing a hash that represents this Clef instance.
     */
    @Override
    public int hashCode() {
        int result = lengthOfClef;
        result = 31 * result + barLength;
        result = 31 * result + (getBars() != null ? getBars().hashCode() : 0);
        return result;
    }

    /**
     * Returns a human readable string representation of the current clef.
     *
     * @return a human readable string representation of the current clef.
     */
    @Override
    public String toString() {
        return "Clef{" +
                "lengthOfClef=" + lengthOfClef +
                ", barLength=" + barLength +
                ", bars=" + bars +
                '}';
    }

}
