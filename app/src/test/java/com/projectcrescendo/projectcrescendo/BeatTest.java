package com.projectcrescendo.projectcrescendo;

import com.projectcrescendo.projectcrescendo.models.Beat;
import com.projectcrescendo.projectcrescendo.models.ConcreteNote;
import com.projectcrescendo.projectcrescendo.models.Intonation;
import com.projectcrescendo.projectcrescendo.models.Note;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * A JUnit test class to test the Beat model class.
 *
 * Created by Craig Hirst on 02/03/2016
 */
public class BeatTest {
    private static Beat testBeat;
    private static List<Note> listOfNotes;
    private static ConcreteNote noteA1;
    private static ConcreteNote noteB1;

    /**
     * Constructs test environment framework before testing
     */
    @BeforeClass
    public static void constructTestEnv() {
        System.out.println("Constructing test environment...");

        listOfNotes = new ArrayList<>();
        testBeat = new Beat();
        noteA1 = new ConcreteNote("A1");
        noteB1 = new ConcreteNote("B1");
    }

    /**
     * Removes test environment framework after testing
     */
    @AfterClass
    public static void dismantleTestEnv() {
        listOfNotes = null;
        noteA1 = null;
        noteB1 = null;

        System.out.println("Testing complete.");
    }

    /**
     * Reassembles test environment before test
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Construct a test-beat
        listOfNotes.add(noteA1);
        listOfNotes.add(noteB1);
        testBeat.setNotes(listOfNotes);
    }

    /**
     * Disassembles test environment after test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        // Empty all notes from test-beat
        listOfNotes.clear();
        testBeat.setNotes(listOfNotes);
    }

    /**
     * Tests getNotes() method. Passes if correct list of Notes is returned.
     * @throws Exception
     */
    @Test
    public void testGetNotes() throws Exception {
        // Passes if returned List is equal to the listOfNotes List
        Assert.assertEquals(listOfNotes, testBeat.getNotes());
    }

    /**
     * Tests setNotes() method. Passes if Notes are successfully added to list.
     * @throws Exception
     */
    @Test
    public void testSetNotes() throws Exception {
        // Passes if the number of notes in the test-beat is equal to the number of notes set.
        Assert.assertEquals(2, testBeat.getNotes().size());
    }

    /**
     * Tests getIntonation() method. Passes if correct Intonation is returned.
     * @throws Exception
     */
    @Test
    public void testGetIntonation() throws Exception {
        // Passes if returned Intonation is equal to the default Intonation
        Assert.assertEquals(Intonation.Accent, testBeat.getIntonation());
    }

    /**
     * Tests setIntonation() method. Passes if Intonation is set successfully.
     * @throws Exception
     */
    @Test
    public void testSetIntonation() throws Exception {
        // Passes if returned Intonation is equal to the Intonation which was set
        testBeat.setIntonation(Intonation.Legato);
        Assert.assertEquals(Intonation.Legato, testBeat.getIntonation());
    }

    /**
     * Tests gridStringRepresentation() method. Passes if correct string is returned.
     * @throws Exception
     */
    @Test
    public void testGridStringRepresentation() throws Exception {
        // Passes if returned string is equal to "\nA1\nB1"
        Assert.assertEquals("\nA1\nB1", testBeat.gridStringRepresentation());
    }
}