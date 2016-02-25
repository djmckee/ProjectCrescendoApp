package com.projectcrescendo.projectcrescendo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.projectcrescendo.projectcrescendo.models.Intonation;
import com.projectcrescendo.projectcrescendo.models.Note;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class TutorialActivity extends ActionBarActivity implements CustomAdapterListener, AddNoteFragmentListener  {

    GridView gridView;
    Context context;
    ArrayList prgmName;
    public static String [] prgmNameList={"c","a","b","f","e","b","c","a"};

    Spinner timeSignatureR1;
    Spinner timeSignatureR2;
    Spinner timeSignatureL1;
    Spinner timeSignatureL2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        gridView = (GridView) findViewById(R.id.gridView1);
        CustomAdapter adapter = new CustomAdapter(this, prgmNameList);
        adapter.setAdapterListener(this);

        gridView.setAdapter(adapter);
        addItemsOnSpinner();

        //Browse instruction page once instruction buttons is pressed.
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.instruction);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TutorialActivity", "starting activity...");
                startActivity(new Intent(TutorialActivity.this, Instructions.class));
            }
        });

    }

    public void onItemTapListener(int position) {
        Log.d("TutorialActivity", "onItemClicked");

        // Present the add note fragment...
        AddNoteFragment addNoteFragment = new AddNoteFragment();

        addNoteFragment.setAddNoteFragmentListener(this);

        addNoteFragment.show(getSupportFragmentManager(), "Add Note");

    }

    public void addItemsOnSpinner() {

        // Ambrose: got code from http://prasans.info/2011/03/add-edittexts-dynamically-and-retrieve-values-android/ for the Spinner UI
        timeSignatureR1 = (Spinner) findViewById(R.id.right_hand_time_signature_1);
        timeSignatureR2 = (Spinner) findViewById(R.id.right_hand_time_signature_2);
        timeSignatureL1 = (Spinner) findViewById(R.id.left_hand_time_signature_1);
        timeSignatureL2 = (Spinner) findViewById(R.id.left_hand_time_signature_2);

        List<String> list = new ArrayList<String>();
        list.add("2");
        list.add("3");
        list.add("4");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        timeSignatureR1.setAdapter(dataAdapter);
        timeSignatureR2.setAdapter(dataAdapter);
        timeSignatureL1.setAdapter(dataAdapter);
        timeSignatureL2.setAdapter(dataAdapter);

    }


    @Override
    public void addNoteFragmentAddedNote(AddNoteFragment addNoteFragment, Note note) {
        // TODO: add the note to the composition

    }

    @Override
    public void addNoteFragmentDeletedNote(AddNoteFragment addNoteFragment, Note note) {
        // TODO: delete the note from the composition

    }

    @Override
    public void addNoteFragmentIntonationSelected(AddNoteFragment addNoteFragment, Intonation newIntonation) {
        // TODO: change intonation for the current beat

    }

}
