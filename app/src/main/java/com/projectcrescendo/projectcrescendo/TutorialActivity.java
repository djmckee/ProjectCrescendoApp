package com.projectcrescendo.projectcrescendo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.projectcrescendo.projectcrescendo.models.Beat;
import com.projectcrescendo.projectcrescendo.models.Intonation;
import com.projectcrescendo.projectcrescendo.models.Note;
import com.projectcrescendo.projectcrescendo.models.Stave;
import com.projectcrescendo.projectcrescendo.models.Tutorial;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The tutorial activity contains the grid and is where the user completes the tutorial by adding
 * notes.
 * <p>
 * Created by Alex on 27/02/16.
 * Modified by Dylan, Ambrose and Jordan.
 */
public class TutorialActivity extends AppCompatActivity implements NoteGridViewAdapterListener, AddNoteFragmentListener {

    /**
     * The Stave for this composition.
     */
    private Stave stave;

    /**
     * A convenience placeholder; holds the current beat being edited whilst adding/removing notes, changing Intonation, etc.
     */
    private Beat currentBeat;

    /**
     * The grid that contains the stave's beats.
     */
    private GridView gridView;

    /**
     * A Spinner to allow the user to select the time signature numerator for the current stave.
     */
    private Spinner rightTimeSignatureNumeratorSpinner;

    /**
     * A Spinner to allow the user to select the time signature denominator for the current stave.
     */
    private Spinner rightTimeSignatureDenominatorSpinner;

    /**
     * A Spinner to allow the user to select the time signature numerator for the current stave.
     */
    private Spinner leftTimeSignatureNumeratorSpinner;

    /**
     * A Spinner to allow the user to select the time signature denominator for the current stave.
     */
    private Spinner leftTimeSignatureDenominatorSpinner;

    /**
     * A list of possible time signature numerators, populated from the TimeSignatureManager
     */
    private List<Integer> timeSignatureNumerators;

    /**
     * A list of possible time signature denominators, populated from the TimeSignatureManager
     */
    private List<Integer> timeSignatureDenominators;

    /**
     * The current tutorial being done by the user in this activity, if there is one and the app
     * isn't in 'free play' mode.
     */
    private Tutorial tutorial;

    /**
     * The index of the current instruction on display to the user from the instructions array
     * attached to the current Tutorial instance; assuming that the app is in tutorial mode.
     * <p>
     * Defaults to -1 to mark an invalid setup if the app isn't in tutorial mode.
     */
    private int instructionIndex = -1;

    /**
     * An instance of the tutorial manager to allow tutorials to be selected from the tutorials
     * list stored within the database, and completed within this current activity.
     */
    private TutorialManager tutorialManager;

    /**
     * A label to hold a piece of instructional text from the Tutorial.
     */
    private TextView instructionalTextView;

