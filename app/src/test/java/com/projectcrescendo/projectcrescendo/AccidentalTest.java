package com.projectcrescendo.projectcrescendo;

import com.projectcrescendo.projectcrescendo.models.Accidental;

import org.junit.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A JUnit test class to test the Accidental enum.
 *
 * Created by Craig Hirst on 02/03/2016
 */
public class AccidentalTest {

    @BeforeClass
    public static void constructTestEnv() {
        System.out.println("Constructing test environment...");
        // No field variable initialisation required by this test
    }

    @AfterClass
    public static void dismantleTestEnv() {
        // No field variable nullification required by this test
        System.out.println("Testing completed.");
    }

    // No setups and tear downs required by this test class

    @Test
    public void testGetAccidentalWithID() throws Exception {
        // Passes if returned Accidental is equal to the Accidental with ID 2 in crescendo.db (Natural)
        Assert.assertEquals(Accidental.Natural, Accidental.getAccidentalWithID(2));
    }
}