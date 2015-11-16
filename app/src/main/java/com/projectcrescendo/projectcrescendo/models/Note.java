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

    // The duration that the note must last for when played, in seconds
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

    // Overriding toString to make Note objects make sense when printed to the console.
    @Override
    public String toString() {
        return "Note{" +
                "name='" + name + '\'' +
                ", frequency=" + frequency +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Note)) {
            return false;
        }

        // Cast the object to a Note instance
        Note note = (Note) object;

        if (this.frequency == note.frequency && this.duration == note.duration) {
            // Compare names too...
            if (this.name != null && note.name != null) {
                if (this.name.equals(note.name)) {
                    return true;
                }
            } else {
                // names are both null but everything else is equal; so the notes are...
                return true;
            }
        }

        return false;

    }

    @Override
    public int hashCode() {
        int result = 0;

        if (name != null) {
            result = 31 * name.hashCode();
        }

        result = (int)(31 * frequency) + result;
        result = (int)(31 * duration) + result;

        return result;
    }
}
