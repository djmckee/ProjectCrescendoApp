package com.projectcrescendo.projectcrescendo;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.content.Intent;



public class MainActivity extends ActionBarActivity  {

    /**
     * The tutorial manager instance.
     */
    private TutorialManager tutorialManager;

    Button startTutorials;

    /**
     * This method is ran on creation of the view. UI and listeners to be set-up here.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tutorialManager = new TutorialManager(this);

        Log.d("Debug", "tutorials: " + tutorialManager.getTutorialsList());

        // hello world.

        startTutorials = (Button) findViewById(R.id.startTutorialsButton);
        startTutorials.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, TutorialActivity.class));
            }
        });

    }


}
