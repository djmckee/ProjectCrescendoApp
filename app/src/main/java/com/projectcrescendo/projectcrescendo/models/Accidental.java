package com.projectcrescendo.projectcrescendo.models;

/**
 * An enum of values to represent the various Accidentals a note can have.
 * Created by Dylan McKee on 27/11/15.
 */
public enum Accidental {
    Sharp,
    Flat,
    Natural;

    /**
     * A helper method to return the Accidental associated with the integer passed into it
     * (association done by database primary key).
     */
    public static Accidental getAccidentalWithID(int aId) {
        // I looked up int to enum casting in Java at https://stackoverflow.com/questions/5878952/cast-int-to-enum-in-java
        return Accidental.values()[aId];

    }

    /**
     * A helper method to return the index of the Accidental (allowing Accidental enum values
     * to be converted to int's, for example for database storage).
     */
    public static int integerValue(Accidental accidental) {

        for (int i = 0; i < Accidental.values().length; i++) {
            Accidental iteratedValue = Accidental.values()[i];
            if (iteratedValue == accidental) {
                return i;
            }
        }

        // Not valid.
        return -1;

    }

}
