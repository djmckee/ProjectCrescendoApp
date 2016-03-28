package com.projectcrescendo.projectcrescendo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The Tutorial class is a model class intended to hold a title string, and a List of
 * TutorialSelectionMenuActivity (in the order that they need to be displayed in).
 *
 * <p>
 * Created by Dylan McKee on 16/11/15.
 */
public class Tutorial {
    /**
     * The title of the current tutorial as a string.
     * The Tutorial title is only set once upon construction of the class, so can be made final.
     */
    final private String title;

    /**
     * The tutorial instruction texts, as a Strings
     */
    final private List<String> instructions;

    /**
     * The list of notes that must be pre-populated into the grid.
     */
    final private List<Beat> prePopulatedBeats = new ArrayList<Beat>();

    /**
     * The list of notes that MUST be present for the tutorial to be passed as valid.
     */
    final private List<Beat> validBeats = new ArrayList<Beat>();

    final private List<Integer> tutorialPatternMatchIndex = new ArrayList<Integer>();

    /**
     * Constructs a new tutorial.
     *
     * @param title       the title of the new tutorial
     * @param instructions a list of the instructional texts for the new tutorial.
     */
    public Tutorial(String title, List<String> instructions) {
        this.title = title;
        this.instructions = instructions;
    }

    /**
     * Returns the title of the current tutorial
     *
     * @return the title of the current tutorial
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the instructional texts of the current tutorial.
     *
     * @return a List of the instructional texts of the current tutorial.
     */
    public List<String> getInstructions() {
        return instructions;
    }

    /**
     * Provides a human readable String representation of the current tutorial, for debugging
     * purposes.
     *
     * @return a human readable String representation of the current tutorial, for debugging
     * purposes.
     */
    @Override
    public String toString() {
        return "Tutorial '" + title + "'";
    }


    /**
     * Returns a list of beats that must be populated on the grid on load of the tutorial.
     * @return a List of Beat objects that must be pre-populated on the grid at the start of the tutorial.
     */
    public List<Beat> getPrePopulatedBeats() {
        return prePopulatedBeats;
    }

    /**
     * Return a list of beats that must be on the grid for the tutorial composition to be valid.
     * @return a List of Beats that must be on the grid for the tutorial composition to be valid.
     */
    public List<Beat> getValidBeats() {
        return validBeats;
    }

    /**
     * Returns the indices that the current tutorial performs pattern matches at.
     * @return a list of integers containing the indices numbers that the current tutorial
     */
    public List<Integer> getTutorialPatternMatchIndex() {
        return tutorialPatternMatchIndex;
    }

    /**
     * Returns a List of Beat objects that match the criteria of being on either the upper or lower
     * Clef (decided by the 'isUpperClef' bool), from the List of Beats passed in to the method
     * (the allBeats parameter)
     * @param allBeats the beats to select the upper/lower beats to return from.
     * @param isUpperClef should the beats be selected from the upper clef, or the lower clef?
     * @return a sublist of beats for either the upper or the lower clef, from the 'allBeats' array
     *          passed into the method.
     */
    private List<Beat> getBeats(List<Beat> allBeats, boolean isUpperClef) {
        List<Beat> beats = new ArrayList<Beat>();

        for (int i = 0; i < allBeats.size(); i++) {
            Beat currentBeat = allBeats.get(i);
            // Upper clef contains only even numbers...
            if (isUpperClef) {
                if ((i % 2) == 0) {
                    beats.add(currentBeat);

                }
            } else {
                // Lower clef contains only odd beats
                if ((i % 2) != 0) {
                    beats.add(currentBeat);

                }
            }
        }

        return beats;
    }

    /**
     * Returns a List of Beats that should be pre-populated onto the stave's upper clef for this
     * tutorial.
     * @return a List of Beats that should be pre-populated onto the stave's upper clef.
     */
    public List<Beat> getPrePopupalatedBeatsForUpperClef() {
        return getBeats(getPrePopulatedBeats(), true);

    }

    /**
     * Returns a List of Beats that should be pre-populated onto the stave's lower clef for this
     * tutorial.
     * @return a List of Beats that should be pre-populated onto the stave's lower clef.
     */
    public List<Beat> getPrePopupalatedBeatsForLowerClef() {
        return getBeats(getPrePopulatedBeats(), false);

    }

    /**
     * Returns a List of Beats that must appear, in order, on the stave's upper clef for this tutorial
     * to have been completed correctly.
     * @return a List of Beats that must appear on the stave's upper clef, in order, for this tutorial
     *          to be marked as correct.
     */
    public List<Beat> getValidBeatsForUpperClef() {
        return getBeats(getValidBeats(), true);

    }

    /**
     * Returns a List of Beats that must appear, in order, on the stave's lower clef for this tutorial
     * to have been completed correctly.
     * @return a List of Beats that must appear on the stave's lower clef, in order, for this tutorial
     *          to be marked as correct.
     */
    public List<Beat> getValidBeatsForLowerClef() {
        return getBeats(getValidBeats(), false);

    }


}
