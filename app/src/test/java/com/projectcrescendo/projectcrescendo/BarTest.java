package com.projectcrescendo.projectcrescendo;

import com.projectcrescendo.projectcrescendo.models.Bar;
import com.projectcrescendo.projectcrescendo.models.Beat;
import com.projectcrescendo.projectcrescendo.models.ConcreteNote;
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
 *
 * Created by Craig Hirst on 02/03/2016
 */
public class BarTest {
    private static List<Note> listOfNotes;
    private static Bar testBar;
    private static Beat testBeat;
    private static ConcreteNote noteA1;
    private static ConcreteNote noteB1;

    @BeforeClass
     public static void constructTestEnv() {
        System.out.println("Constructing test environment...");

        listOfNotes = new ArrayList<>();
        testBar = new Bar();
        testBeat = new Beat();
        noteA1 = new ConcreteNote("A1");
        noteB1 = new ConcreteNote("B1");
    }

    @AfterClass
    public static void dismantleTestEnv() {
        listOfNotes = null;
        testBar = null;
        testBeat = null;
        noteA1 = null;
        noteB1 = null;

        System.out.println("Testing complete.");
    }

    @Before
    public void setUp() throws Exception {
        // Construct a test-beat
        listOfNotes.add(noteA1);
        listOfNotes.add(noteB1);
        testBeat.setNotes(listOfNotes);

        // Construct a test-bar
        testBar.addBeat(testBeat);
    }

    @After
    public void tearDown() throws Exception {
        // Empty all notes from the test-beat.
        listOfNotes.clear();
        testBeat.setNotes(listOfNotes);

        // Replace test-bar with an empty test-bar
        testBar = new Bar();
    }

    @Test
    public void testAddBeat() throws Exception {
        // Passes if there is 1 item in the list of beats (Only test-beat should be in list).
        Assert.assertEquals(1, testBar.getBeats().size());
    }

    @Test
    public void testGetBeats() throws Exception {
        // Passes if test-beat is in the returned list.
        Assert.assertTrue(testBar.getBeats().contains(testBeat));
    }

    @Test
    public void testGetAllNotesOnBar() throws Exception {
        // Passes if returned List is of size 2 (should only contain noteA1 and noteB1)
        Assert.assertEquals(2, testBar.getAllNotesOnBar().size());
    }
}