package com.projectcrescendo.projectcrescendo.models;

/**
 * An enum of values to represent the various Intonations a note can have.
 * Created by Dylan McKee on 27/11/15.
 */
public enum Intonation {
    None,
    Accent,
    Staccato,
    Legato,
    Pedal,
    Rallentando,
    Accelerando;

    /**
     * A helper method to return the Intonation by name.
     *
     * @param intonationNameString the name of the desired Intonation
     * @return an Intonation matching the name passed to this method.
     */
    public static Intonation getIntonationWithName(String intonationNameString) {
        // Don't let case insensitivity break this
        intonationNameString = intonationNameString.toLowerCase();

        switch (intonationNameString) {
            case "accent":
                return Accent;

            case "staccato":
                return Staccato;

            case "legato":
                return Legato;

            case "pedal":
                return Pedal;

            case "rallentando":
                return Rallentando;

            case "accelerando":
                return Accelerando;

            default:
                return None;

        }
    }

    /**
     * A helper method to return the Intonation associated with the integer passed into it
     * (association done by database primary key).
     */
    public static Intonation getIntonationWithID(int iId) {
        // I looked up int to enum casting in Java at https://stackoverflow.com/questions/5878952/cast-int-to-enum-in-java
        return Intonation.values()[iId];

    }

    /**
     * A helper method to return the index of the Intonation (allowing Intonation enum values
     * to be converted to int's, for example for database storage).
     */
    public static int integerValue(Intonation intonation) {

        for (int i = 0; i < Intonation.values().length; i++) {
            Intonation iteratedValue = Intonation.values()[i];
            if (iteratedValue == intonation) {
                return i;
            }
        }

        // Not valid.
        return -1;

    }

}
