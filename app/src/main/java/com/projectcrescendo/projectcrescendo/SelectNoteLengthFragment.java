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

interface SelectNoteLengthFragmentCallbackListener {
    void noteLengthSelectedFromFragment(SelectNoteLengthFragment fragment, double length);

}

/**
 * Created by Dylan McKee on 27/02/2016
 * <p>
 * Based off of the SelectIntonationFragment, using tutorial example http://www.tutorialsbuzz.com/2014/06/android-dialogfragment-listview.html.
 */
public class SelectNoteLengthFragment extends DialogFragment implements
        OnItemClickListener {


    private ListView lengthListView;

    private SelectNoteLengthFragmentCallbackListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_note_length, null, false);
        lengthListView = (ListView) view.findViewById(R.id.noteLengthSelectionList);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

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

    public SelectNoteLengthFragmentCallbackListener getListener() {
        return listener;
    }

    public void setListener(SelectNoteLengthFragmentCallbackListener listener) {
        this.listener = listener;
    }


}
