package com.projectcrescendo.projectcrescendo;

import com.projectcrescendo.projectcrescendo.models.Tutorial;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * A JUnit test class to test the Tutorial model.
 *
 * Created by Craig Hirst on 02/03/2016
 */
public class TutorialTest {
    private static Tutorial testTutorial;

    /**
     * Constructs test environment framework before testing
     */
    @BeforeClass
    public static void constructTestEnv() {
        System.out.println("Constructing test environment...");
        List<String> array = new ArrayList<String>();
        array.add("test_instruction");
        testTutorial = new Tutorial("test_title", array);
    }

    /**
     * Removes test environment framework after testing
     */
    @AfterClass
    public static void dismantleTestEnv() {
        testTutorial = null;
        System.out.println("Testing complete.");
    }

    // Setup and teardown is not required by this test

    /**
     * Tests getTitle() method. Passes if correct Title is returned.
     * @throws Exception
     */
    @Test
    public void testGetTitle() throws Exception {
        // Passes if returned string is equal to the string we set as a parameter
        Assert.assertEquals("test_title", testTutorial.getTitle());
    }

    /**
     * Tests getInstruction method. Passes if correct Instruction is returned.
     * @throws Exception
     */
    @Test
    public void testGetInstruction() throws Exception {
        // Passes if returned string is equal to the string we set as a parameter
        List<String> array = new ArrayList<String>();
        array.add("test_instruction");
        String instruction = testTutorial.getInstructions().get(0);
        Assert.assertEquals("test_instruction", instruction);
    }

    /**
     * Tests toString() method. Passes if correct String is returned.
     * @throws Exception
     */
    @Test
    public void testToString() throws Exception {
        // Passes if returned string is equal to "Tutorial 'test_title'"
        Assert.assertEquals("Tutorial 'test_title'", testTutorial.toString());
    }
}