package com.projectcrescendo.projectcrescendo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;


/**
 * The main activity provides a main menu interface, it is the first screen that users see as they
 * enter the app and it allows them to select from the various options/modes and begin using the
 * app.
 * <p>
 * Created by Dylan McKee on 16/11/2015.
 */
public class MainActivity extends ActionBarActivity {

    /**
     * A static block to load the SeeScoreLib.so library.
     * Taken from the Dolphin Computing (Cambridge) Ltd. SeeScore Example Project.
     */
    static {
        System.loadLibrary("stlport_shared");
        System.loadLibrary("SeeScoreLib");
    }

    /**
     * The tutorial manager instance.
     */
    private TutorialManager tutorialManager;

    /**
     * This method is ran on creation of the view. UI and listeners to be set-up here.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        // TODO: Uncomment once tutorial manager is working.
        tutorialManager = new TutorialManager(this);

        Log.d("Debug", "tutorials: " + tutorialManager.getTutorialsList());
        */

        NoteManager noteManager = new NoteManager(this);
        Log.d("Debug", "note names: " + noteManager.getNoteNames());


        FloatingActionButton startTutorials = (FloatingActionButton) findViewById(R.id.startTutorialsButton);
        startTutorials.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, TutorialActivity.class));
                Log.d("MainActivity", "starting activity...");
                startActivity(new Intent(MainActivity.this, TutorialActivity.class));
            }
        });

        // View animation code from https://stackoverflow.com/questions/16800716/android-fade-view-in-and-out
        TextView txt = (TextView) findViewById(R.id.tab_to_start);
        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(2000);
        anim.setRepeatCount(999999999);
        anim.setRepeatMode(Animation.REVERSE);
        txt.startAnimation(anim);

    }

}
