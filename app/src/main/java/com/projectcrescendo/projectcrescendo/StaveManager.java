package com.projectcrescendo.projectcrescendo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.projectcrescendo.projectcrescendo.models.Bar;
import com.projectcrescendo.projectcrescendo.models.Beat;
import com.projectcrescendo.projectcrescendo.models.ConcreteNote;
import com.projectcrescendo.projectcrescendo.models.Intonation;
import com.projectcrescendo.projectcrescendo.models.Note;
import com.projectcrescendo.projectcrescendo.models.Stave;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A class to allow Stave instances to be saved to and retrieved from to the app's SQLite database.
 * <p/>
 * Created by Dylan McKee on 12/04/16.
 */
class StaveManager {

    /**
     * The date format to use in the titling of new saved Staves.
     */
    private final static String TITLE_DATE_STRING_FORMAT = "dd-MM-yyyy HH:mm";
    /**
     * The theoretical maximum number of beats on a bar.
     */
    private final static int MAX_NOTES_PER_BEAT = 8;
    /**
     * The default Dynamic for a bar being saved.
     */
    private final static int DEFAULT_DYNAMIC_ID = 3;

    /**
     * The SQLite database manager.
     */
    private final DatabaseManager databaseManager;

    /**
     * Constructs a new Stave Manager.
     *
     * @param context the context that is creating the intonation manager
     */
    public StaveManager(Context context) {
        databaseManager = new DatabaseManager(context);

    }

