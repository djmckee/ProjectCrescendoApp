package com.projectcrescendo.projectcrescendo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to instantiate intonation names from the SQL Database.
 * <p/>
 * Created by Dylan McKee on 23/02/16.
 */
class IntonationManager {

    /**
     * The SQLite database manager.
     */
    private final DatabaseManager databaseManager;

    /**
     * Constructs a new Intonation Manager.
     *
     * @param context the context that is creating the intonation manager
     */
    public IntonationManager(Context context) {
        databaseManager = new DatabaseManager(context);

    }

    /**
     * Returns a list of Strings containing the Intonation names from the SQLite database.
     *
     * @return returns a list of Strings containing the Intonation names
     */
    public List<String> getIntonationNames() {
        SQLiteDatabase databaseInstance = databaseManager.getReadableDatabase();

        Cursor intonationQueryCursor = databaseInstance.rawQuery(SQLQueries.SELECT_ALL_INTONATIONS, null);

        List<String> intonationNames = new ArrayList<String>();

        if (intonationQueryCursor != null) {
            if (intonationQueryCursor.moveToFirst()) {
                // I looked up the use of 'moveToNext' and 'getColumnIndex' at http://examples.javacodegeeks.com/android/core/database/android-cursor-example/
                do {
                    String noteName = intonationQueryCursor.getString(intonationQueryCursor.getColumnIndex(SQLQueries.INTONATION_ENTIRETY_NAME_COLUMN));

                    intonationNames.add(noteName);

                } while (intonationQueryCursor.moveToNext());

            }

            intonationQueryCursor.close();

        }

        databaseInstance.close();

        databaseManager.close();

        return intonationNames;
    }

}
