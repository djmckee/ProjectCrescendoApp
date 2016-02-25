package com.projectcrescendo.projectcrescendo.models;

/**
 * A stave is a model object made up of a Time Signature and 2 bar objects.
 * Created by Dylan McKee on 25/02/16.
 */
public class Stave {
    public static int BEATS_PER_STAVE = 8;
    public static int BEATS_PER_BAR = BEATS_PER_STAVE / 2;

    private Bar upperBar = new Bar(BEATS_PER_BAR);
    private Bar lowerBar = new Bar(BEATS_PER_BAR);


    public Bar getUpperBar() {
        return upperBar;
    }

    public Bar getLowerBar() {
        return lowerBar;
    }

}
