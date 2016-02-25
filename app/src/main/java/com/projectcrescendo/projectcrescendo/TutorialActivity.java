package com.projectcrescendo.projectcrescendo;

import android.content.Context;
import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.projectcrescendo.projectcrescendo.models.Note;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


// got code from http://prasans.info/2011/03/add-edittexts-dynamically-and-retrieve-values-android/
public class TutorialActivity extends ActionBarActivity implements CustomAdapterListener, AddNoteFragmentListener  {

    GridView gridView;
    Context context;
    ArrayList prgmName;
    public static String [] prgmNameList={"c","a","b","f","e","b","c","a"};

    Spinner time_signature_r1, time_signature_r2, time_signature_l1, time_signature_l2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        gridView = (GridView) findViewById(R.id.gridView1);
        CustomAdapter adapter = new CustomAdapter(this, prgmNameList);
        adapter.setAdapterListener(this);

        gridView.setAdapter(adapter);
        addItemsOnSpinner();

    }

    public void onItemTapListener(int position) {
        Log.d("TutorialActivity", "onItemClicked");

        // Present the add note fragment...
        AddNoteFragment addNoteFragment = new AddNoteFragment();

        addNoteFragment.setAddNoteFragmentListener(this);

        addNoteFragment.show(getSupportFragmentManager(), "Add Note");

    }

    public void addItemsOnSpinner() {

        time_signature_r1 = (Spinner) findViewById(R.id.right_hand_time_signature_1);
        time_signature_r2 = (Spinner) findViewById(R.id.right_hand_time_signature_2);
        time_signature_l1 = (Spinner) findViewById(R.id.left_hand_time_signature_1);
        time_signature_l2 = (Spinner) findViewById(R.id.left_hand_time_signature_2);
        List<String> list = new ArrayList<String>();
        list.add("2");
        list.add("3");
        list.add("4");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_signature_r1.setAdapter(dataAdapter);
        time_signature_r2.setAdapter(dataAdapter);
        time_signature_l1.setAdapter(dataAdapter);
        time_signature_l2.setAdapter(dataAdapter);
    }


    @Override
    public void addNoteFragmentAddedNote(AddNoteFragment addNoteFragment, Note note) {
        // TODO: add the note to the composition

    }

    @Override
    public void addNoteFragmentDeletedNote(AddNoteFragment addNoteFragment, Note note) {
        // TODO: delete the note from the composition

    }

}
