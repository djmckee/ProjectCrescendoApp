package com.projectcrescendo.projectcrescendo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.projectcrescendo.projectcrescendo.models.Tutorial;

import java.util.ArrayList;
import java.util.List;

/**
 * The TutorialManager is a model manager that instantiates Tutorial instances from an SQLite database
 * located in the app's assets bundle, and has an array of Tutorial objects.
 *
 * Created by Dylan McKee on 10/12/15.
 */
public class TutorialManager {

    /**
     * The list of tutorials.
     */
    private final List<Tutorial> tutorialsList = new ArrayList<Tutorial>();


    /**
     * A database manager instance so that this class can access stored data bundled with the app.
     */
    private final DatabaseManager databaseManager;

    /**
     * A public constructor to instantiate the tutorials from the database.
     */
    public TutorialManager(Context context) {
        databaseManager = new DatabaseManager(context);
        SQLiteDatabase databaseInstance = databaseManager.getReadableDatabase();

        Cursor tutorialsQueryCursor = databaseInstance.rawQuery("SELECT * FROM tutorials", null);

        // Are there tutorials?
        if (tutorialsQueryCursor != null) {
            if (tutorialsQueryCursor.moveToFirst()) {
                // I looked up the use of 'getColumnIndex' at http://examples.javacodegeeks.com/android/core/database/android-cursor-example/
                String title = tutorialsQueryCursor.getString(tutorialsQueryCursor.getColumnIndex("title"));
                String instructionalText = tutorialsQueryCursor.getString(tutorialsQueryCursor.getColumnIndex("text"));

                Tutorial savedTutorial = new Tutorial(title, instructionalText);

                tutorialsList.add(savedTutorial);

            }
        }

        databaseInstance.close();



    }

    /**
     * A getter method to return the list of tutorials
     * @return the list of tutorials.
     */
    public List<Tutorial> getTutorialsList() {
        return tutorialsList;
    }

    /**
     * Returns the specified tutorial.
     *
     * @return the tutorial.
     */
    public Tutorial getTutorial(int tutorialIndex) {
        try {
            return tutorialsList.get(tutorialIndex);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

    }

}
