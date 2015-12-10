package com.projectcrescendo.projectcrescendo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A class to read in a file from the raw resources bundle of the app and return it as a String
 * instance, to provide a better level of abstraction and reduce code duplication.
 *
 * Created by Dylan McKee on 10/12/15.
 */
public class FileHandler {

    /**
     * A static method that reads the content of the file handed to it into a String, and returns
     * the string.
     * @param context the current activity's context
     * @param fileReference a reference to the file to read in (via 'R.')
     * @return the contents of the file, as a String - or null if an error occurred whilst reading.
     */
    public static String stringFromFile(Context context, int fileReference) {
        // Open a file input stream containing the data of the file reference we've been passed
        InputStream fileInputStream = context.getResources().openRawResource(fileReference);

        // Create a reader to read the input stream
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedStreamReader = new BufferedReader(inputStreamReader);

        // Create a placeholder string (with blank instance) to append the line being read on to the end of
        String string = "";

        // Create a placeholder to hold the current line being read in as the file gets read line by line
        String input = null;

        try {
            // Repeat forever - the loop gets broken when nothing's left to read...
            while (true) {
                // Read the next line of the file
                input = bufferedStreamReader.readLine();

                // If there's nothing left to read, then leave the loop, do not continue.
                if (input == null) {
                    break;
                }

                // Concat the new line on to the end string
                string = string + input;
            }
        } catch (IOException iE) {
            // If an IO Exception occurs, give up and return null to indicate something went wrong...
            return null;
        }

        // If nothing's been read, just return null...
        if (string.equals("")) {
            return null;
        }

        // success! return the actual string...
        return string;

    }

}
