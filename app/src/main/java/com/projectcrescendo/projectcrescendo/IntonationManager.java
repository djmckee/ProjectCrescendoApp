package com.projectcrescendo.projectcrescendo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 *  A class to instantiate intonation names from the SQL Database.
 * Created by Dylan McKee on 23/02/16.
 */
public class IntonationManager {

    final DatabaseManager databaseManager;

    public IntonationManager(Context context) {
        databaseManager = new DatabaseManager(context);

    }

    public List<String> getIntonationNames() {
        SQLiteDatabase databaseInstance = databaseManager.getReadableDatabase();

        Cursor intonationQueryCursor = databaseInstance.rawQuery("SELECT * FROM intonation", null);

        List<String> intonationNames = new ArrayList<String>();

        if (intonationQueryCursor != null) {
            if (intonationQueryCursor.moveToFirst()) {
                // I looked up the use of 'moveToNext' and 'getColumnIndex' at http://examples.javacodegeeks.com/android/core/database/android-cursor-example/
                do {
                    String noteName = intonationQueryCursor.getString(intonationQueryCursor.getColumnIndex("name"));

                    intonationNames.add(noteName);

                } while (intonationQueryCursor.moveToNext());

            }
        }

        databaseInstance.close();

        databaseManager.close();

        return intonationNames;
    }

}
