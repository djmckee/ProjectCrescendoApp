package com.projectcrescendo.projectcrescendo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The Tutorial class is a model class intended to hold a title string, and a List of
 * Instructions (in the order that they need to be displayed in)
 * Created by Dylan McKee on 16/11/15.
 */
public class Tutorial {
    private String title;
    private List<Instruction> instructions = new ArrayList<Instruction>();

    public Tutorial(String title) {
        this.title = title;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Tutorial '" + title + "'";
    }
}
