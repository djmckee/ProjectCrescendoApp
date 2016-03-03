package com.projectcrescendo.projectcrescendo;

import com.projectcrescendo.projectcrescendo.models.Dynamic;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A JUnit test class to teset the Dynamic enum.
 *
 * Created by Craig Hirst on 02/03/2016
 */
public class DynamicTest {

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
    public void testGetADynamicWithID() throws Exception {
        // Passes if returned Dynamic is equal to the Dynamic with ID 3 in crescendo.db (MezzoPiano)
        Assert.assertEquals(Dynamic.MezzoPiano, Dynamic.getDynamicWithID(3));
    }
}