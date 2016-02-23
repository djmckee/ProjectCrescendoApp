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

    final DatabaseManager databaseManager;

    public NoteManager(Context context) {
        databaseManager = new DatabaseManager(context);

    }

    public List<String> getNoteNames() {
        SQLiteDatabase databaseInstance = databaseManager.getReadableDatabase();

        Cursor noteNameQueryCursor = databaseInstance.rawQuery("SELECT * FROM note_names", null);

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
        }

        databaseInstance.close();

        databaseManager.close();

        return noteNames;
    }


}
