package com.projectcrescendo.projectcrescendo;

import com.projectcrescendo.projectcrescendo.models.Clef;
import com.projectcrescendo.projectcrescendo.models.Stave;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A JUnit test class to test the Stave model class.
 *
 * Created by Craig Hirst on 02/03/2016
 */
public class StaveTest {
    private static Stave testStave;

    /**
     * Constructs test environment framework before testing
     */
    @BeforeClass
    public static void constructTestEnv() {
        System.out.println("Constructing test environment...");

        testStave = new Stave();
    }

    /**
     * Removes test environment framework after testing
     */
    @AfterClass
    public static void dismantleTestEnv() {
        testStave = null;

        System.out.println("Testing complete.");
    }

    /**
     * Reassembles test environment before test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Construct a test-stave
        testStave.setTimeSignatureDenominator(4);
        testStave.setTimeSignatureNumerator(4);
    }

    /**
     * Disassembles test environment after test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        // Reset test-stave back to default
        testStave = new Stave();
    }

    /**
     * Tests getUpperClef() method. Passes if correct Clef object is returned.
     * @throws Exception
     */
    @Test
    public void testGetUpperClef() throws Exception {
        // Passes if returned object is of class Clef
        Assert.assertEquals(Clef.class, testStave.getUpperClef().getClass());
    }

    /**
     * Tests getLowerClef() method. Passes if correct Clef object is returned.
     * @throws Exception
     */
    @Test
    public void testGetLowerClef() throws Exception {
        // Passes if returned object is of class Clef
        Assert.assertEquals(Clef.class, testStave.getLowerClef().getClass());
    }

    /**
     * Tests getTimeSignatureDenominator() method. Passes if correct Time Signature Denominator is returned.
     * @throws Exception
     */
    @Test
    public void testGetTimeSignatureDenominator() throws Exception {
        // Passes if returned value is equal to the value we set up
        Assert.assertEquals(4, testStave.getTimeSignatureDenominator());
    }

    /**
     * Tests setTimeSignatureDenominator() method. Passes if new Time Signature Denominator is set successfully.
     * @throws Exception
     */
    @Test
    public void testSetTimeSignatureDenominator() throws Exception {
        // Passes if returned value is equal to the value we set up
        Assert.assertEquals(4, testStave.getTimeSignatureDenominator());
    }

    /**
     * Tests getTimeSignatureNumerator() method. Passes if correct Time Signature Numerator is returned.
     * @throws Exception
     */
    @Test
    public void testGetTimeSignatureNumerator() throws Exception {
        // Passes if returned value is equal to the value we set up
        Assert.assertEquals(4, testStave.getTimeSignatureNumerator());
    }

    /**
     * Tests setTimeSignatureNumerator() method. Passes if new Time Signature Numerator is set successfully.
     * @throws Exception
     */
    @Test
    public void testSetTimeSignatureNumerator() throws Exception {
        // Passes if returned value is equal to the value we set up
        Assert.assertEquals(4, testStave.getTimeSignatureNumerator());
    }
}