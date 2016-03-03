package com.projectcrescendo.projectcrescendo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;



import com.projectcrescendo.projectcrescendo.models.ConcreteNote;
import com.projectcrescendo.projectcrescendo.models.Intonation;
import com.projectcrescendo.projectcrescendo.models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface to allow the AddNoteFragment to communicate changes from its UI (such as note
 * additions/removals and intonation changes) back to the TutorialActivity instance that presented
 * the fragment UI.
 */
interface AddNoteFragmentListener {
    /**
     * Called by the AddNoteFragment when a note is added to the stave by the user. This method
     * needs to add the note to the current beat that they're editing, and refresh the grid UI so
     * that the note shows up graphically to the user.
     *
     * @param addNoteFragment the fragment instance that the note was added from.
     * @param note the note that was added by the user.
     */
    void addNoteFragmentAddedNote(AddNoteFragment addNoteFragment, Note note);

    /**
     * Called by the AddNoteFragment when a note is removed from the stave by the user. This method
     * needs to remove the note to the current beat that they're editing, and refresh the grid UI so
     * that the note removal shows up graphically to the user.
     *
     * @param addNoteFragment the fragment instance that the note was removed from.
     * @param note the note that was removed by the user.
     */
    void addNoteFragmentDeletedNote(AddNoteFragment addNoteFragment, Note note);

    /**
     * Called by the AddNoteFragment when the intonation for the currently selected beat is changed.
     *
     * @param addNoteFragment the fragment instance that the intonation was edited from.
     * @param newIntonation the new intonation for the beat.
     */
    void addNoteFragmentIntonationSelected(AddNoteFragment addNoteFragment, Intonation newIntonation);
}

/**
 * A fragment UI to allow users to add and remove notes from the current beat, and edit the
 * intonation of the current beat. Presented from the TutorialActivity, this class uses the
 * AddNoteFragmentListener interface (defined above) to give callbacks to the TutorialActivity, so
 * that the activity can update the Stave model.
 *
 * This design pattern allows us to adhere to the Model-View-Controller (MVC) design pattern.
 *
 * Created by Dylan McKee on 22/02/2016.
 */
public class AddNoteFragment extends DialogFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, SelectIntonationFragmentCallbackListener {

    /**
     * The intonation of the current bar being edited in this fragment.
     */
    private Intonation currentIntonation;

    /**
     * A list of Notes for the current Bar instance being edited by this fragment.
     */
    private List<Note> notesForCurrentBar;

    /**
     * A list view to present the list of notes for the current bar in so that they can be edited.
     */
    private ListView listView;
    private ArrayAdapter<String> listViewAdapter;

    private Button intonationButton;

    private AddNoteFragmentListener addNoteFragmentListener;

    // A class-wide note placeholder
    Note noteToAdd = null;

    public AddNoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddNoteFragment.
     */
    public static AddNoteFragment newInstance(Intonation intonation, List<Note> notesForCurrentBar) {
        AddNoteFragment fragment = new AddNoteFragment();

        fragment.currentIntonation = intonation;
        fragment.notesForCurrentBar = notesForCurrentBar;

        return fragment;
    }


    /**
     * When the fragment is created, this method ensures that the UI elements are instantiated from
     * the XML and refreshes the notes list and intonation initially.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) this.getView().findViewById(R.id.noteListView);

        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        intonationButton = (Button) this.getView().findViewById(R.id.intonationSelectionButton);
        intonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIntonation();
            }
        });

        if (notesForCurrentBar == null) {
            notesForCurrentBar = new ArrayList<Note>();
        }

        refreshNotesList();

        refreshIntonation();


    }


    /**
     * This method presents the 'Select Intonation Fragment' when the 'intonation' button is tapped.
     *
     */
    void selectIntonation() {
        // Present a modal that allows the user to select intonation...

        SelectIntonationFragment selectIntonationFragment = new SelectIntonationFragment();

        selectIntonationFragment.setListener(this);

        selectIntonationFragment.show(getActivity().getFragmentManager(), "Add intonation");

    }

    void refreshIntonation() {
        intonationButton.setText("Expression: " + currentIntonation);
    }

    void refreshNotesList() {
        // Refresh the Android ListView...

        List<String> noteTitleList = new ArrayList<String>();

        // Add note names to the list...
        for (Note note : notesForCurrentBar) {
            Log.d("AddNoteFragment", "note = " + note.toString());

            String title = String.format("%s - length: %.2f", note.getPitch(), note.getLength());

            noteTitleList.add(title);

        }

        // If there's less than 5 notes in the bar; add the option to add a note too...
        if (notesForCurrentBar.size() < 5) {
            noteTitleList.add("Add new note +");
        }

        listViewAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.note_list_row, noteTitleList);

