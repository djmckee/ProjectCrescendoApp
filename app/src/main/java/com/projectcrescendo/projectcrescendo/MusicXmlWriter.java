package com.projectcrescendo.projectcrescendo;

import com.projectcrescendo.projectcrescendo.models.Stave;

/**
 * A class to convert the Project Crescendo Stave, Bar, Beat and Note model classes into valid
 * MusicXML for playback with the SeeScore engine.
 * Created by Dylan McKee on 25/02/16.
 * Based on Pseudocode by Charlie Marcus.
 */
public class MusicXmlWriter {

    public static String encode(Stave stave) {
        String musicXMLString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";
        musicXMLString += "<!DOCTYPE score-partwise PUBLIC\"-//Recordare//DTD MusicXML 3.0Partwise//EN\"\"http://www.musicxml.org/dtds/partwise.dtd\">";
        musicXMLString += "<score-partwise version=\"3.0\">";
        musicXMLString += musicXMLString + "<part-list>";	//List the parts
        musicXMLString += musicXMLString + "<score-part id=\"p1\">"; // Create the first part
        musicXMLString += musicXMLString + "<part-name>Right Hand</part-name>M/score-part>"; //The first part is the right hand.
        musicXMLString += musicXMLString + "<score-part id=\"p2\">"; // Create the second part
        musicXMLString += musicXMLString + "<part-name>Left Hand</part-name>M/score-part>"; //The second part is the left hand.
        musicXMLString += musicXMLString + "</part-list>"; //End of the parts

        // TODO: Implement!!!

        return musicXMLString;
    }

}