    /**
     * Sets up the initial grid view and the time signature selection spinner UI on initial load of
     * the activity, along with the tutorial selection menu, and the creation of a new blank stave.
     *
     * @param savedInstanceState not used in our implementation.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        stave = new Stave();

        gridView = (GridView) findViewById(R.id.gridView1);

        refreshGrid();


        // Set up spinner...
        // Ambrose: got code from http://prasans.info/2011/03/add-edittexts-dynamically-and-retrieve-values-android/ for the Spinner UI
        rightTimeSignatureNumeratorSpinner = (Spinner) findViewById(R.id.right_hand_time_signature_1);
        rightTimeSignatureDenominatorSpinner = (Spinner) findViewById(R.id.right_hand_time_signature_2);
        leftTimeSignatureNumeratorSpinner = (Spinner) findViewById(R.id.left_hand_time_signature_1);
        leftTimeSignatureDenominatorSpinner = (Spinner) findViewById(R.id.left_hand_time_signature_2);

        TimeSignatureManager timeSignatureManager = new TimeSignatureManager(this);

        timeSignatureNumerators = timeSignatureManager.getTimeSignatureNumerators();
        timeSignatureDenominators = timeSignatureManager.getTimeSignatureDenominator();

        List<String> timeSignatureNumeratorStrings = new ArrayList<String>();
        List<String> timeSignatureDenominatorStrings = new ArrayList<String>();

        for (int numerator : timeSignatureNumerators) {
            String stringValue = String.format(Locale.UK, "%d", numerator);
            timeSignatureNumeratorStrings.add(stringValue);
        }

        for (int denominator : timeSignatureDenominators) {
            String stringValue = String.format(Locale.UK, "%d", denominator);
            timeSignatureDenominatorStrings.add(stringValue);
        }


        ArrayAdapter<String> topTimeSignatureAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeSignatureNumeratorStrings);
        ArrayAdapter<String> lowerTimeSignatureAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeSignatureDenominatorStrings);

        topTimeSignatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lowerTimeSignatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        rightTimeSignatureNumeratorSpinner.setAdapter(topTimeSignatureAdapter);
        rightTimeSignatureDenominatorSpinner.setAdapter(lowerTimeSignatureAdapter);
        leftTimeSignatureNumeratorSpinner.setAdapter(topTimeSignatureAdapter);
        leftTimeSignatureDenominatorSpinner.setAdapter(lowerTimeSignatureAdapter);


        rightTimeSignatureNumeratorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("spinner 1", "item selected!!!");
                int newSignature = timeSignatureNumerators.get(position);
                stave.setTimeSignatureNumerator(newSignature);

                leftTimeSignatureNumeratorSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("spinner 1", "no item selected!!!");

            }
        });

        rightTimeSignatureDenominatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("spinner 2", "item selected!!!");
                int newSignature = timeSignatureDenominators.get(position);
                stave.setTimeSignatureDenominator(newSignature);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("spinner 2", "no item selected!!!");

            }
        });

        leftTimeSignatureNumeratorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("spinner 3", "item selected!!!");
                int newSignature = timeSignatureNumerators.get(position);
                stave.setTimeSignatureNumerator(newSignature);

                rightTimeSignatureNumeratorSpinner.setSelection(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("spinner 3", "no item selected!!!");

            }
        });

        leftTimeSignatureDenominatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("spinner 4", "item selected!!!");
                int newSignature = timeSignatureDenominators.get(position);
                stave.setTimeSignatureDenominator(newSignature);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("spinner 4", "no item selected!!!");

            }
        });


        FloatingActionButton exportButton = (FloatingActionButton) findViewById(R.id.export);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Push tutorial playback view!
                Log.d("TutorialActivity", "export button tapped");

                // Encode the stave to MusicXML for the playback activity to playback...
                String musicXMLRepresentation = MusicXmlWriter.encode(stave);

                Intent intent = new Intent(TutorialActivity.this, PlaybackActivity.class);
                intent.putExtra(PlaybackActivity.SCORE_STRING_KEY, musicXMLRepresentation);
                startActivity(intent);

            }
        });


        instructionalTextView = (TextView) findViewById(R.id.tutorialActivityInstructionText);

        // Set the instructional text view to a default...
        instructionalTextView.setText(R.string.initialInstructionText);

        // When the instructional text snippet is tapped, show it in full...
        instructionalTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TutorialFragment tutorialFragment = new TutorialFragment();
                tutorialFragment.setTutorialText((String) instructionalTextView.getText());
                tutorialFragment.show(getSupportFragmentManager(), "Tutorial");

            }
        });

        FloatingActionButton instructionButton = (FloatingActionButton) findViewById(R.id.instruction);
        instructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TutorialActivity", "instruction button tapped");
                Intent intent = new Intent(TutorialActivity.this, InstructionsActivity.class);
                startActivity(intent);
            }
        });


        FloatingActionButton verificationButton = (FloatingActionButton) findViewById(R.id.verify);

        verificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateButtonClicked();
            }
        });


        tutorialManager = new TutorialManager(this);
        List<Tutorial> tutorials = tutorialManager.getTutorialsList();

        // Invert the list so the first tutorial in the database appears at the top of the menu
        // This is due to the LIFO nature of the FloatingActionsMenu...
        List<Tutorial> placeholderList = new ArrayList<Tutorial>();

        for (int i = tutorials.size() - 1; i >= 0; i--) {
            placeholderList.add(tutorials.get(i));
        }

        tutorials = placeholderList;

        final FloatingActionsMenu tutorialSelectionMenu = (FloatingActionsMenu) findViewById(R.id.sonata_tutorial);

        // Create a menu item for each tutorial...
        for (final Tutorial tutorial : tutorials) {
            FloatingActionButton tutorialSelectionButton = new FloatingActionButton(this);
            tutorialSelectionButton.setColorNormal(R.color.white);
            tutorialSelectionButton.setColorPressed(R.color.purple);
            tutorialSelectionButton.setTitle(tutorial.getTitle());

            tutorialSelectionButton.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    // Load relevant tutorial
                    setTutorial(tutorial);

                    // Close the menu, tutorial has been loaded...
                    tutorialSelectionMenu.collapse();

                }
            });

            tutorialSelectionMenu.addButton(tutorialSelectionButton);
        }

    }


    /**
     * Updates the grid with the current stave's beats. To be performed after a notes is added or
     * removed, so that the note shows up on the grid graphically.
     */
    private void refreshGrid() {
        // Create a string array from the current stave's bars and beats...
        List<String> notesAsStringList = new ArrayList<String>();

        // Add for lower stave
        for (Beat beat : stave.getLowerClef().getBeats()) {
            String notesForBeat = beat.gridStringRepresentation();
            notesAsStringList.add(notesForBeat);
        }

        // Add for upper stave
        for (Beat beat : stave.getUpperClef().getBeats()) {
            String notesForBeat = beat.gridStringRepresentation();
            notesAsStringList.add(notesForBeat);
        }

        // Convert to primitive String array
        String[] noteNamesArray = new String[notesAsStringList.size()];
        notesAsStringList.toArray(noteNamesArray);

        // Hand to adapter; refresh grid.
        NoteGridViewAdapter adapter = new NoteGridViewAdapter(this, noteNamesArray);
        adapter.setAdapterListener(this);

        gridView.setAdapter(adapter);

    }

