package com.projectcrescendo.projectcrescendo;

import android.content.Context;

import com.projectcrescendo.projectcrescendo.models.Instruction;
import com.projectcrescendo.projectcrescendo.models.Tutorial;

import java.util.ArrayList;
import java.util.List;

import org.json.*;

/**
 * The TutorialManager is a model manager that instantiates Tutorial instances from a JSON file
 * located in the app's resources bundle, and has an array of Tutorial objects.
 *
 * Created by Dylan McKee on 10/12/15.
 */
public class TutorialManager {


    /**
     * The list of tutorials.
     */
    private final List<Tutorial> tutorialsList = new ArrayList<Tutorial>();

    /**
     * A public constructor to instantiate the tutorials from the JSON file containing them.
     */
    public TutorialManager(Context context) {


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
