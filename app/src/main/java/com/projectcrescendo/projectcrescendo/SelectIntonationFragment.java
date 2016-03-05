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

import com.projectcrescendo.projectcrescendo.models.Intonation;

import java.util.List;

/**
 * An interface to handle callbacks from the SelectIntonationFragment.
 * <p>
 * Created by Dylan McKee on 23/02/2016
 */
interface SelectIntonationFragmentCallbackListener {
    /**
     * This method is called when the user has selected an Intonation value from the intonation
     * selection fragment list, and the list has dismissed itself from the screen.
     *
     * @param fragment      the intonation selection fragment.
     * @param newIntonation the Intonation that has been selected in the fragment.
     */
    void intonationSelectedFromFragment(SelectIntonationFragment fragment, Intonation newIntonation);

}

/**
 * A fragment that contains a list of possible intonations so that the user can select an intonation
 * for the current Beat. Once selected, the Intonation is relayed via a callback to the fragment that
 * presented it via the SelectIntonationFragmentCallbackListener.
 * <p>
 * I looked at the tutorial at http://www.tutorialsbuzz.com/2014/06/android-dialogfragment-listview.html
 * and used their examples in the creation of this fragment.
 * <p>
 * Created by Dylan McKee on 23/02/2016
 */
public class SelectIntonationFragment extends DialogFragment implements
        OnItemClickListener {

    /**
     * A list view containing the possible intonations for the beat that the user can select from.
     */
    private ListView intonationSelectionList;

    /**
     * A callback listener that implements the SelectIntonationFragmentCallbackListener, so that
     * the fragment/activity presenting this fragment can receive a callback and know when the
     * intonation for the beat has been selected.
     */
    private SelectIntonationFragmentCallbackListener listener;

    /**
     * On load, this method inflates the fragment from XML.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_intonation, null, false);
        intonationSelectionList = (ListView) view.findViewById(R.id.intonationSelectionList);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    /**
     * After the fragments XML has been loaded, this method populates the list view with the possible
     * intonation choices for the current beat that the user can select from.
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


        List<String> intonationNames = new IntonationManager(getActivity()).getIntonationNames();

        // Convert from list to array as per solution from https://stackoverflow.com/questions/9572795/convert-list-to-array-in-java
        String[] namesArray = new String[intonationNames.size()];
        intonationNames.toArray(namesArray);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, namesArray);


        intonationSelectionList.setAdapter(adapter);

        intonationSelectionList.setOnItemClickListener(this);

    }

    /**
     * When an intonation in the list of intonations is selected by the user, this method calls the listener
     * to let it know of the new selection, and then once this callback has been carried out it
     * dismisses the intonation selection fragment from the screen.
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        // Tell the AddNoteFragment about it!
        Intonation newIntonation = Intonation.getIntonationWithID(position);

        if (listener != null) {
            listener.intonationSelectedFromFragment(this, newIntonation);
        }

        // Looked up the following line at https://stackoverflow.com/questions/5901298/how-to-get-a-fragment-to-remove-itself-i-e-its-equivalent-of-finish
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();

    }

    /**
     * Returns the callback listener.
     *
     * @return the callback listener.
     */
    public SelectIntonationFragmentCallbackListener getListener() {
        return listener;
    }

    /**
     * Sets the callback listener
     *
     * @param listener the callback listener that is presenting this fragment
     */
    public void setListener(SelectIntonationFragmentCallbackListener listener) {
        this.listener = listener;
    }


}
