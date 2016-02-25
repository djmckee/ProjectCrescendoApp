package com.projectcrescendo.projectcrescendo.models;

/**
 * A stave is a model object made up of a Time Signature and 2 bar objects.
 * Created by Dylan McKee on 25/02/16.
 */
public class Stave {
    public static int BEATS_PER_STAVE = 4;

    private Bar bar = new Bar(BEATS_PER_STAVE);


    public Bar getBar() {
        return bar;
    }


}
