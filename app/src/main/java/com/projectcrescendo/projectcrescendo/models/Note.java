package com.projectcrescendo.projectcrescendo.models;

/**
 * The note class is a class to model a musical note. It contains all the data about the note -
 * and the data held by this class can be changed by the user in the app.
 * Created by Dylan McKee on 16/11/15.
 */
public class Note {
    // The name of the note
    private String name;

    // TODO: Sort out note icons...

    // The frequency of the note, in Hz
    private double frequency;

    // The duration that the note must last for, in seconds
    private double duration;

    // Getter and setters to expose these properties publicly and make them readwrite
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

}
