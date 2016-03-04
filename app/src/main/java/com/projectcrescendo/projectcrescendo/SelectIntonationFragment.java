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
 *
 * Created by Dylan McKee on 23/02/2016
 */
interface SelectIntonationFragmentCallbackListener {
    /**
     * This method is called when the user has selected an Intonation value from the intonation
     * selection fragment list, and the list has dismissed itself from the screen.
     * @param fragment the intonation selection fragment.
     * @param newIntonation the Intonation that has been selected in the fragment.
     */
    void intonationSelectedFromFragment(SelectIntonationFragment fragment, Intonation newIntonation);

}

/**
 * A fragment that contains a list of possible intonations so that the user can select an intonation
 * for the current Beat. Once selected, the Intonation is relayed via a callback to the fragment that
 * presented it via the SelectIntonationFragmentCallbackListener.
 *
 * I looked at the tutorial at http://www.tutorialsbuzz.com/2014/06/android-dialogfragment-listview.html
 * and used their examples in the creation of this fragment.
 *
 * Created by Dylan McKee on 23/02/2016
 */
public class SelectIntonationFragment extends DialogFragment implements
        OnItemClickListener {


    private ListView intonationSelectionList;

    private SelectIntonationFragmentCallbackListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_intonation, null, false);
        intonationSelectionList = (ListView) view.findViewById(R.id.intonationSelectionList);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

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

    public SelectIntonationFragmentCallbackListener getListener() {
        return listener;
    }

    public void setListener(SelectIntonationFragmentCallbackListener listener) {
        this.listener = listener;
    }


}
