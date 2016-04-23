package com.projectcrescendo.projectcrescendo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;


/**
 * The main activity provides a main menu interface, it is the first screen that users see as they
 * enter the app and it allows them to select from the various options/modes and begin using the
 * app.
 * <p/>
 * Created by Dylan McKee on 16/11/2015.
 * Modified by Ambrose and Alex.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * A constant defining the duration of the fade in/out animation of the 'start' text view.
     */
    private static final int FADE_ANIMATION_DURATION = 2000;

    /**
     * A static block to load the SeeScoreLib.so library.
     * Taken from the Dolphin Computing (Cambridge) Ltd. SeeScore Example Project.
     */
    static {
        System.loadLibrary("stlport_shared");
        System.loadLibrary("SeeScoreLib");
    }

    /**
     * This method is ran on creation of the view. UI and listeners to be set-up here.
     *
     * @param savedInstanceState the state of the activity being created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton startTutorials = (FloatingActionButton) findViewById(R.id.startTutorialsButton);
        startTutorials.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("MainActivity", "starting activity...");
                startActivity(new Intent(MainActivity.this, TutorialActivity.class));
            }
        });

        // View animation code from https://stackoverflow.com/questions/16800716/android-fade-view-in-and-out
        TextView startInformationTextView = (TextView) findViewById(R.id.tab_to_start);
        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(FADE_ANIMATION_DURATION);
        anim.setRepeatCount(Integer.MAX_VALUE);
        anim.setRepeatMode(Animation.REVERSE);
        startInformationTextView.startAnimation(anim);

    }

}
