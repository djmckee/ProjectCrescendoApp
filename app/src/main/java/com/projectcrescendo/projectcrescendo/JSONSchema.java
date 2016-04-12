package com.projectcrescendo.projectcrescendo;

/**
 * A class to hold the field names of the data within our JSON encoded objects (such as JSON encoded
 * tutorials, sets of instructions, and arrays of valid notes for beats).
 * <p>
 * This class helps to create a better level of abstraction and improves the use of constants.
 * <p>
 * The constants in this file MUST match up to the JSON within the database's 'tutorials' table.
 * <p>
 * Created by Dylan McKee on 12/04/16.
 */
public final class JSONSchema {
    /**
     * The name of the field containing instruction text entries within our JSON encoded tutorials.
     */
    public static final String TUTORIAL_INSTRUCTIONS_FIELD = "instruction_text";

    /**
     * The name of the field containing the pattern matches within our JSON encoded tutorials.
     */
    public static final String TUTORIAL_MATCH_INDEX_FIELD = "pattern_match_index";

    /**
     * The name of the field containing the notes array within our JSON encoded tutorials.
     */
    public static final String TUTORIAL_NOTES_ARRAY_FIELD = "notes";

    /**
     * The name of the field containing the note name within our JSON encoded notes.
     */
    public static final String NOTE_NAME_FIELD = "name";

    /**
     * The name of the field containing the note length within our JSON encoded notes.
     */
    public static final String NOTE_LENGTH_FIELD = "length";

    /**
     * The name of the field containing a boolean indicating if the note should be pre-populated on
     * the grid or note within our JSON encoded notes.
     */
    public static final String NOTE_PREPOPULATED_FIELD = "is_prepopulated";

    /**
     * The name of the field containing the intontation (i.e. Expression) within our JSON encoded
     * beats.
     */
    public static final String BEAT_INTONATION_FIELD = "expression";

}
