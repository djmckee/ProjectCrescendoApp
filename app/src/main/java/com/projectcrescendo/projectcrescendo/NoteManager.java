package com.projectcrescendo.projectcrescendo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.projectcrescendo.projectcrescendo.models.Tutorial;

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

        Cursor tutorialsQueryCursor = databaseInstance.rawQuery("SELECT * FROM notes", null);

        List<String> noteNames = new ArrayList<String>();

        // Are there tutorials?
        if (tutorialsQueryCursor != null) {
            if (tutorialsQueryCursor.moveToFirst()) {
                // I looked up the use of 'moveToNext' and 'getColumnIndex' at http://examples.javacodegeeks.com/android/core/database/android-cursor-example/
                do {
                    String noteName = tutorialsQueryCursor.getString(tutorialsQueryCursor.getColumnIndex("name"));

                    noteNames.add(noteName);

                } while (tutorialsQueryCursor.moveToNext());

            }
        }

        databaseInstance.close();

        databaseManager.close();

        return noteNames;
    }


}
