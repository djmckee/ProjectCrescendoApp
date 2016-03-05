package com.projectcrescendo.projectcrescendo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * A menu to select the tutorial from - tutorial names are to be shown in a list, and a
 * TutorialActivity taking you through the selected tutorial is pushed when you tap on a selection.
 * <p>
 * Created by Jordan Dixon on 01/03/2016.
 */
public class TutorialSelectionMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
