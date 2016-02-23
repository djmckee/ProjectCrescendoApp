package com.projectcrescendo.projectcrescendo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;

import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


/**
 * Created by Jordan on 06/02/2016.
 class to create grid holders for boxes.
 **/
public class GridActivity extends ActionBarActivity {

    private GridviewAdapter mAdapter;
    private GridView gridView;
    private ArrayList<String> listNotes;
    private ArrayList<Button> listvol;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_music);

        noteList();

        // prepared arraylist and passed it to the Adapter class
        mAdapter = new GridviewAdapter(this,listNotes, listvol);

        // Set custom adapter to gridview
        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(mAdapter);

        // Implement On Item click listener


    }

    public void noteList()
    {
        listNotes = new ArrayList<String>();

        listNotes.add("No Notes");
        listNotes.add("No Notes");
        listNotes.add("No Notes");
        listNotes.add("No Notes");
        listNotes.add("No Notes");
        listNotes.add("No Notes");
        listNotes.add("No Notes");
        listNotes.add("No Notes");
        listNotes.add("No Notes");
        listNotes.add("No Notes"); //notes to edit text

        listvol = new ArrayList<Button>();
        listvol.add((Button)findViewById(R.id.button1));
        listvol.add((Button)findViewById(R.id.button1));
        listvol.add((Button)findViewById(R.id.button1));
        listvol.add((Button)findViewById(R.id.button1));
        listvol.add((Button)findViewById(R.id.button1));
        listvol.add((Button)findViewById(R.id.button1));
        listvol.add((Button)findViewById(R.id.button1));
        listvol.add((Button)findViewById(R.id.button1));
        listvol.add((Button)findViewById(R.id.button1));
        listvol.add((Button)findViewById(R.id.button1));





    }
}
