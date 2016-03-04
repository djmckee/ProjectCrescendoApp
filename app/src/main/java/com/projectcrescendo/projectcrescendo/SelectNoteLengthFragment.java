package com.projectcrescendo.projectcrescendo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.projectcrescendo.projectcrescendo.models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface to handle callbacks from the SelectNoteLengthFragment.
 *
 * Created by Dylan McKee on 27/02/2016
 */
interface SelectNoteLengthFragmentCallbackListener {
    /**
     * This method is called when the user has selected a length value from the length
     * selection fragment list, and the fragment has dismissed itself from the screen.
     * @param fragment the note length selection fragment.
     * @param length the length for the note, in terms of beats.
     */
    void noteLengthSelectedFromFragment(SelectNoteLengthFragment fragment, double length);

}

/**
 * A fragment that contains a list of possible note lengths so that the user can select an note length
 * for the new note begin added in hte AddNoteFragment.
 *
 * Once selected, the Intonation is relayed via a callback to the fragment that
 * presented it via the SelectNoteLengthFragmentCallbackListener.
 *
 * I looked at the tutorial at http://www.tutorialsbuzz.com/2014/06/android-dialogfragment-listview.html
 * and used their examples in the creation of this fragment.
 *
 * Created by Dylan McKee on 27/02/2016
 *
 */
public class SelectNoteLengthFragment extends DialogFragment implements
        OnItemClickListener {

    /**
     * A list view containing the possible lengths for the note that the user can select from.
     */
    private ListView lengthListView;

    /**
     * A callback listener that implements the SelectNoteLengthFragmentCallbackListener, so that
     * the fragment/activity presenting this fragment can receive a callback and know when the
     * length has been selected.
     */
    private SelectNoteLengthFragmentCallbackListener listener;

    /**
     * On load, this method inflates the fragment from XML.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_note_length, null, false);
        lengthListView = (ListView) view.findViewById(R.id.noteLengthSelectionList);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    /**
     * After the fragments XML has been loaded, this method populates the list view with the possible
     * lengths for the note that the user can select from.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


        List<String> lengthStrings = new ArrayList<String>();

        double[] possibleLengths = Note.VALID_NOTE_LENGTHS;

        for (double length : possibleLengths) {
            String lengthString = String.format("%.1f", length);
            lengthStrings.add(lengthString);
        }

        // Convert from list to array as per solution from https://stackoverflow.com/questions/9572795/convert-list-to-array-in-java
        String[] lengthStringsArray = new String[lengthStrings.size()];
        lengthStrings.toArray(lengthStringsArray);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, lengthStringsArray);


        lengthListView.setAdapter(adapter);

        lengthListView.setOnItemClickListener(this);

    }

    /**
     * When an length in the list of lengths is selected by the user, this method calls the listener
     * to let it know of the new selection, and then once this callback has been carried out it
     * dismisses the lenght selection fragment from the screen.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        // Tell the AddNoteFragment about it!
        double[] possibleLengths = Note.VALID_NOTE_LENGTHS;
        double length = possibleLengths[position];

        if (listener != null) {
            listener.noteLengthSelectedFromFragment(this, length);
        }

        // Looked up the following line at https://stackoverflow.com/questions/5901298/how-to-get-a-fragment-to-remove-itself-i-e-its-equivalent-of-finish
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();

    }

    /**
     * Returns the callback listener.
     * @return the callback listener.
     */
    public SelectNoteLengthFragmentCallbackListener getListener() {
        return listener;
    }

    /**
     * Sets the callback listener
     * @param listener the callback listener that is presenting this fragment
     */
    public void setListener(SelectNoteLengthFragmentCallbackListener listener) {
        this.listener = listener;
    }


}
