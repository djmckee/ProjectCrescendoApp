package com.projectcrescendo.projectcrescendo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The Tutorial class is a model class intended to hold a title string, and a List of
 * Instructions (in the order that they need to be displayed in)
 * Created by Dylan McKee on 16/11/15.
 */
public class Tutorial {
    // The Tutorial title is only set once upon construction of the class, so can be made final.
    final private String title;

    // The tutorial instruction text
    private String instruction;

    public Tutorial(String title, String instruction) {
        this.title = title;
        this.instruction = instruction;

    }

    public String getTitle() {
        return title;
    }

    public String getInstruction() {
        return instruction;
    }

    @Override
    public String toString() {
        return "Tutorial '" + title + "'";
    }

}
