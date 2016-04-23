package com.projectcrescendo.projectcrescendo;

/**
 * A class to hold the SQL Queries used within the model manager classes to retrieve data from the
 * SQLite Database, as constant Strings. This helps to create a better level of abstraction and
 * improves the use of constants.
 * <p/>
 * The constants in this file MUST match up to the SQLite database's current schema.
 * <p/>
 * Created by Dylan McKee on 12/04/16.
 */
final class SQLQueries {
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
     * Select all staves from the stave table.
     */
    public static final String SELECT_ALL_STAVES = "SELECT * FROM stave";

    /**
     * Select all bars with the specified stave ID.
     */
    public static final String SELECT_BARS_WITH_STAVE_ID = "SELECT * from bar WHERE stave_id = %d";

    /**
     * Select all beats with the specified bar ID.
     */
    public static final String SELECT_BEATS_WITH_BAR_ID = "SELECT * from beat WHERE bar_id = %d";

    /**
     * Select all notes with the specified beat ID.
     */
    public static final String SELECT_NOTES_WITH_BEAT_ID = "SELECT * from notes WHERE beat_id = %d";

    /**
     * Select intonation with the specified intonation ID.
     */
    public static final String SELECT_INTONATION_WITH_ID = "SELECT * from intonation WHERE id = %d";

    /**
     * Select note name with the specified note name ID.
     */
    public static final String SELECT_NOTE_NAME_WITH_ID = "SELECT * from note_names WHERE id = %d";

    /**
     * Select note name with the specified note name string.
     */
    public static final String SELECT_NOTE_NAME_WITH_NAME = "SELECT * from note_names WHERE name = %s";

    /**
     * Select time signature with the specified numerator and denominator.
     */
    public static final String SELECT_TIME_SIGNATURE_WITH_NUMBERS = "SELECT * from time_signature WHERE first = %d AND second = %d";

    /**
     * Select time signature with the specified id.
     */
    public static final String SELECT_TIME_SIGNATURE_WITH_ID = "SELECT * from time_signature WHERE id = %d";

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
     * The name of the id of the Time Signature entirety.
     */
    public static final String TIME_SIGNATURE_ID_COLUMN = "id";

    /**
     * The name of the first field of the Time Signature entirety.
     */
    public static final String TIME_SIGNATURE_ENTIRETY_FIRST_COLUMN = "first";

    /**
     * The name of the second field of the Time Signature entirety.
     */
    public static final String TIME_SIGNATURE_ENTIRETY_SECOND_COLUMN = "second";

    /**
     * The name of the table containing stave instances.
     */
    public static final String STAVE_TABLE_NAME = "stave";

    /**
     * The name of the table containing bar instances.
     */
    public static final String BAR_TABLE_NAME = "bar";

    /**
     * The name of the table containing beat instances.
     */
    public static final String BEAT_TABLE_NAME = "beat";

    /**
     * The name of the table containing note instances.
     */
    public static final String NOTE_TABLE_NAME = "notes";

    /**
     * The name of the column on the Stave entirety that holds the 'time signature ID'
     */
    public static final String STAVE_TIME_SIGNATURE_ID_COLUMN = "time_signature_id";

    /**
     * The name of the id column of the Stave entirety.
     */
    public static final String STAVE_ID_COLUMN = "id";

    /**
     * The name of the name column of the Stave entirety.
     */
    public static final String STAVE_NAME_COLUMN = "name";

    /**
     * The name of the id column of the Bar entirety.
     */
    public static final String BAR_ID_COLUMN = "id";

    /**
     * The name of the number column of the Bar entirety.
     */
    public static final String BAR_NUMBER_COLUMN = "number";

    /**
     * The name of the stave id column of the Bar entirety.
     */
    public static final String BAR_STAVE_ID_COLUMN = "stave_id";

    /**
     * The name of the id column of the Beat entirety.
     */
    public static final String BEAT_ID_COLUMN = "id";

    /**
     * The name of the number column of the Beat entirety.
     */
    public static final String BEAT_NUMBER_COLUMN = "number";

    /**
     * The name of the bar id column of the Beat entirety.
     */
    public static final String BEAT_BAR_ID_COLUMN = "bar_id";

    /**
     * The name of the dynamic id column of the Beat entirety.
     */
    public static final String BEAT_DYNAMIC_COLUMN = "dynamic_id";

    /**
     * The name of the name id column of the Note entirety.
     */
    public static final String NOTE_NAME_ID_COLUMN = "name_id";

    /**
     * The name of the intonation id column of the Note entirety.
     */
    public static final String NOTE_INTONATION_ID_COLUMN = "intonation_id";

    /**
     * The name of the beat id column of the Note entirety.
     */
    public static final String NOTE_BEAT_ID_COLUMN = "beat_id";

    /**
     * The name of the length column of the Note entirety.
     */
    public static final String NOTE_LENGTH_COLUMN = "length";

    /**
     * The name of the id column of the Note Names table.
     */
    public static final String NOTE_NAMES_NAME_ID_COLUMN = "id";

    /**
     * The name of the name column of the Note Names table.
     */
    public static final String NOTE_NAMES_NAME_COLUMN = "name";

    /**
     * The clause to be used during deletion of a Stave by ID.
     */
    public static final String STAVE_DELETE_WHERE_CLAUSE = STAVE_ID_COLUMN + "=";


}
