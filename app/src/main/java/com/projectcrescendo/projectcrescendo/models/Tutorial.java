package com.projectcrescendo.projectcrescendo.models;

/**
 * The Tutorial class is a model class intended to hold a title string, and a List of
 * TutorialSelectionMenuActivity (in the order that they need to be displayed in).
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
     * The tutorial instruction text, as a String
     */
    private String instruction;

    /**
     * Constructs a new tutorial.
     *
     * @param title       the title of the new tutorial
     * @param instruction the instructional text for the new tutorial.
     */
    public Tutorial(String title, String instruction) {
        this.title = title;
        this.instruction = instruction;

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
     * Returns the instructional text of the current tutorial.
     *
     * @return the instructional text of the current tutorial.
     */
    public String getInstruction() {
        return instruction;
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

}
