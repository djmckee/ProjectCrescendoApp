package com.projectcrescendo.projectcrescendo;
import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.projectcrescendo.projectcrescendo.models.Intonation;

import java.util.List;

interface SelectIntonationFragmentCallbackListener {
    void intonationSelectedFromFragment(SelectIntonationFragment fragment, Intonation newIntonation);

}

/**
 * Created by Dylan McKee on 23/02/2016
 *
 * I looked at the tutorial at http://www.tutorialsbuzz.com/2014/06/android-dialogfragment-listview.html
 * and used their examples in the creation of this fragment.
 *
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

        // TODO: fix go back
        //getFragmentManager().popBackStack();

    }

    public SelectIntonationFragmentCallbackListener getListener() {
        return listener;
    }

    public void setListener(SelectIntonationFragmentCallbackListener listener) {
        this.listener = listener;
    }



}