    /**
     * Saves the stave that it has been passed to the database, and returns the save status as a
     * boolean.
     *
     * @param stave the Stave instance to write to the SQLite database.
     * @return a boolean indicating whether or not the save succeeded.
     */
    public boolean writeStaveToDatabase(Stave stave) {
        /*
        Writing order:
        0. Look up time signature ID
        1. Write stave
        2. Write bars
        3. Write beats
        4. Lookup id of note name for each note
        5. Lookup intonation id for each beat, apply to each note in that beat
        5. Write notes
         */
        SQLiteDatabase database = databaseManager.getWritableDatabase();

        try {
            // Lookup time signature
            long timeSignatureId = -1;

            String timeSignatureQuery = String.format(Locale.UK, SQLQueries.SELECT_TIME_SIGNATURE_WITH_NUMBERS, stave.getTimeSignatureNumerator(), stave.getTimeSignatureDenominator());

            Cursor timeSignatureCursor = database.rawQuery(timeSignatureQuery, null);

            if (timeSignatureCursor != null) {
                if (timeSignatureCursor.moveToFirst()) {
                    timeSignatureId = timeSignatureCursor.getLong(timeSignatureCursor.getColumnIndex(SQLQueries.TIME_SIGNATURE_ID_COLUMN));

                } else {
                    // Nothing.
                    return false;
                }

            } else {
                // No valid time signature found; cannot possibly save.
                return false;
            }

            // Have read data.
            timeSignatureCursor.close();


            ContentValues staveValues = new ContentValues();
            staveValues.put(SQLQueries.STAVE_TIME_SIGNATURE_ID_COLUMN, timeSignatureId);

            // I looked up SimpleDateFormat at https://stackoverflow.com/questions/2942857/how-to-convert-current-date-into-string-in-java
            Date today = new Date();
            SimpleDateFormat dateFormatter = new SimpleDateFormat(TITLE_DATE_STRING_FORMAT, Locale.UK);
            String currentDateString = dateFormatter.format(today);


            staveValues.put(SQLQueries.STAVE_NAME_COLUMN, currentDateString);

            long staveId = database.insertOrThrow(SQLQueries.STAVE_TABLE_NAME, null, staveValues);

            if (staveId > -1) {
                // Save bars...
                int upperBarsSize = stave.getUpperClef().getBars().size();
                int lowerBarsSize = stave.getLowerClef().getBars().size();
                int totalBars = lowerBarsSize + upperBarsSize;

                for (int i = 0; i < totalBars; i++) {
                    Bar currentBar;
                    if (i > upperBarsSize - 1) {
                        // lower bar, subtract upperBarSize to get offset.
                        int index = i - upperBarsSize;
                        currentBar = stave.getLowerClef().getBars().get(index);
                    } else {
                        // upper bar
                        currentBar = stave.getUpperClef().getBars().get(i);
                    }

                    ContentValues barValues = new ContentValues();
                    barValues.put(SQLQueries.BAR_NUMBER_COLUMN, i);
                    barValues.put(SQLQueries.BAR_STAVE_ID_COLUMN, staveId);

                    long barId = database.insertOrThrow(SQLQueries.BAR_TABLE_NAME, null, barValues);

                    if (barId < 0) {
                        // fail
                        return false;
                    } else {
                        // Save notes for that bar...
                        for (Beat beat : currentBar.getBeats()) {
                            ContentValues beatValues = new ContentValues();
                            int beatIndex = currentBar.getBeats().indexOf(beat);
                            int dynamicIndex = DEFAULT_DYNAMIC_ID;
                            beatValues.put(SQLQueries.BEAT_NUMBER_COLUMN, beatIndex);
                            beatValues.put(SQLQueries.BEAT_DYNAMIC_COLUMN, dynamicIndex);

                            beatValues.put(SQLQueries.BEAT_BAR_ID_COLUMN, barId);

                            long beatId = database.insertOrThrow(SQLQueries.BEAT_TABLE_NAME, null, beatValues);

                            if (beatId < 0) {
                                // Something went wrong
                                return false;
                            } else {
                                // Save notes for beat...
                                List<Note> notes = beat.getNotes();

                                for (Note note : notes) {
                                    // Get note name id...
                                    long noteId = -1;
                                    String noteNameQuery = String.format(Locale.UK, SQLQueries.SELECT_NOTE_NAME_WITH_NAME, note.getPitch());

                                    Cursor noteNameCursor = database.rawQuery(noteNameQuery, null);

                                    if (noteNameCursor != null) {
                                        if (noteNameCursor.moveToFirst()) {
                                            noteId = noteNameCursor.getLong(noteNameCursor.getColumnIndex(SQLQueries.NOTE_NAMES_NAME_ID_COLUMN));

                                        } else {
                                            // Nothing.
                                            return false;
                                        }

                                    } else {
                                        // No valid note name found; cannot possibly save.
                                        return false;
                                    }

                                    // Have read data.
                                    noteNameCursor.close();

                                    ContentValues noteData = new ContentValues();
                                    noteData.put(SQLQueries.NOTE_NAME_ID_COLUMN, noteId);
                                    noteData.put(SQLQueries.NOTE_INTONATION_ID_COLUMN, beat.getIntonation().ordinal());
                                    noteData.put(SQLQueries.NOTE_BEAT_ID_COLUMN, beatId);
                                    noteData.put(SQLQueries.NOTE_LENGTH_COLUMN, note.getLength());

                                    long savedNoteId = database.insertOrThrow(SQLQueries.NOTE_TABLE_NAME, null, noteData);

                                    if (savedNoteId < 0) {
                                        // Failed
                                        return false;
                                    }

                                }

                            }

                        }
                    }

                }

            } else {
                // Not saved.
                return false;
            }

        } catch (SQLiteException sQE) {
            // Not saved correctly; return false
            return false;
        }

        // Finished write.
        database.close();

        // No exception during writing, assume saved correctly...
        return true;

    }

