package com.projectcrescendo.projectcrescendo;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.projectcrescendo.projectcrescendo.models.Stave;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface to handle callbacks from the OpenCompositionFragment.
 * <p>
 * Created by Dylan McKee on 23/03/2016
 */
interface OpenCompositionFragmentCallbackListener {
    /**
     * This method is called when the user has selected a composition, in the
     * form of a Stave instance from the selection fragment list,
     * and the list has dismissed itself from the screen.
     *
     * @param fragment       the composition selection fragment instance.
     * @param newComposition the Stave that has been selected in the fragment.
     */
    void compositionSelectedFromFragment(OpenCompositionFragment fragment, Stave newComposition);

    /**
     * This method is called when the user has selected a composition to be deleted in the fragment
     * by long-pressing on it, the fragment has confirmed the deletion intent, and the fragment
     * has dismissed itself from the screen.
     *
     * @param fragment            the composition selection fragment instance.
     * @param compositionToDelete the Stave instance that has been marked for deletion.
     */
    void compositionMarkedForDeletionFromFragment(OpenCompositionFragment fragment, Stave compositionToDelete);

}

/**
 * A fragment that contains a list of possible compositions to open so that the user can select a composition
 * from the database. Once selected, the composition, in the form of a Stave instance, is relayed via a callback to the fragment that
 * presented it via the OpenCompositionFragmentCallbackListener.
 * <p>
 * I looked at the tutorial at http://www.tutorialsbuzz.com/2014/06/android-dialogfragment-listview.html
 * and used their examples in the creation of this fragment.
 * <p>
 * Created by Dylan McKee on 23/03/2016
 */
public class OpenCompositionFragment extends DialogFragment implements
        OnItemClickListener, OnItemLongClickListener {

    /**
     * A list view containing the possible compositions (Stave instances) for the beat that the user can select from.
     */
    private ListView compositionSelectionList;

    /**
     * A list of Stave instances (i.e. the compositions)
     */
    private List<Stave> compositions;

    /**
     * A callback listener that implements the OpenCompositionFragmentCallbackListener, so that
     * the fragment/activity presenting this fragment can receive a callback and know when the
     * composition (in the form of a Stave instance) has been selected.
     */
    private OpenCompositionFragmentCallbackListener listener;

    /**
     * On load, this method inflates the fragment from XML.
     *
     * @param inflater           the layout inflater that inflates this fragment.
     * @param container          the view that contains this fragment.
     * @param savedInstanceState a saved state of an existing instance of this fragment.
     * @return the view containing this fragment, inflated from XML.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_open_composition, null, false);
        compositionSelectionList = (ListView) view.findViewById(R.id.savedCompositionSelectionList);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    /**
     * After the fragments XML has been loaded, this method populates the list view with the possible
     * composition choices from the database.
     *
     * @param savedInstanceState saved state data about this fragment, if any exists.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


        List<String> compositionNames = new ArrayList<String>();

        // Loop through the Stave instances, adding their names to the list (if present...)
        for (Stave composition : compositions) {
            String name = composition.getName();

            if (name == null) {
                // Untitled, use fallback to prevent obvious crash.
                name = "Untitled Composition";
            }

            compositionNames.add(name);

        }


        // Convert from list to array as per solution from https://stackoverflow.com/questions/9572795/convert-list-to-array-in-java
        String[] namesArray = new String[compositionNames.size()];
        compositionNames.toArray(namesArray);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, namesArray);


        compositionSelectionList.setAdapter(adapter);

        compositionSelectionList.setOnItemClickListener(this);
        compositionSelectionList.setOnItemLongClickListener(this);

    }

    /**
     * When an intonation in the list of intonations is selected by the user, this method calls the listener
     * to let it know of the new selection, and then once this callback has been carried out it
     * dismisses the intonation selection fragment from the screen.
     *
     * @param parent   the ListView containing the intonations.
     * @param view     the cell containing the selected intonation.
     * @param position the position of the cell/intonation that has been selected.
     * @param id       the id of the cell containing the selected intonation
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        // Tell the TutorialActivity about it!
        Stave selectedComposition = compositions.get(position);

        if (listener != null) {
            listener.compositionSelectedFromFragment(this, selectedComposition);
        }

        // Looked up the following line at https://stackoverflow.com/questions/5901298/how-to-get-a-fragment-to-remove-itself-i-e-its-equivalent-of-finish
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();

    }

    /**
     * This method is called when an item in the compositions list is long pressed. If it's a valid composition,
     * the user is then asked if they'd like to delete it from the database.
     *
     * @param parent   the ListView containing the compositions.
     * @param view     the cell containing the selected composition.
     * @param position the position of the selected composition in the list.
     * @param id       the ID of the cell containing the selected composition in the list.
     * @return a boolean indicating whether the currently selected composition can be long pressed
     * and marked for deletion, or not.
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final Stave selectedComposition = compositions.get(position);

        AlertDialog.Builder deleteConfirmationDialog = new AlertDialog.Builder(getActivity());
        deleteConfirmationDialog.setTitle(getString(R.string.delete_composition_title) + selectedComposition.getName() + getString(R.string.question_mark));

        deleteConfirmationDialog.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int buttonId) {
                // Tell the listener and close this fragment...

                // Close the fragment...
                // Looked up the following line at https://stackoverflow.com/questions/5901298/how-to-get-a-fragment-to-remove-itself-i-e-its-equivalent-of-finish
                getActivity().getFragmentManager().beginTransaction().remove(OpenCompositionFragment.this).commit();


                if (listener != null) {
                    // Tell the listener about the deletion...
                    listener.compositionMarkedForDeletionFromFragment(OpenCompositionFragment.this, selectedComposition);
                }

            }
        });

        deleteConfirmationDialog.setNegativeButton(R.string.cancel, null);

        deleteConfirmationDialog.show();

        return true;
    }


    /**
     * Returns the callback listener.
     *
     * @return the callback listener.
     */
    public OpenCompositionFragmentCallbackListener getListener() {
        return listener;
    }

    /**
     * Sets the callback listener
     *
     * @param listener the callback listener that is presenting this fragment
     */
    public void setListener(OpenCompositionFragmentCallbackListener listener) {
        this.listener = listener;
    }

    /**
     * Returns the array of saved compositions (i.e. Stave instances).
     *
     * @return a List of Stave instances displayed in this fragment for the user to select from.
     */
    public List<Stave> getCompositions() {

        return compositions;
    }

    /**
     * Sets the list of compositions to open (i.e. Stave instances from the database) to whatever
     * has been passed into this method.
     *
     * @param compositions a List of Stave instances that the user can select from to open.
     */
    public void setCompositions(List<Stave> compositions) {
        this.compositions = compositions;
    }


}
