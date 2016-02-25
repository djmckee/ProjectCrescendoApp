package com.projectcrescendo.projectcrescendo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.projectcrescendo.projectcrescendo.models.Beat;
import com.projectcrescendo.projectcrescendo.models.Intonation;
import com.projectcrescendo.projectcrescendo.models.Note;
import com.projectcrescendo.projectcrescendo.models.Stave;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class TutorialActivity extends ActionBarActivity implements CustomAdapterListener, AddNoteFragmentListener, AdapterView.OnItemSelectedListener {

    /**
     * The Stave for this composition.
     */
    private Stave stave;

    /**
     * A conveneince placeholder; holds the current beat being edited whilst adding/removing notes, changing Intonation, etc.
     */
    private Beat currentBeat;

    private GridView gridView;

    Spinner timeSignatureR1;
    Spinner timeSignatureR2;
    Spinner timeSignatureL1;
    Spinner timeSignatureL2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        stave = new Stave();


        gridView = (GridView) findViewById(R.id.gridView1);
        refreshGrid();


        // Set up spinner...
        // Ambrose: got code from http://prasans.info/2011/03/add-edittexts-dynamically-and-retrieve-values-android/ for the Spinner UI
        timeSignatureR1 = (Spinner) findViewById(R.id.right_hand_time_signature_1);
        timeSignatureR2 = (Spinner) findViewById(R.id.right_hand_time_signature_2);
        timeSignatureL1 = (Spinner) findViewById(R.id.left_hand_time_signature_1);
        timeSignatureL2 = (Spinner) findViewById(R.id.left_hand_time_signature_2);

        TimeSignatureManager timeSignatureManager = new TimeSignatureManager(this);

        List<Integer> timeSignatureNumeratorIntegers = timeSignatureManager.getTimeSignatureNumerators();
        List<Integer> timeSignatureDenominatorIntegers = timeSignatureManager.getTimeSignatureDenominator();

        List<String> timeSignatureNumeratorStrings = new ArrayList<String>();
        List<String> timeSignatureDenominatorStrings = new ArrayList<String>();

        for (int numerator : timeSignatureNumeratorIntegers) {
            String stringValue = String.format("%d", numerator);
            timeSignatureNumeratorStrings.add(stringValue);
        }

        for (int denominator : timeSignatureDenominatorIntegers) {
            String stringValue = String.format("%d", denominator);
            timeSignatureDenominatorStrings.add(stringValue);
        }

        ArrayAdapter<String> topTimeSignatureAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeSignatureNumeratorStrings);
        ArrayAdapter<String> lowerTimeSignatureAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeSignatureDenominatorStrings);

        topTimeSignatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lowerTimeSignatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        timeSignatureR1.setAdapter(topTimeSignatureAdapter);
        timeSignatureR2.setAdapter(lowerTimeSignatureAdapter);
        timeSignatureL1.setAdapter(topTimeSignatureAdapter);
        timeSignatureL2.setAdapter(lowerTimeSignatureAdapter);

        timeSignatureR1.setOnItemSelectedListener(this);
        timeSignatureR2.setOnItemSelectedListener(this);
        timeSignatureL1.setOnItemSelectedListener(this);
        timeSignatureL2.setOnItemSelectedListener(this);

    }

    public void refreshGrid() {
        // Create a string array from the current stave's bars and beats...
        List<String> notesAsStringList = new ArrayList<String>();


        // Add for lower stave
        for (Beat beat : stave.getLowerBar().getBeats()) {
            String notesForBeat = beat.gridStringRepresentation();
            notesAsStringList.add(notesForBeat);
        }


        // Add for upper stave
        for (Beat beat : stave.getUpperBar().getBeats()) {
            String notesForBeat = beat.gridStringRepresentation();
            notesAsStringList.add(notesForBeat);
        }

        // Convert to primitive String array
        String[] noteNamesArray = new String[notesAsStringList.size()];
        notesAsStringList.toArray(noteNamesArray);

        // Hand to adapter; refresh grid.
        CustomAdapter adapter = new CustomAdapter(this, noteNamesArray);
        adapter.setAdapterListener(this);

        gridView.setAdapter(adapter);

    }

    /**
     * This method is called when a grid item is tapped; the index of the tapped grid item is passed
     * into it as a parameter.
     * @param position the index of the grid item that was clicked.
     */
    public void onItemTapListener(int position) {
        Log.d("TutorialActivity", "onItemClicked");

        // Work out what beat is being edited...
        int maxBarLength = Stave.BEATS_PER_BAR;

        Log.d("TutorialActivity", "position tapped: " + position);
        Log.d("TutorialActivity", "maxBarLength: " + maxBarLength);

        if (position > (maxBarLength - 1)) {
            // upper bar, take away max bar length to get true position
            Log.d("TutorialActivity", "upper bar");

            int truePosition = position - maxBarLength;
            currentBeat = stave.getUpperBar().getBeats().get(truePosition);

        } else {
            // lower bar
            Log.d("TutorialActivity", "lower bar");

            currentBeat = stave.getLowerBar().getBeats().get(position);

        }



        // Present the add note fragment...
        AddNoteFragment addNoteFragment = new AddNoteFragment();

        // set note fragment up for current bar
        addNoteFragment.setCurrentIntonation(currentBeat.getIntonation());
        addNoteFragment.setNotesForCurrentBar(currentBeat.getNotes());

        addNoteFragment.setAddNoteFragmentListener(this);

        addNoteFragment.show(getSupportFragmentManager(), "Add Note");



    }

    @Override
    public void addNoteFragmentAddedNote(AddNoteFragment addNoteFragment, Note note) {
        // Add the note to the current beat...
        currentBeat.getNotes().add(note);

        // Refresh grid to reflect changes in UI
        refreshGrid();

    }

    @Override
    public void addNoteFragmentDeletedNote(AddNoteFragment addNoteFragment, Note note) {
        // Delete the note from the current beat...
        currentBeat.getNotes().remove(note);

        // Refresh grid to reflect changes in UI
        refreshGrid();

    }

    @Override
    public void addNoteFragmentIntonationSelected(AddNoteFragment addNoteFragment, Intonation newIntonation) {
        // Set intonation on the current beat
        currentBeat.setIntonation(newIntonation);

    }

    // TODO: Fix this!!!! (´･_･`)
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("time sig", "selector view = : " + view);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d("time sig", "no time signature selected");

    }
}
