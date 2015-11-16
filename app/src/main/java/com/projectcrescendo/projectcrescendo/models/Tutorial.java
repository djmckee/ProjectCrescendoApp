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

    // The List is only instantiated once, and can have Instruction instances added to it using the relevant method.
    final private List<Instruction> instructions = new ArrayList<Instruction>();

    public Tutorial(String title) {
        this.title = title;

    }

    public String getTitle() {
        return title;
    }

    public List<Instruction> getInstructions() {
        // Defensive copying; return a copy of the instructions list...
        return new ArrayList<Instruction>(instructions);
    }

    public void addInstruction(Instruction instruction) {
        // Add the instruction we've been passed into the list
        instructions.add(instruction);
    }

    @Override
    public String toString() {
        return "Tutorial '" + title + "'";
    }
}
