package com.projectcrescendo.projectcrescendo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.projectcrescendo.projectcrescendo.models.ConcreteNote;
import com.projectcrescendo.projectcrescendo.models.Intonation;
import com.projectcrescendo.projectcrescendo.models.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface AddNoteFragmentListener {
    void addNoteFragmentAddedNote(AddNoteFragment addNoteFragment, Note note);

}

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNoteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNoteFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    private Intonation currentIntonation;
    private List<Note> notesForCurrentBar;

    private OnFragmentInteractionListener mListener;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) this.getView().findViewById(R.id.noteListView);

        listView.setOnItemClickListener(this);

        intonationButton = (Button) this.getView().findViewById(R.id.intonationSelectionButton);
        intonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIntonation();
            }
        });


        /*
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Present the add note fragment...
                AddNoteFragment addNoteFragment = new AddNoteFragment();

                addNoteFragment.addNoteFragmentListener = this;

                addNoteFragment.show(getSupportFragmentManager(), "Add Note");

            }
        });
         */

    }


    void setIntonation() {
        // Present a modal that allows the user to select intonation...

        SelectIntonationFragment selectIntonationFragment = new SelectIntonationFragment();

        // TODO: Fix!
        //selectIntonationFragment.show(getActivity().getSupportFragmentManager(), "Select Intonation");

    }

    void refreshNotesList() {
        // Refresh the Android ListView...

        List<String> noteTitleList = new ArrayList<String>();

        // Add note names to the list...
        for (Note note : notesForCurrentBar) {
            String title = String.format("%s - %f s.", note.getPitch(), note.getLength());

            noteTitleList.add(title);

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
        final EditText inputTextView = new EditText(getActivity());
        inputDialog.setView(inputTextView);

        inputDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int buttonId) {
                // Get user input string
                String textInput = inputTextView.getText().toString();

                // Perform some input validation on the entered note...
                boolean isValid = false;

                for (String noteName : validNoteNames) {
                    if (noteName.equals(textInput)) {
                        // Input is valid!
                        isValid = true;
                    }
                }

                // If the note's valid, instantiate the new note and ask for a length - otherwise, error
                if (isValid) {
                    noteToAdd = new ConcreteNote(textInput);

                    // Now, ask for note length...
                    // TODO: Get time signature
                    AlertDialog.Builder lengthInputDialog = new AlertDialog.Builder(getActivity());
                    lengthInputDialog.setTitle("Enter length");
                    final int maxTime = 4;
                    String message = "Enter the note length (between 0.5 to " + maxTime + ")";
                    lengthInputDialog.setMessage(message);

                    final EditText lengthInputTextView = new EditText(getActivity());
                    lengthInputDialog.setView(lengthInputTextView);

                    lengthInputDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int buttonId) {
                            // Validate the length input...
                            String lengthInputValue = lengthInputTextView.getText().toString();

                            double length = 0.5;

                            // Attempt to parse to a double...
                            try {
                                // Valid; continue.
                                length = Double.parseDouble(lengthInputValue);
                            } catch(NumberFormatException nfe) {
                               // Invalid; show error and give up...
                                new AlertDialog.Builder(getActivity())
                                        .setTitle("Invalid note length!")
                                        .setMessage("Not a valid note name - please try again...")
                                        .setPositiveButton("Okay", null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();

                                // Reset placeholder
                                noteToAdd = null;
                            }

                            if (noteToAdd != null) {
                                // there's a note to add; add it and refresh...
                                notesForCurrentBar.add(noteToAdd);

                                // Tell the listener...
                                if (addNoteFragmentListener != null) {
                                    addNoteFragmentListener.addNoteFragmentAddedNote(AddNoteFragment.this, noteToAdd);
                                }

                                refreshNotesList();

                            }

                        }
                    });

                    lengthInputDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int buttonId) {
                            // Reset note placeholder; the user's given up...
                            noteToAdd = null;
                        }
                    });




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
        //
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public AddNoteFragmentListener getAddNoteFragmentListener() {
        return addNoteFragmentListener;
    }

    public void setAddNoteFragmentListener(AddNoteFragmentListener addNoteFragmentListener) {
        this.addNoteFragmentListener = addNoteFragmentListener;
    }


}