    /**
     * This method is called when a grid item is tapped; the index of the tapped grid item is passed
     * into it as a parameter.
     *
     * @param position the index of the grid item that was clicked.
     */
    public void onItemTapListener(int position) {
        Log.d("TutorialActivity", "onItemClicked");

        // Work out what beat is being edited...
        int maxBarLength = Stave.BEATS_PER_ROW;

        Log.d("TutorialActivity", "position tapped: " + position);
        Log.d("TutorialActivity", "maxBarLength: " + maxBarLength);

        if (position > (maxBarLength - 1)) {
            // upper bar, take away max bar length to get true position
            Log.d("TutorialActivity", "upper bar");

            int truePosition = position - maxBarLength;
            currentBeat = stave.getUpperClef().getBeats().get(truePosition);

        } else {
            // lower bar
            Log.d("TutorialActivity", "lower bar");

            currentBeat = stave.getLowerClef().getBeats().get(position);

        }

        // Present the add note fragment...
        AddNoteFragment addNoteFragment = new AddNoteFragment();

        // set note fragment up for current bar
        addNoteFragment.setCurrentIntonation(currentBeat.getIntonation());
        addNoteFragment.setNotesForCurrentBeat(currentBeat.getNotes());

        addNoteFragment.setAddNoteFragmentListener(this);

        addNoteFragment.show(getSupportFragmentManager(), "Add Note");


    }

    /**
     * Called by the AddNoteFragment when a note is added to the stave by the user. This method
     * needs to add the note to the current beat that they're editing, and refresh the grid UI so
     * that the note shows up graphically to the user.
     *
     * @param addNoteFragment the fragment instance that the note was added from.
     * @param note            the note that was added by the user.
     */
    @Override
    public void addNoteFragmentAddedNote(AddNoteFragment addNoteFragment, Note note) {
        // Add the note to the current beat...
        currentBeat.getNotes().add(note);

        // Refresh grid to reflect changes in UI
        refreshGrid();

    }

    /**
     * Called by the AddNoteFragment when a note is removed from the stave by the user. This method
     * needs to remove the note to the current beat that they're editing, and refresh the grid UI so
     * that the note removal shows up graphically to the user.
     *
     * @param addNoteFragment the fragment instance that the note was removed from.
     * @param note            the note that was removed by the user.
     */
    @Override
    public void addNoteFragmentDeletedNote(AddNoteFragment addNoteFragment, Note note) {
        // Delete the note from the current beat...
        currentBeat.getNotes().remove(note);

        // Refresh grid to reflect changes in UI
        refreshGrid();

    }

    /**
     * Called by the AddNoteFragment when the intonation for the currently selected beat is changed.
     *
     * @param addNoteFragment the fragment instance that the intonation was edited from.
     * @param newIntonation   the new intonation for the beat.
     */
    @Override
    public void addNoteFragmentIntonationSelected(AddNoteFragment addNoteFragment, Intonation newIntonation) {
        // Set intonation on the current beat
        currentBeat.setIntonation(newIntonation);

    }

