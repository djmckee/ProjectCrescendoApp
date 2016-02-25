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


// got code from http://prasans.info/2011/03/add-edittexts-dynamically-and-retrieve-values-android/
public class TutorialActivity extends ActionBarActivity implements CustomAdapterListener, AddNoteFragmentListener  {

    GridView gridView;
    Context context;
    ArrayList prgmName;
    public static String [] prgmNameList={"c","a","b","f","e","b","c","a"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        gridView = (GridView) findViewById(R.id.gridView1);
        CustomAdapter adapter = new CustomAdapter(this, prgmNameList);
        adapter.setAdapterListener(this);

        gridView.setAdapter(adapter);

    }

    public void onItemTapListener(int position) {
        Log.d("TutorialActivity", "onItemClicked");

        // Present the add note fragment...
        AddNoteFragment addNoteFragment = new AddNoteFragment();

        addNoteFragment.setAddNoteFragmentListener(this);

        addNoteFragment.show(getSupportFragmentManager(), "Add Note");

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