    /**
     * Reads the currently saved Staves from the SQLite database and returns a List containing
     * the staves that are currently in the database.
     *
     * @return a List containing the staves currently saved in the app's database.
     */
    public List<Stave> getSavedStaves() {

        List<Stave> staves = new ArrayList<Stave>();

        SQLiteDatabase databaseInstance = databaseManager.getReadableDatabase();

        Cursor stavesQueryCursor = databaseInstance.rawQuery(SQLQueries.SELECT_ALL_STAVES, null);

        // Are there staves?
        if (stavesQueryCursor != null) {
            if (stavesQueryCursor.moveToFirst()) {
                // I looked up the use of 'moveToNext' and 'getColumnIndex' at http://examples.javacodegeeks.com/android/core/database/android-cursor-example/
                do {
                    String name = stavesQueryCursor.getString(stavesQueryCursor.getColumnIndex(SQLQueries.STAVE_NAME_COLUMN));

                    long staveId = stavesQueryCursor.getLong(stavesQueryCursor.getColumnIndex(SQLQueries.STAVE_ID_COLUMN));

                    Stave stave = new Stave();
                    stave.setName(name);
                    stave.setId((int) staveId);

                    // Get time signature.
                    long timeSignatureId = stavesQueryCursor.getLong(stavesQueryCursor.getColumnIndex(SQLQueries.STAVE_TIME_SIGNATURE_ID_COLUMN));

                    int timeSignatureNumerator = 4;
                    int timeSignatureDenominator = 4;
                    String timeSignatureQuery = String.format(Locale.UK, SQLQueries.SELECT_TIME_SIGNATURE_WITH_ID, timeSignatureId);

                    Cursor timeSignatureCursor = databaseInstance.rawQuery(timeSignatureQuery, null);

                    if (timeSignatureCursor != null) {
                        if (timeSignatureCursor.moveToFirst()) {
                            timeSignatureNumerator = timeSignatureCursor.getInt(timeSignatureCursor.getColumnIndex(SQLQueries.TIME_SIGNATURE_ENTIRETY_FIRST_COLUMN));
                            timeSignatureDenominator = timeSignatureCursor.getInt(timeSignatureCursor.getColumnIndex(SQLQueries.TIME_SIGNATURE_ENTIRETY_SECOND_COLUMN));

                        }
                    }

                    // Have read data.
                    if (timeSignatureCursor != null) {
                        timeSignatureCursor.close();
                    }

                    stave.setTimeSignatureNumerator(timeSignatureNumerator);
                    stave.setTimeSignatureDenominator(timeSignatureDenominator);

                    // Store stave lengths based off the current time signature in a cheeky placeholder
                    int numberOfBeatsOnOneClef = stave.getUpperClef().getBeats().size();

                    Bar[] upperClefContent = new Bar[numberOfBeatsOnOneClef];
                    Bar[] lowerClefContent = new Bar[numberOfBeatsOnOneClef];

                    String barsQuery = String.format(Locale.UK, SQLQueries.SELECT_BARS_WITH_STAVE_ID, staveId);

                    Cursor barQueryCursor = databaseInstance.rawQuery(barsQuery, null);

                    if (barQueryCursor != null) {
                        if (barQueryCursor.moveToFirst()) {
                            do {
                                // Get ID of bar...
                                long barId = barQueryCursor.getLong(barQueryCursor.getColumnIndex(SQLQueries.BAR_ID_COLUMN));

                                // And the number of the bar
                                int barNumber = barQueryCursor.getInt(barQueryCursor.getColumnIndex(SQLQueries.BAR_NUMBER_COLUMN));

                                // Create a bar...
                                Bar currentBar = new Bar();

                                // Is it on the upper bar?
                                if (barNumber < numberOfBeatsOnOneClef - 1) {
                                    // Upper bar...
                                    upperClefContent[barNumber] = currentBar;
                                } else {
                                    // Lower bar...
                                    // Subtract length of clef to get index...
                                    int index = barNumber - numberOfBeatsOnOneClef;
                                    lowerClefContent[index] = currentBar;

                                }

                                Beat[] beatsOnCurrentBar = new Beat[MAX_NOTES_PER_BEAT];

                                String beatQuery = String.format(Locale.UK, SQLQueries.SELECT_BEATS_WITH_BAR_ID, barId);

                                Cursor beatCursor = databaseInstance.rawQuery(beatQuery, null);

                                if (beatCursor != null) {
                                    if (beatCursor.moveToFirst()) {
                                        do {
                                            // Create beat object, then get notes...
                                            long beatId = barQueryCursor.getLong(barQueryCursor.getColumnIndex(SQLQueries.BEAT_ID_COLUMN));
                                            int beatNumber = barQueryCursor.getInt(barQueryCursor.getColumnIndex(SQLQueries.BEAT_NUMBER_COLUMN));

                                            // Create new beat...
                                            Beat currentBeat = new Beat();
                                            beatsOnCurrentBar[beatNumber] = currentBeat;

                                            List<Note> notesOnCurrentBeat = new ArrayList<Note>();

                                            // Get notes for beat...
                                            String notesQuery = String.format(Locale.UK, SQLQueries.SELECT_NOTES_WITH_BEAT_ID, beatId);

                                            Cursor notesCursor = databaseInstance.rawQuery(notesQuery, null);

                                            if (notesCursor != null) {
                                                if (notesCursor.moveToFirst()) {
                                                    do {

                                                        String noteName = "";

                                                        // Get note name...
                                                        long noteNameId = notesCursor.getLong(notesCursor.getColumnIndex(SQLQueries.NOTE_NAME_ID_COLUMN));

                                                        // Get intonation...
                                                        int intonationId = notesCursor.getInt(notesCursor.getColumnIndex(SQLQueries.NOTE_INTONATION_ID_COLUMN));
                                                        currentBeat.setIntonation(Intonation.getIntonationWithID(intonationId));

                                                        String noteNameQuery = String.format(Locale.UK, SQLQueries.SELECT_NOTE_NAME_WITH_ID, noteNameId);

                                                        Cursor noteNameCursor = databaseInstance.rawQuery(noteNameQuery, null);

                                                        if (noteNameCursor != null) {
                                                            if (noteNameCursor.moveToFirst()) {
                                                                noteName = noteNameCursor.getColumnName(noteNameCursor.getColumnIndex(SQLQueries.NOTE_NAMES_NAME_COLUMN));
                                                            }
                                                        }

                                                        // Have read data.
                                                        if (noteNameCursor != null) {
                                                            noteNameCursor.close();
                                                        }


                                                        // Create note object...
                                                        Note currentNote = new ConcreteNote(noteName);

                                                        double length = notesCursor.getDouble(notesCursor.getColumnIndex(SQLQueries.NOTE_LENGTH_COLUMN));
                                                        currentNote.setLength(length);

                                                        notesOnCurrentBeat.add(currentNote);

                                                    } while (notesCursor.moveToNext());

                                                }
                                            }

                                            // Have read data.
                                            if (notesCursor != null) {
                                                notesCursor.close();
                                            }


                                            currentBeat.setNotes(notesOnCurrentBeat);


                                        } while (beatCursor.moveToNext());
                                    }
                                }

                                // Have read data.
                                if (beatCursor != null) {
                                    beatCursor.close();
                                }

                                // Add beats to bar...
                                for (Beat beat : beatsOnCurrentBar) {
                                    if (beat != null) {
                                        // Beat exists; add to bar...
                                        currentBar.addBeat(beat);


                                    }
                                }


                            } while (barQueryCursor.moveToNext());
                        }
                    }

                    // Have read data.
                    if (barQueryCursor != null) {
                        barQueryCursor.close();
                    }

                    // Add bars to stave...
                    List<Bar> upperClefBarsList = new ArrayList<Bar>();
                    for (Bar bar : upperClefContent) {
                        if (bar != null) {
                            upperClefBarsList.add(bar);
                        }
                    }

                    stave.getUpperClef().setBars(upperClefBarsList);

                    List<Bar> lowerClefBarsList = new ArrayList<Bar>();
                    for (Bar bar : lowerClefContent) {
                        if (bar != null) {
                            lowerClefBarsList.add(bar);
                        }
                    }

                    stave.getUpperClef().setBars(lowerClefBarsList);

                    staves.add(stave);


                } while (stavesQueryCursor.moveToNext());

            }

            stavesQueryCursor.close();
        }

        databaseInstance.close();


        return staves;

    }

    /**
     * Deletes the specified Stave from the app's local SQLite database and returns a boolean
     * indicating whether or not deletion has been successful.
     *
     * @param stave the Stave instance that should be deleted from the database.
     * @return a boolean indicating whether or not the delete succeeded.
     */
    public boolean deleteStaveFromDatabase(Stave stave) {
        if (stave.getId() < 0) {
            // Invalid stave, not opened from database.
            return false;
        }

        // Get writable Database...
        SQLiteDatabase database = databaseManager.getWritableDatabase();

        String deletionWhereStatement = SQLQueries.STAVE_DELETE_WHERE_CLAUSE + stave.getId();

        int numberOfRowsDeleted = database.delete(SQLQueries.STAVE_TABLE_NAME, deletionWhereStatement, null);

        database.close();

        // Return deletion status...
        return numberOfRowsDeleted > 0;

    }


}
