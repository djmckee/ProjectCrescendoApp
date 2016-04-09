package com.projectcrescendo.projectcrescendo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.projectcrescendo.projectcrescendo.models.Beat;
import com.projectcrescendo.projectcrescendo.models.ConcreteNote;
import com.projectcrescendo.projectcrescendo.models.Intonation;
import com.projectcrescendo.projectcrescendo.models.Note;
import com.projectcrescendo.projectcrescendo.models.Tutorial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The TutorialManager is a model manager that instantiates Tutorial instances from an SQLite database
 * located in the app's assets bundle, and has an array of Tutorial objects.
 * <p>
 * Created by Dylan McKee on 10/12/15.
 */
public class TutorialManager {

    /**
     * The list of tutorials.
     */
    private final List<Tutorial> tutorialsList = new ArrayList<Tutorial>();


    /**
     * A public constructor to instantiate the tutorials from the database.
     */
    public TutorialManager(Context context) {
        final DatabaseManager databaseManager = new DatabaseManager(context);
        SQLiteDatabase databaseInstance = databaseManager.getReadableDatabase();

        Cursor tutorialsQueryCursor = databaseInstance.rawQuery("SELECT * FROM tutorials", null);

        // Are there tutorials?
        if (tutorialsQueryCursor != null) {
            if (tutorialsQueryCursor.moveToFirst()) {
                // I looked up the use of 'moveToNext' and 'getColumnIndex' at http://examples.javacodegeeks.com/android/core/database/android-cursor-example/
                do {
                    String title = tutorialsQueryCursor.getString(tutorialsQueryCursor.getColumnIndex("title"));

                    String instructionsJsonText = tutorialsQueryCursor.getString(tutorialsQueryCursor.getColumnIndex("instructions"));
                    List<String> instructionsList = new ArrayList<String>();

                    List<Integer> tutorialPoints = new ArrayList<Integer>();

                    try {
                        JSONArray jsonStringArray = new JSONArray(instructionsJsonText);

                        for (int i = 0; i < jsonStringArray.length(); i++) {
                            JSONObject tutorialObject = jsonStringArray.getJSONObject(i);

                            String tutorialString = tutorialObject.getString("instruction_text");

                            instructionsList.add(tutorialString);

                            int tutorialBreakpoint = tutorialObject.getInt("pattern_match_index");

                            tutorialPoints.add(tutorialBreakpoint);

                        }


                    } catch (JSONException ignored) {

                    }

                    Tutorial savedTutorial = new Tutorial(title, instructionsList);


                    for (int point : tutorialPoints) {
                        savedTutorial.getTutorialPatternMatchIndex().add(point);

                    }

                    String validNoteGridJsonText = tutorialsQueryCursor.getString(tutorialsQueryCursor.getColumnIndex("valid_grid_representation"));

                    try {
                        JSONArray jsonStringArray = new JSONArray(validNoteGridJsonText);
                        for (int i = 0; i < jsonStringArray.length(); i++) {
                            Beat requiredBeat = new Beat();
                            Beat prePopulatedBeat = new Beat();

                            JSONObject gridItem = jsonStringArray.getJSONObject(i);

                            JSONArray gridItemNotes = gridItem.getJSONArray("notes");
                            for (int j = 0; j < gridItemNotes.length(); j++) {
                                JSONObject noteItem = gridItemNotes.getJSONObject(j);

                                String notePitch = noteItem.getString("name");
                                double noteLength = noteItem.getDouble("length");
                                boolean isPrePopulated = noteItem.optBoolean("is_prepopulated");

                                Note note = new ConcreteNote(notePitch);

                                note.setLength(noteLength);

                                requiredBeat.getNotes().add(note);

                                if (isPrePopulated) {
                                    prePopulatedBeat.getNotes().add(note);
                                }

                            }

                            String intonationString = gridItem.getString("expression");
                            Intonation beatIntonation = Intonation.getIntonationWithName(intonationString);
                            requiredBeat.setIntonation(beatIntonation);
                            prePopulatedBeat.setIntonation(beatIntonation);

                            savedTutorial.getValidBeats().add(requiredBeat);
                            savedTutorial.getPrePopulatedBeats().add(prePopulatedBeat);

                        }

                    } catch (JSONException ignored) {

                    }


                    tutorialsList.add(savedTutorial);

                } while (tutorialsQueryCursor.moveToNext());

            }

            tutorialsQueryCursor.close();
        }

        databaseInstance.close();

        databaseManager.close();

    }

    /**
     * A getter method to return the list of tutorials
     *
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
