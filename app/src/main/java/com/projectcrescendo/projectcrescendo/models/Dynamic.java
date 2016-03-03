package com.projectcrescendo.projectcrescendo.models;

/**
 * An enum of values to represent the various Dynamics a note can have.
 * Created by Dylan McKee on 27/11/15.
 */
public enum Dynamic {
    Forte,
    Piano,
    MezzoForte,
    MezzoPiano,
    Pianissimo,
    Fortissimo,
    Crescendo,
    Decrescendo;

    /**
     * A helper method to return the Dynamic associated with the integer passed into it
     * (association done by database primary key).
     */
    public static Dynamic getDynamicWithID(int dynamicId) {
        // I looked up int to enum casting in Java at https://stackoverflow.com/questions/5878952/cast-int-to-enum-in-java
        return Dynamic.values()[dynamicId];

    }

    /**
     * A helper method to return the index of the Dynamic (allowing Dynamic enum values
     * to be converted to int's, for example for database storage).
     */
    public static int integerValue(Dynamic dynamic) {

        for (int i = 0; i < Dynamic.values().length; i++) {
            Dynamic iteratedValue = Dynamic.values()[i];
            if (iteratedValue == dynamic) {
                return i;
            }
        }

        // Not valid.
        return -1;

    }

}

