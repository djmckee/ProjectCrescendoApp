package com.projectcrescendo.projectcrescendo;

import android.content.Context;
import android.test.InstrumentationTestCase;

import junit.framework.TestCase;

import java.io.File;

/**
 * A JUnit Unit Tese case to test the FileHandler file reading functionality.
 *
 * Created by Dylan McKee on 10/12/15.
 */
public class FileHandlerTest extends InstrumentationTestCase {
    /**
     * The contents of the text.txt file
     */
    private static final String TEST_FILE_CONTENTS = "test";

    /**
     * A unit test to check the read string from file method works.
     * @throws Exception an exception if something fails.
     */
    public void testStringFromFile() throws Exception {
        Context testContext = getInstrumentation().getContext();

        // Ensure the test file contains the test string
        String fileContents = FileHandler.stringFromFile(testContext, R.raw.test);

        assertEquals(TEST_FILE_CONTENTS, fileContents);

    }
}