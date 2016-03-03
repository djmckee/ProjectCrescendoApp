package com.projectcrescendo.projectcrescendo;

import android.util.Log;

import com.projectcrescendo.projectcrescendo.models.Bar;
import com.projectcrescendo.projectcrescendo.models.Beat;
import com.projectcrescendo.projectcrescendo.models.Clef;
import com.projectcrescendo.projectcrescendo.models.Note;
import com.projectcrescendo.projectcrescendo.models.Stave;

/**
 * A class to convert the Project Crescendo Stave, Bar, Beat and Note model classes into valid
 * MusicXML for playback with the SeeScore engine.
 *
 * Created by Dylan McKee on 25/02/16.
 * Based on Pseudocode by Charlie Marcus.
 *
 */
public class MusicXmlWriter {

    /**
     * A method that encodes the Stave instance passed to it into a valid MusicXML representation,
     * and returns this representation as a String.
     * @param stave the Stave instance containing the composition to encode.
     * @return a String containing the MusicXML representation of the composition.
     */
    public static String encode(Stave stave) {
        String musicXMLString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";

        musicXMLString += "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.0Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\n";
        musicXMLString += "<score-partwise version=\"3.0\">\n";
        musicXMLString += "<part-list>\n";	//List the parts
        musicXMLString += "<score-part id=\"p1\">\n"; // Create the first part
        musicXMLString += "<part-name>Right Hand</part-name>\n</score-part>\n"; //The first part is the right hand.
        musicXMLString += "<score-part id=\"p2\">\n"; // Create the second part
        musicXMLString +=  "<part-name>Left Hand</part-name>\n</score-part>\n"; //The second part is the left hand.
        musicXMLString += "</part-list>\n"; //End of the parts

        int timeSigBeat = stave.getTimeSignatureNumerator(); // get 'first' from time signature of the current stave.
        int timeSigType = stave.getTimeSignatureDenominator(); // get 'second' from time signature of the current stave.

        int noOfDivisions = timeSigBeat * 2;

        Clef[] parts = {stave.getLowerClef(), stave.getUpperClef()};

        for (int i = 0; i < parts.length; i++) {
            Clef currentClef = parts[i];

            musicXMLString += "<part id=\"p" + (i + 1) + "\">\n"; //So for the first part it would be p1 and the second p2

            // At the moment, there's only one bar per part...
            for (int bar = 0; bar < currentClef.getBars().size(); bar++) {
                Bar currentBar = parts[i].getBars().get(bar);

                musicXMLString += "<measure number=\"" + (bar + 1) +"\">\n";	//Write the bar number

                if (bar == 0) {
                    musicXMLString += "<attributes>\n";
                    musicXMLString += "<divisions>" +noOfDivisions + "</divisions>\n";
                    musicXMLString += "<key>\n";
                    musicXMLString += "<fifths>0</fifths>\n";
                    musicXMLString += "</key>\n";
                    musicXMLString += "<time>\n";
                    musicXMLString += "<beats>" + timeSigBeat + "</beats>\n";
                    musicXMLString += "<beat-type>" + timeSigType + " </beat-type>\n";
                    musicXMLString += "</time>\n";

                    musicXMLString += "<clef>\n";

                    if (i == 0) {
                        // right hand part
                        // Different clefs for right and left hand
                        musicXMLString += "<sign>G</sign>\n";
                        musicXMLString += "<line>2</line>\n";
                    } else {
                        // left hand part
                        musicXMLString += "<sign>F</sign>\n";
                        musicXMLString += "<line>4</line>\n";
                    }

                    musicXMLString += "</clef>\n";

                    musicXMLString += "</attributes>\n";
                }


                int notesInBar = currentBar.getAllNotesOnBar().size();  // get the number of notes in the current bar

                // If there are no notes in a bar then we can draw a rest for the entire bar
                if (notesInBar == 0) {

                    musicXMLString += "<note>\n";
                    musicXMLString += "<rest/>\n";
                    musicXMLString += "<duration>" + noOfDivisions + "</duration>\n";
                    musicXMLString += "</note>\n";

                } else {
                    for (Beat beat : currentBar.getBeats()) {
                        int notesInBeat = beat.getNotes().size();
                        Log.d("MusicXML", "notes in beat: " + notesInBeat);

                        String noteNames[] = new String[notesInBeat]; // Get names of notes at the current beat in the current bar. (will have to do some string manipulation to seperate)
                        double noteLengths[] = new double[notesInBeat]; // Get the lengths of the notes in the current beat in the current bar (will have to do some string manipulation to seperate)

                        // Populate arrays
                        for (int n = 0; n < notesInBeat; n++) {
                            Note note = beat.getNotes().get(n);

                            noteNames[n] = note.getPitch();
                            noteLengths[n] = note.getLength();

                            Log.d("MusicXML", "adding note: " + note.getPitch());


                        }

                        int alter = 0;
                        int octave = 0;
                        boolean accidental = false;

                        //Write out each note at the current beat
                        for (int n = 0; n < notesInBeat; n++) {

                            char stepChar = noteNames[n].toCharArray()[0];
                            String step = String.format("%c", stepChar);

                            int noteNameLength = noteNames[n].toCharArray().length;
                            //Will return null if there is no third character so should probably check for noteNames[n].length first
                            if (noteNameLength >= 2) {
                                // Could be a # after it
                                if (noteNames[n].toCharArray()[noteNameLength-1] == '#' || noteNameLength == 3) {
                                    alter = -1;
                                    accidental = true;
                                } else if (noteNames[n].toCharArray()[noteNameLength-1] == 'b' || noteNameLength == 3) {
                                    alter = 1;
                                    accidental = true;
                                } else {
                                    alter = 0;
                                }
                            } else {
                                // no accidental
                                alter = 0;
                            }

                                if (noteNameLength >= 2) {
                                    char octaveChar = noteNames[n].toCharArray()[1];
                                    String octaveString = String.format("%c", octaveChar);

                                    try {
                                        octave = Integer.parseInt(octaveString);
                                    } catch(NumberFormatException nfe) {
                                        Log.d("octave error", "could not parse octave");
                                    }

                                }

                            musicXMLString += "<note>\n";

                            if (n > 0) {
                                musicXMLString += "<chord/>\n";
                            }

                            musicXMLString += "<pitch>\n";
                            musicXMLString += "<step>" + step + "</step>\n";
                            musicXMLString += "<alter>" + alter + "</alter>\n";
                            musicXMLString += "<octave>" + octave +"</octave>\n";
                            musicXMLString += "</pitch>\n";

                            double length = noteLengths[n] * 8;
                            musicXMLString += "<duration>" + ((int) (length + 0.5)) + "</duration>\n";

                            musicXMLString += "</note>\n";



                        }


                    }

                }

                musicXMLString += "</measure>\n";

            }


            musicXMLString += "</part>\n";
        }

        musicXMLString += "</score-partwise>\n";
        return musicXMLString;

    }

}
