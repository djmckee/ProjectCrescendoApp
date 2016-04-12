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

    /**
     * The name of the title field of the Tutorial entirety.
     */
    public static final String TUTORIAL_ENTIRETY_TITLE_COLUMN = "title";

    /**
     * The name of the instructions field of the Tutorial entirety.
     */
    public static final String TUTORIAL_ENTIRETY_INSTRUCTIONS_COLUMN = "instructions";

    /**
     * The name of the JSON encoded valid grid representation field of the Tutorial activity.
     */
    public static final String TUTORIAL_ENTIRETY_VALID_GRID_COLUMN = "valid_grid_representation";

    /**
     * The name of the name field of the Intonation entirety.
     */
    public static final String INTONATION_ENTIRETY_NAME_COLUMN = "name";

    /**
     * The name of the name field of the Note entirety.
     */
    public static final String NOTE_ENTIRETY_NAME_COLUMN = "name";

    /**
     * The name of the first field of the Time Signature entirety.
     */
    public static final String TIME_SIGNATURE_ENTIRETY_FIRST_COLUMN = "first";

    /**
     * The name of the second field of the Time Signature entirety.
     */
    public static final String TIME_SIGNATURE_ENTIRETY_SECOND_COLUMN = "second";

}
