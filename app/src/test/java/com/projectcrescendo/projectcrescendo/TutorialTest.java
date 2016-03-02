package com.projectcrescendo.projectcrescendo;

import com.projectcrescendo.projectcrescendo.models.Tutorial;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * Created by Craig Hirst on 02/03/2016
 */
public class TutorialTest {
    private static Tutorial testTutorial;

    @BeforeClass
    public static void constructTestEnv() {
        System.out.println("Constructing test environment...");
        testTutorial = new Tutorial("test_title", "test_instruction");
    }

    @AfterClass
    public static void dismantleTestEnv() {
        testTutorial = null;
        System.out.println("Testing complete.");
    }

    // Setup and teardown is not required by this test

    @Test
    public void testGetTitle() throws Exception {
        // Passes if returned string is equal to the string we set as a parameter
        Assert.assertEquals("test_title", testTutorial.getTitle());
    }

    @Test
    public void testGetInstruction() throws Exception {
        // Passes if returned string is equal to the string we set as a parameter
        Assert.assertEquals("test_instruction", testTutorial.getInstruction());
    }

    @Test
    public void testToString() throws Exception {
        // Passes if returned string is equal to "Tutorial 'test_title'"
        Assert.assertEquals("Tutorial 'test_title'", testTutorial.toString());
    }
}