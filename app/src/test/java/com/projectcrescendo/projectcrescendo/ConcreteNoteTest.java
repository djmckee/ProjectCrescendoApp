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
 *
 * Created by Craig Hirst on 02/03/2016
 */
public class ConcreteNoteTest {
    private static ConcreteNote testConcreteNote;

    @BeforeClass
    public static void constructTestEnv() {
        System.out.println("Constructing test environment...");

        testConcreteNote = new ConcreteNote("A1");
    }

    @AfterClass
    public static void dismantleTestEnv() {
        testConcreteNote = null;

        System.out.println("Testing complete.");
    }

    @Before
    public void setUp() throws Exception {
        // Construct a test-concreteNote
        testConcreteNote.setPitch("C1");
        testConcreteNote.addAccidental(Accidental.Natural);
        testConcreteNote.addIntonation(Intonation.Legato);
        testConcreteNote.setDynamic(Dynamic.MezzoForte);
        testConcreteNote.setLength(1);
    }

    @After
    public void tearDown() throws Exception {
        // Reset test-concreteNote back to default
        testConcreteNote = new ConcreteNote("A1");
    }

    @Test
    public void testSetPitch() throws Exception {
        // Passes if returned pitch is equal to the pitch we set up (C1)
        Assert.assertEquals("C1", testConcreteNote.getPitch());
    }

    @Test
    public void testGetPitch() throws Exception {
        // Passes if returned pitch is equal to the pitch we set up (C1)
        Assert.assertEquals("C1", testConcreteNote.getPitch());
    }

    @Test
    public void testAddAccidental() throws Exception {
        // Passes if returned Accidental is equal to the Accidental we set up (Natural)
        Assert.assertEquals(Accidental.Natural, testConcreteNote.getAccidental());
    }

    @Test
    public void testGetAccidental() throws Exception {
        // Passes if returned Accidental is equal to the Accidental we set up (Natural)
        Assert.assertEquals(Accidental.Natural, testConcreteNote.getAccidental());
    }

    @Test
    public void testRemoveAccidental() throws Exception {
        // Passes if the returned value is the default Accidental (Natural)
        testConcreteNote.removeAccidental();
        Assert.assertEquals(Accidental.Natural, testConcreteNote.getAccidental());
    }

    @Test
    public void testAddIntonation() throws Exception {
        // Passes if returned Intonation is equal to the Intonation we set up (Legato)
        Assert.assertEquals(Intonation.Legato, testConcreteNote.getIntonation());
    }

    @Test
    public void testGetIntonation() throws Exception {
        // Passes if returned Intonation is equal to the Intonation we set up (Legato)
        Assert.assertEquals(Intonation.Legato, testConcreteNote.getIntonation());
    }

    @Test
    public void testRemoveIntonation() throws Exception {
        // Passes if the returned value is the default Intonation (Accent)
        testConcreteNote.removeIntonation();
        Assert.assertEquals(Intonation.Accent, testConcreteNote.getIntonation());
    }

    @Test
    public void testSetDynamic() throws Exception {
        // Passes if returned Dynamic is equal to the Dynamic we set up (MezzoForte)
        Assert.assertEquals(Dynamic.MezzoForte, testConcreteNote.getDynamic());
    }

    @Test
    public void testGetDynamic() throws Exception {
        // Passes if returned Dynamic is equal to the Dynamic we set up (MezzoForte)
        Assert.assertEquals(Dynamic.MezzoForte, testConcreteNote.getDynamic());
    }

    @Test
    public void testSetLength() throws Exception {
        // Passes if returned Length is equal to the Length we set up (1)
        Assert.assertEquals(1, (int) testConcreteNote.getLength());
    }

    @Test
    public void testGetLength() throws Exception {
        // Passes if returned Length is equal to the Length we set up (1)
        Assert.assertEquals(1, (int) testConcreteNote.getLength());
    }

    @Test
    public void testToString() throws Exception {
        // Passes if returned string is equal to "Note-name: C1 length: 1"
        Assert.assertEquals("Note - name: C1 length: 1.0", testConcreteNote.toString());
    }
}