    /**
     * The current tutorial being completed by the user, if one exists.
     *
     * @return the tutorial currently being completed by the user.
     */
    public Tutorial getTutorial() {
        return tutorial;
    }

    /**
     * Sets the Tutorial for the user to complete, starting from the first instruction in the tutorial.
     *
     * @param tutorial the Tutorial instance for the user to complete in this TutorialActivity.
     */
    private void setTutorial(Tutorial tutorial) {
        // Set tutorial placeholder
        this.tutorial = tutorial;

        // Instructions will need to start from the first (0)...
        instructionIndex = 0;

        // Remove any existing notes and dynamics from the grid by re-setting the stave...
        stave = new Stave();

        // Reset time signatures
        rightTimeSignatureNumeratorSpinner.setSelection(0);
        rightTimeSignatureDenominatorSpinner.setSelection(0);
        leftTimeSignatureNumeratorSpinner.setSelection(0);
        leftTimeSignatureDenominatorSpinner.setSelection(0);

        // Get upper and lower stave instances...
        List<Beat> upperBeats = stave.getUpperClef().getBeats();
        List<Beat> lowerBeats = stave.getLowerClef().getBeats();


        // Pre-load the stave with the tutorial's pre-filled notes...
        List<Beat> topPrefilledBeats = tutorial.getPrePopulatedBeatsForUpperClef();
        Log.d("TutorialActivity", "topPrefilledBeats = " + topPrefilledBeats);
        for (int i = 0; i < upperBeats.size(); i++) {
            Beat upperBeat = upperBeats.get(i);
            Beat prefilledBeat = topPrefilledBeats.get(i);
            upperBeat.setIntonation(prefilledBeat.getIntonation());
            upperBeat.setNotes(prefilledBeat.getNotes());
        }


        List<Beat> bottomPrefilledBeats = tutorial.getPrePopulatedBeatsForLowerClef();
        Log.d("TutorialActivity", "bottomPrefilledBeats = " + bottomPrefilledBeats);

        for (int i = 0; i < lowerBeats.size(); i++) {
            Beat lowerBeat = lowerBeats.get(i);
            Beat prefilledBeat = bottomPrefilledBeats.get(i);
            lowerBeat.setIntonation(prefilledBeat.getIntonation());
            lowerBeat.setNotes(prefilledBeat.getNotes());
        }

        // Refresh the grid UI to reflect these stave changes...
        refreshGrid();

        // Show the first instruction for this tutorial in a TutorialFragment to signify the change to the user...

        String firstInstruction = tutorial.getInstructions().get(0);

        // Check there's an instruction to display, then display it...
        if (firstInstruction != null) {
            TutorialFragment fragment = new TutorialFragment();

            fragment.setTutorialText(firstInstruction);

            fragment.show(getSupportFragmentManager(), "Tutorial");

            // Show instruction 1 in a text box
            instructionalTextView.setText(firstInstruction);

        }

    }

