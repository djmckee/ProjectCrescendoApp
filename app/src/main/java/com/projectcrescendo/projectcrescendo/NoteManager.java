package com.projectcrescendo.projectcrescendo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to instantiate notes from the SQL Database.
 * Created by Dylan McKee on 18/02/16.
 */
public class NoteManager {

    /**
     * The SQLite database manager.
     */
    private final DatabaseManager databaseManager;

    /**
     * Constructs a new Note Manager.
     *
     * @param context the context that is creating the note manager
     */
    public NoteManager(Context context) {
        databaseManager = new DatabaseManager(context);

    }

    /**
     * Returns a list of Strings containing the note names from the SQLite database.
     *
     * @return returns a list of Strings containing the note names
     */
    public List<String> getNoteNames() {
        SQLiteDatabase databaseInstance = databaseManager.getReadableDatabase();

        Cursor noteNameQueryCursor = databaseInstance.rawQuery(SQLQueries.SELECT_ALL_NOTE_NAMES, null);

        List<String> noteNames = new ArrayList<String>();

        // Are there tutorials?
        if (noteNameQueryCursor != null) {
            if (noteNameQueryCursor.moveToFirst()) {
                // I looked up the use of 'moveToNext' and 'getColumnIndex' at http://examples.javacodegeeks.com/android/core/database/android-cursor-example/
                do {
                    String noteName = noteNameQueryCursor.getString(noteNameQueryCursor.getColumnIndex("name"));

                    noteNames.add(noteName);

                } while (noteNameQueryCursor.moveToNext());

            }

            noteNameQueryCursor.close();
        }

        databaseInstance.close();

        databaseManager.close();

        return noteNames;
    }


}
