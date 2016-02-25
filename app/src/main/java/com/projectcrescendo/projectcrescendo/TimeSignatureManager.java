package com.projectcrescendo.projectcrescendo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.projectcrescendo.projectcrescendo.models.Tutorial;

import java.util.ArrayList;
import java.util.List;

/**
 * The TimeSignatureManager is a model manager that instantiates lists of Time Signature choices from the
 * database.
 *
 * Created by Dylan McKee on 10/12/15.
 */
public class TimeSignatureManager {

    /**
     * The list of time signature numerators.
     */
    private final List<Integer> timeSignatureNumerators = new ArrayList<Integer>();

    /**
     * The list of time signature denominator.
     */
    private final List<Integer> timeSignatureDenominator = new ArrayList<Integer>();



    /**
     * A public constructor to instantiate the time signatures from the database.
     */
    public TimeSignatureManager(Context context) {
        final DatabaseManager databaseManager = new DatabaseManager(context);
        SQLiteDatabase databaseInstance = databaseManager.getReadableDatabase();

        Cursor timeSigCursor = databaseInstance.rawQuery("SELECT * FROM time_signature", null);

        // Are there time signatures?
        if (timeSigCursor != null) {
            if (timeSigCursor.moveToFirst()) {
                // I looked up the use of 'moveToNext' and 'getColumnIndex' at http://examples.javacodegeeks.com/android/core/database/android-cursor-example/
                do {

                    int numerator = timeSigCursor.getInt(timeSigCursor.getColumnIndex("first"));
                    int denominator = timeSigCursor.getInt(timeSigCursor.getColumnIndex("second"));

                    timeSignatureNumerators.add(numerator);
                    timeSignatureDenominator.add(denominator);

                } while (timeSigCursor.moveToNext());

            }
        }

        databaseInstance.close();

        databaseManager.close();

    }


    public List<Integer> getTimeSignatureNumerators() {
        return timeSignatureNumerators;
    }

    public List<Integer> getTimeSignatureDenominator() {
        return timeSignatureDenominator;
    }

}