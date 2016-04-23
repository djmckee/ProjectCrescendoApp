package com.projectcrescendo.projectcrescendo;

import com.projectcrescendo.projectcrescendo.models.Clef;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A JUnit test class to test the Clef model class.
 * <p/>
 * Created by Craig Hirst on 02/03/2016
 */
public class ClefTest {
    private static Clef testClef;

    /**
     * Constructs test environment framework before testing
     */
    @BeforeClass
    public static void constructTestEnv() {
        System.out.println("Constructing test environment...");

        testClef = new Clef(8);
    }

    /**
     * Removes test environment framework after testing
     */
    @AfterClass
    public static void dismantleTestEnv() {
        testClef = null;

        System.out.println("Testing complete.");
    }

    /**
     * Reassembles test environment before test
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Setup is not required for this test
    }

    /**
     * Disassembles test environment after test
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        // Tear down is not required for this test
    }

    /**
     * Tests changeBarLength() method. Passes if Bar Length is changed successfully.
     *
     * @throws Exception
     */
    @Test
    public void testChangeBarLength() throws Exception {
        // Passes if the number of beats on the only bar in the List is 8
        Assert.assertEquals(8, testClef.getBars().get(0).getBeats().size());
    }

    /**
     * Tests getBars() method. Passes if correct Bars are returned.
     *
     * @throws Exception
     */
    @Test
    public void testGetBars() throws Exception {
        // Passes if the number of bars is equal to 1
        Assert.assertEquals(1, testClef.getBars().size());
    }

    /**
     * Tests getBeats() method. Passes if correct Beats are returned.
     *
     * @throws Exception
     */
    @Test
    public void testGetBeats() throws Exception {
        // Passes if the number of beats on the only bar in the List is 8
        Assert.assertEquals(8, testClef.getBars().get(0).getBeats().size());
    }
}