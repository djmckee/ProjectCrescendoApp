package com.projectcrescendo.projectcrescendo.models;

/**
 * An enum of values to represent the various Intonations a note can have.
 * Created by Dylan McKee on 27/11/15.
 */
public enum Intonation {
    Accent,
    Staccato,
    Legato,
    Pedal,
    Rallentando,
    Accelerando;

    /**
     * A helper method to return the Intonation associated with the integer passed into it
     * (association done by database primary key).
     */
    public static Intonation getIntonationWithID(int iId) {
        // I looked up int to enum casting in Java at https://stackoverflow.com/questions/5878952/cast-int-to-enum-in-java
        return Intonation.values()[iId];

    }
}
