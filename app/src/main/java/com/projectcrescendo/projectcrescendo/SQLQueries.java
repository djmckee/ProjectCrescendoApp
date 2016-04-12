package com.projectcrescendo.projectcrescendo;

/**
 * A class to hold the SQL Queries used within the model manager classes to retrieve data from the
 * SQLite Database, as constant Strings. This helps to create a better level of abstraction and
 * improves the use of constants.
 * <p>
 * The constants in this file MUST match up to the SQLite database's current schema.
 * <p>
 * Created by Dylan McKee on 12/04/16.
 */
public class SQLQueries {
    /**
     * Select all Intonation entireties from the intonation table.
     */
    public static final String SELECT_ALL_INTONATIONS = "SELECT * FROM intonation";

    /**
     * Select all time signatures from the time_signatures table.
     */
    public static final String SELECT_ALL_TIME_SIGNATURES = "SELECT * FROM time_signature";

    /**
     * Select all note names from the note_names table.
     */
    public static final String SELECT_ALL_NOTE_NAMES = "SELECT * FROM note_names";

    /**
     * Select all tutorials from the tutorials table.
     */
    public static final String SELECT_ALL_TUTORIALS = "SELECT * FROM tutorials";


}
