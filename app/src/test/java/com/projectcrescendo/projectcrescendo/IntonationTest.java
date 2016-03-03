package com.projectcrescendo.projectcrescendo;

import com.projectcrescendo.projectcrescendo.models.Intonation;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A JUnit test class to test the Intonation enum.
 *
 * Created by Craig Hirst on 02/03/2016
 */
public class IntonationTest {

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
    public void testGetIntonationWithID() throws Exception {
        // Passes if returned Intonation is equal to the Intonation with ID 5 in crescendo.db (Accelerando)
        Assert.assertEquals(Intonation.Accelerando, Intonation.getIntonationWithID(5));
    }
}