    /**
     * Returns true if the user is currently completing a Tutorial in this TutorialActivity.
     * <p>
     * Warning suppressed here because 'is in tutorial mode' is the natural language way of
     * answering the question that this method answers, so I do not want to invert it for the sake
     * of clarity.
     *
     * @return a boolean indicating whether or not the user is currently completing a Tutorial.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isInTutorialMode() {
        return (tutorial != null);
    }

    private void validateButtonClicked() {
        // Don't bother checking if there's no tutorial present...
        if (!isInTutorialMode()) {
            showNoTutorialError();

            return;
        }

        // If we're at the last instruction, don't bother validating.
        if (instructionIndex == tutorialManager.getTutorialsList().size()) {
            return;
        }

        // Work out where to validate up to...
        int validationLimit = tutorial.getTutorialPatternMatchIndex().get(instructionIndex);

        // Do the validation check...
        performTutorialCheck(validationLimit);

    }


    /**
     * A method to check whether or not the current tutorial has been completed correctly up to the
     * beat number passed into this method (the 'limit' parameter).
     *
     * @param limit the beat number to verify correctness of this tutorial up to.
     */
    private void performTutorialCheck(int limit) {
        // Don't bother checking if there's no tutorial present...
        if (!isInTutorialMode()) {
            showNoTutorialError();

            return;
        }

        // If we're at the last instruction, don't bother validating.
        if (instructionIndex == tutorialManager.getTutorialsList().size()) {
            return;
        }

        // Assume the bars are correct until we loop through to find a mistake...
        boolean upperBarCorrect = true;
        boolean lowerBarCorrect = true;

        List<Beat> upperBeats = stave.getUpperClef().getBeats();
        List<Beat> lowerBeats = stave.getLowerClef().getBeats();

        List<Beat> correctUpperBeats = tutorial.getValidBeatsForUpperClef();
        List<Beat> correctLowerBeats = tutorial.getValidBeatsForLowerClef();

        // Check up to the desired limit
        for (int i = 0; i < limit; i++) {
            Beat upperBeat = upperBeats.get(i);
            Beat lowerBeat = lowerBeats.get(i);

            Beat desiredUpperBeat = correctUpperBeats.get(i);
            Beat desiredLowerBeat = correctLowerBeats.get(i);

            if (!upperBeat.equals(desiredUpperBeat)) {
                // Invalid upper bar.
                upperBarCorrect = false;
            }

            if (!lowerBeat.equals(desiredLowerBeat)) {
                // Invalid lower bar.
                lowerBarCorrect = false;
            }

        }

        if (lowerBarCorrect && upperBarCorrect) {
            // Tutorial Valid.
            Log.d("TutorialActivity", "Tutorial valid");

            // Move to next instruction...
            moveToNextTutorialStep();

        } else {
            // Tutorial Invalid.
            Log.d("TutorialActivity", "Tutorial invalid");

            // Show error
            new AlertDialog.Builder(this)
                    .setTitle(R.string.invalid_tutorial_error_title)
                    .setMessage(R.string.invalid_tutorial_error_message)
                    .setPositiveButton(R.string.okay, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }


    }


    /**
     * A convenience method to reduce code duplication. Displays a simple 'Not in tutorial mode'
     * error message in a standard Android Alert Dialog.
     */
    private void showNoTutorialError() {
        // Show error if user tries to validate tutorial without ever selecting a tutorial
        new AlertDialog.Builder(this)
                .setTitle(R.string.no_tutorial_error_title)
                .setMessage(R.string.no_tutorial_error_message)
                .setPositiveButton(R.string.okay, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * A method that moves the user on to the next tutorial step. To be called when the user is in
     * tutorial mode and has successfully completed and validated their current tutorial step,
     * so as to let them move on to the next one.
     */
    private void moveToNextTutorialStep() {
        // Increment tutorial instruction counter
        instructionIndex++;

        String fragmentText;

        // If we're at the last instruction, show congratulation message
        if (instructionIndex == tutorialManager.getTutorialsList().size()) {
            // Show congratulatory fragment!
            fragmentText = String.format(getString(R.string.tutorial_complete_message), tutorial.getTitle());

        } else {
            // Go to next step...
            fragmentText = tutorial.getInstructions().get(instructionIndex);

        }

        instructionalTextView.setText(fragmentText);

        TutorialFragment tutorialFragment = new TutorialFragment();
        tutorialFragment.setTutorialText(fragmentText);
        tutorialFragment.show(getSupportFragmentManager(), "Tutorial");

    }

    /**
     * A method called when the save/open button is tapped. Displays an alert asking the user
     * whether they want to save or open a composition (i.e. a Stave).
     */
    private void saveOrOpenButtonTapped() {
        // I looked up the AlertDialog Builder at http://rajeshvijayakumar.blogspot.co.uk/2013/04/alert-dialog-dialog-with-item-list.html
        CharSequence[] alertChoiceTitles = {
                getString(R.string.save_choice_save),
                getString(R.string.save_choice_open)
        };

        // Create alert dialog with choices
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.save_choice_title);
        alertDialogBuilder.setItems(alertChoiceTitles, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                if (item == 0) {
                    // Save the current composition to the SQLite Database.

                    // Show loading UI...
                    ProgressDialog loadingDialog = ProgressDialog.show(TutorialActivity.this, getString(R.string.save_composition_loading_title), getString(R.string.save_composition_loading_message));
                    loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                    loadingDialog.setCancelable(false);

                    // Do save...
                    StaveManager staveManager = new StaveManager(TutorialActivity.this);
                    staveManager.writeStaveToDatabase(stave);

                    // Synchronous save complete; hide loading dialog
                    loadingDialog.hide();


                } else {
                    // Show saved compositions and allow the user to open one.
                }

            }

        });

        // Add choices to an alert and show it...
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}
