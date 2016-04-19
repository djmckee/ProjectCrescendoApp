package com.projectcrescendo.projectcrescendo;

import com.projectcrescendo.projectcrescendo.models.Accidental;
import com.projectcrescendo.projectcrescendo.models.ConcreteNote;
import com.projectcrescendo.projectcrescendo.models.Dynamic;
import com.projectcrescendo.projectcrescendo.models.Intonation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A JUnit test class to test the ConcreteNote implementation.
 *
 * Created by Craig Hirst on 02/03/2016
 */
public class ConcreteNoteTest {
    private static ConcreteNote testConcreteNote;

    /**
     * Constructs test environment framework before testing
     */
    @BeforeClass
    public static void constructTestEnv() {
        System.out.println("Constructing test environment...");

        testConcreteNote = new ConcreteNote("A1");
    }

    /**
     * Removes test environment framework after testing
     */
    @AfterClass
    public static void dismantleTestEnv() {
        testConcreteNote = null;

        System.out.println("Testing complete.");
    }

    /**
     * Reassembles test environment before test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Construct a test-concreteNote
        testConcreteNote.setPitch("C1");
        testConcreteNote.addAccidental(Accidental.Natural);
        testConcreteNote.addIntonation(Intonation.Legato);
        testConcreteNote.setDynamic(Dynamic.MezzoForte);
        testConcreteNote.setLength(1);
    }

    /**
     * Disassembles test environment after test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        // Reset test-concreteNote back to default
        testConcreteNote = new ConcreteNote("A1");
    }

    /**
     * Tests setPitch() method. Passes if new Pitch is set successfully.
     * @throws Exception
     */
    @Test
    public void testSetPitch() throws Exception {
        // Passes if returned pitch is equal to the pitch we set up (C1)
        Assert.assertEquals("C1", testConcreteNote.getPitch());
    }

    /**
     * Tests getPitch() method. Passes if correct Pitch is successfully returned.
     * @throws Exception
     */
    @Test
    public void testGetPitch() throws Exception {
        // Passes if returned pitch is equal to the pitch we set up (C1)
        Assert.assertEquals("C1", testConcreteNote.getPitch());
    }

    /**
     * Tests addAccidental() method. Passes if new Accidental is successfully added.
     * @throws Exception
     */
    @Test
    public void testAddAccidental() throws Exception {
        // Passes if returned Accidental is equal to the Accidental we set up (Natural)
        Assert.assertEquals(Accidental.Natural, testConcreteNote.getAccidental());
    }

    /**
     * Tests getAccidental() method. Passes if correct accidental is returned.
     * @throws Exception
     */
    @Test
    public void testGetAccidental() throws Exception {
        // Passes if returned Accidental is equal to the Accidental we set up (Natural)
        Assert.assertEquals(Accidental.Natural, testConcreteNote.getAccidental());
    }

    /**
     * Tests removeAccidental() method. Passes if correct Accidental is successfully removed.
     * @throws Exception
     */
    @Test
    public void testRemoveAccidental() throws Exception {
        // Passes if the returned value is the default Accidental (Natural)
        testConcreteNote.removeAccidental();
        Assert.assertEquals(Accidental.Natural, testConcreteNote.getAccidental());
    }

    /**
     * Tests addIntonation() method. Passes if new Intonation is successfully added.
     * @throws Exception
     */
    @Test
    public void testAddIntonation() throws Exception {
        // Passes if returned Intonation is equal to the Intonation we set up (Legato)
        Assert.assertEquals(Intonation.Legato, testConcreteNote.getIntonation());
    }

    /**
     * Tests getIntonation() method. Passes if correct Intonation is returned.
     * @throws Exception
     */
    @Test
    public void testGetIntonation() throws Exception {
        // Passes if returned Intonation is equal to the Intonation we set up (Legato)
        Assert.assertEquals(Intonation.Legato, testConcreteNote.getIntonation());
    }

    /**
     * Tests removeIntonation() method. Passes if correct Intonation is successfully removed.
     * @throws Exception
     */
    @Test
    public void testRemoveIntonation() throws Exception {
        // Passes if the returned value is the default Intonation (Accent)
        testConcreteNote.removeIntonation();
        Assert.assertEquals(Intonation.Accent, testConcreteNote.getIntonation());
    }

    /**
     * Tests setDynamic() method. Passes if new Dynamic is set successfully.
     * @throws Exception
     */
    @Test
    public void testSetDynamic() throws Exception {
        // Passes if returned Dynamic is equal to the Dynamic we set up (MezzoForte)
        Assert.assertEquals(Dynamic.MezzoForte, testConcreteNote.getDynamic());
    }

    /**
     * Tests getDynamic() method. Passes if correct Dynamic is successfully returned.
     * @throws Exception
     */
    @Test
    public void testGetDynamic() throws Exception {
        // Passes if returned Dynamic is equal to the Dynamic we set up (MezzoForte)
        Assert.assertEquals(Dynamic.MezzoForte, testConcreteNote.getDynamic());
    }

    /**
     * Tests setLength() method. Passes if new Length is set successfully.
     * @throws Exception
     */
    @Test
    public void testSetLength() throws Exception {
        // Passes if returned Length is equal to the Length we set up (1)
        Assert.assertEquals(1, (int) testConcreteNote.getLength());
    }

    /**
     * Tests getLength() method. Passes if correct Length is returned.
     * @throws Exception
     */
    @Test
    public void testGetLength() throws Exception {
        // Passes if returned Length is equal to the Length we set up (1)
        Assert.assertEquals(1, (int) testConcreteNote.getLength());
    }

    /**
     * Tests toString() method. Passes if correct String is returned.
     * @throws Exception
     */
    @Test
    public void testToString() throws Exception {
        // Passes if returned string is equal to "Note-name: C1 length: 1"
        Assert.assertEquals("Note - name: C1 length: 1.0", testConcreteNote.toString());
    }
}