        // refresh using the list view adapter
        listView.setAdapter(listViewAdapter);

    }


    void addNewNote() {
        // Get all possible note names so we have something to validate against...
        final List<String> validNoteNames = new NoteManager(getActivity()).getNoteNames();



        // Get the note name...
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(getActivity());

        inputDialog.setTitle("Enter note name");
        final AutoCompleteTextView inputTextView = new AutoCompleteTextView(getActivity());

        List<String> noteNameStrings = new NoteManager(getActivity()).getNoteNames();

        // Convert from list to array as per solution from https://stackoverflow.com/questions/9572795/convert-list-to-array-in-java
        String[] noteNameArray = new String[noteNameStrings.size()];
        noteNameStrings.toArray(noteNameArray);

        ArrayAdapter<String> autoCompleteNotesAdapter = new ArrayAdapter<String>(getActivity() ,android.R.layout.simple_list_item_1, noteNameArray);
        inputTextView.setAdapter(autoCompleteNotesAdapter);

        inputDialog.setView(inputTextView);

        inputDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int buttonId) {
                // Get user input string
                String textInput = inputTextView.getText().toString();

                // The database stores only uppercase note names; do a quick conversion...
                textInput = textInput.toUpperCase();

                // Perform some input validation on the entered note...
                boolean isValid = false;

                for (String noteName : validNoteNames) {
                    if (noteName.toUpperCase().equals(textInput)) {
                        // Input is valid!
                        isValid = true;

                        // Sort out any case sensitivity issues to ensure that notes appear on the composition exactly as they do in the Notes List in the database
                        textInput = noteName;
                    }
                }


                // If the note's valid, instantiate the new note and ask for a length - otherwise, error
                if (isValid) {
                    noteToAdd = new ConcreteNote(textInput);

                    // Now, ask for note length...
                    SelectNoteLengthFragment selectNoteLengthFragment = new SelectNoteLengthFragment();

                    selectNoteLengthFragment.setListener(new SelectNoteLengthFragmentCallbackListener() {
                        @Override
                        public void noteLengthSelectedFromFragment(SelectNoteLengthFragment fragment, double length) {
                            if (noteToAdd != null) {
                                noteToAdd.setLength(length);

                                // there's a note to add; add it and refresh...
                                Log.d("AddNote", ("Added note " + noteToAdd));

                                // Tell the listener...
                                if (addNoteFragmentListener != null) {
                                    addNoteFragmentListener.addNoteFragmentAddedNote(AddNoteFragment.this, noteToAdd);
                                }

                                refreshNotesList();

                                // Don't allow the same note to be added multiple times...
                                noteToAdd = null;
                            }


                        }
                    });

                    selectNoteLengthFragment.show(getActivity().getFragmentManager(), "Select note length");



                } else {
                    // Display error message...
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Invalid note!")
                            .setMessage("Not a valid note name - please try again with a valid note name...")
                            .setPositiveButton("Okay", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }



            }
        });

        inputDialog.setNegativeButton("Cancel", null);

        inputDialog.create().show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }



    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // If it's the Add New Note button, add new note
        if (notesForCurrentBar.size() < 5) {
            if (position == notesForCurrentBar.size() || (notesForCurrentBar.size() == 0 && position == 0)) {
                // Add new note
                addNewNote();
            }
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // If it's the add note button, return false - otherwise - delete the note.
        if (notesForCurrentBar.size() < 5) {
            if (position == notesForCurrentBar.size() || (notesForCurrentBar.size() == 0 && position == 0)) {
                // Add new note button, just return false
                return false;
            }
        }

        final Note selectedNote = notesForCurrentBar.get(position);

        AlertDialog.Builder lengthInputDialog = new AlertDialog.Builder(getActivity());
        lengthInputDialog.setTitle("Are you sure you want to delete note '" + selectedNote.getPitch() + "'?");

        lengthInputDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int buttonId) {
                // Tell the listener...
                if (addNoteFragmentListener != null) {
                    addNoteFragmentListener.addNoteFragmentDeletedNote(AddNoteFragment.this, selectedNote);
                }

                refreshNotesList();

            }
        });

        lengthInputDialog.setNegativeButton("Cancel", null);

        lengthInputDialog.show();


        return true;
    }

    @Override
    public void intonationSelectedFromFragment(SelectIntonationFragment fragment, Intonation newIntonation) {
        Log.d("intonation selected", "intonation selected: " + newIntonation);
        currentIntonation = newIntonation;
        refreshIntonation();

        if (addNoteFragmentListener != null) {
            addNoteFragmentListener.addNoteFragmentIntonationSelected(this, currentIntonation);
        }

    }



    public AddNoteFragmentListener getAddNoteFragmentListener() {
        return addNoteFragmentListener;
    }

    public void setAddNoteFragmentListener(AddNoteFragmentListener addNoteFragmentListener) {
        this.addNoteFragmentListener = addNoteFragmentListener;
    }

    public Intonation getCurrentIntonation() {
        return currentIntonation;
    }

    public void setCurrentIntonation(Intonation currentIntonation) {
        this.currentIntonation = currentIntonation;
    }

    public List<Note> getNotesForCurrentBar() {
        return notesForCurrentBar;
    }

    public void setNotesForCurrentBar(List<Note> notesForCurrentBar) {
        this.notesForCurrentBar = notesForCurrentBar;
    }

}
