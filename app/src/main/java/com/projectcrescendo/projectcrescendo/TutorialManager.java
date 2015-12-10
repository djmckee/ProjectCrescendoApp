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
 * The tutorials only need to be instantiated once per use of the app, so this class is a singleton
 * to reduce memory footprint.
 *
 * Created by Dylan McKee on 10/12/15.
 */
public class TutorialManager {

    /**
     * A constant containing the reference to the json file containing the tutorials.
     */
    private static final int TUTORIALS_FILE = R.raw.tutorials;

    /**
     * The list of tutorials.
     */
    private final List<Tutorial> tutorialsList = new ArrayList<Tutorial>();

    /**
     * A public constructor to instantiate the tutorials from the JSON file containing them.
     */
    public TutorialManager(Context context) {
        // Instantiate the Tutorials from tutorials.json
        // TODO: Parse tutorials.json into this array!!!!
        JSONArray array = new JSONArray();

        // Loop through the array, creating a Tutorial instance for each entry...
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = array.getJSONObject(i);

                // Create tutorial...
                String tutorialTitle = object.getString("name");
                Tutorial tutorial = new Tutorial(tutorialTitle);

                // Get instructions too...
                JSONArray instructions = object.getJSONArray("instructions");

                for (int j = 0; j < instructions.length(); j++) {
                    JSONObject instructionObject = instructions.getJSONObject(j);
                    String instructionText = instructionObject.getString("text");
                    Instruction instruction = new Instruction(instructionText);

                    tutorial.addInstruction(instruction);

                }

            } catch (JSONException e) {
                // Object was malformed!
            }
        }



    }


}
