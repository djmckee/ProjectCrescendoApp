package com.projectcrescendo.projectcrescendo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * An activity to show some instructions on how to use the app, displayed in a full size
 * scrollable image view.
 * <p/>
 * Created by Ambrose Suen on 26/3/2016.
 * Documented by Dylan.
 */
public class InstructionsActivity extends AppCompatActivity {

    /**
     * Creates the activity from the XML layout file.
     *
     * @param savedInstanceState a saved instance state, if one exists (not used).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }


}
