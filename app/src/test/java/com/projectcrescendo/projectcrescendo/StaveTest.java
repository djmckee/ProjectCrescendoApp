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
 *
 * Created by Craig Hirst on 02/03/2016
 */
public class StaveTest {
    private static Stave testStave;

    @BeforeClass
    public static void constructTestEnv() {
        System.out.println("Constructing test environment...");

        testStave = new Stave();
    }

    @AfterClass
    public static void dismantleTestEnv() {
        testStave = null;

        System.out.println("Testing complete.");
    }

    @Before
    public void setUp() throws Exception {
        // Construct a test-stave
        testStave.setTimeSignatureDenominator(4);
        testStave.setTimeSignatureNumerator(4);
    }

    @After
    public void tearDown() throws Exception {
        // Reset test-stave back to default
        testStave = new Stave();
    }

    @Test
    public void testGetUpperClef() throws Exception {
        // Passes if returned object is of class Clef
        Assert.assertEquals(Clef.class, testStave.getUpperClef().getClass());
    }

    @Test
    public void testGetLowerClef() throws Exception {
        // Passes if returned object is of class Clef
        Assert.assertEquals(Clef.class, testStave.getLowerClef().getClass());
    }

    @Test
    public void testGetTimeSignatureDenominator() throws Exception {
        // Passes if returned value is equal to the value we set up
        Assert.assertEquals(4, testStave.getTimeSignatureDenominator());
    }

    @Test
    public void testSetTimeSignatureDenominator() throws Exception {
        // Passes if returned value is equal to the value we set up
        Assert.assertEquals(4, testStave.getTimeSignatureDenominator());
    }

    @Test
    public void testGetTimeSignatureNumerator() throws Exception {
        // Passes if returned value is equal to the value we set up
        Assert.assertEquals(4, testStave.getTimeSignatureNumerator());
    }

    @Test
    public void testSetTimeSignatureNumerator() throws Exception {
        // Passes if returned value is equal to the value we set up
        Assert.assertEquals(4, testStave.getTimeSignatureNumerator());
    }
}