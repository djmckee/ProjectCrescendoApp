package com.projectcrescendo.projectcrescendo.models;

/**
 * The instruction class is a model class intended to hold a text string containing the instruction;
 * and any associated instruction metadata.
 * Created by Dylan McKee on 16/11/15.
 */
public class Instruction {
    // The instruction text is only set once upon construction, so can be made final.
    final private String text;

    public Instruction(String instructionText) {
        text = instructionText;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "text='" + text + '\'' +
                '}';
    }

}
