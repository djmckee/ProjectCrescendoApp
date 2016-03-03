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
 *
 * Created by Craig Hirst on 02/03/2016
 */
public class ClefTest {
    private static Clef testClef;

    @BeforeClass
    public static void constructTestEnv() {
        System.out.println("Constructing test environment...");

        testClef = new Clef(8);
    }

    @AfterClass
    public static void dismantleTestEnv() {
        testClef = null;

        System.out.println("Testing complete.");
    }

    @Before
    public void setUp() throws Exception {
        // Setup is not required for this test
    }

    @After
    public void tearDown() throws Exception {
        // Tear down is not required for this test
    }

    @Test
    public void testChangeBarLength() throws Exception {
        // Passes if the number of beats on the only bar in the List is 8
        Assert.assertEquals(8, testClef.getBars().get(0).getBeats().size());
    }

    @Test
    public void testGetBars() throws Exception {
        // Passes if the number of bars is equal to 1
        Assert.assertEquals(1, testClef.getBars().size());
    }

    @Test
    public void testGetBeats() throws Exception {
        // Passes if the number of beats on the only bar in the List is 8
        Assert.assertEquals(8, testClef.getBars().get(0).getBeats().size());
    }
}