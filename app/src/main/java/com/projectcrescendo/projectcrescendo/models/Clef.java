package com.projectcrescendo.projectcrescendo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dylan McKee on 01/03/16.
 */
public class Clef {

    public static final double LENGTH_OF_BEAT = 0.5;

    private int barLength = 4;

    private List<Bar> bars = new ArrayList<Bar>();

    final private int lengthOfClef;

    public Clef(int lengthOfClef) {
        this.lengthOfClef = lengthOfClef;

        changeBarLength(barLength);
    }

    public void changeBarLength(int barLength) {
        this.barLength = barLength;

        // Rearrange beats into bars.
        List<Beat> beatHolder = new ArrayList<Beat>();

        for (Bar bar : bars) {
            beatHolder.addAll(bar.getBeats());

        }


        int newNumberOfBeatsPerBar = (int) ((barLength / LENGTH_OF_BEAT) + 0.5);

        int numberOfBars = (int) ((lengthOfClef / newNumberOfBeatsPerBar) + 0.5);

        bars = new ArrayList<Bar>();

        int counter = 0;

        for (int i = 0; i < numberOfBars; i++) {
            Bar newBar = new Bar();

            int nextIncrement = counter + newNumberOfBeatsPerBar;

            for (int j = counter; j < nextIncrement; j++) {
                if (j > beatHolder.size() && j < beatHolder.size() - 1) {
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

    public List<Bar> getBars() {
        return bars;
    }

    public List<Beat> getBeats() {
        List<Beat> beats = new ArrayList<Beat>();

        for (Bar bar : bars) {
            beats.addAll(bar.getBeats());
        }

        return beats;

    }

}
