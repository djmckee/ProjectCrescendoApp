package com.projectcrescendo.projectcrescendo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * This class uses the SeeScore Library, provided under an educational non-profit only licence and
 * originally authored by Dolphin Computing (Cambridge) Ltd. - downloaded from
 * http://www.seescore.co.uk/developers/ on 19th February 2016.
 */
import uk.co.dolphin_com.seescoreandroid.SeeScoreView;
import uk.co.dolphin_com.sscore.LoadOptions;
import uk.co.dolphin_com.sscore.SScore;
import uk.co.dolphin_com.sscore.SScoreKey;
import uk.co.dolphin_com.sscore.ex.ScoreException;

/**
 * An activity that uses the SeeScore SDK (downloaded from http://www.seescore.co.uk/developers/ on
 * 19th February 2016, originally authored by Dolphin Computing (Cambridge) Ltd. and used under an
 * education license) to display the MusicXML string passed to this activity, and to allow for
 * audio playback and sharing of the MusicXML file through our hosted API hosting the file, and the
 * link to the hosted file being shared on Twitter/Facebook/other social channels by the user.
 *
 * Created by Dylan McKee on 22/02/2016.
 */
public class PlaybackActivity extends ActionBarActivity {

    /**
     * A key for the String containing the MusicXML encoded score as it is passed between activities.
     */
    public static final String SCORE_STRING_KEY = "projectcrescendo.MusicXMLScoreString";

    /**
     * The SeeScore view instance which displays the score.
     */
    private SeeScoreView seeScoreView;

    /**
     * A String representation of the MusicXML encoded score, to be set via the Intent that
     * transitions into this activity.
     */
    private String musicXmlScore;

    /**
     * An SScore instance of the current musical score on display.
     */
    private SScore score;

    /**
     * A scroll view instance to contain our SeeScore score view and make it scrollable
     * for longer scores/smaller screens.
     */
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Get the MusicXML string that we've been passed in the transition to this activity...
        Intent intent = getIntent();
        musicXmlScore = intent.getExtras().getString(SCORE_STRING_KEY);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        // Create our score view
        seeScoreView = new SeeScoreView(this, this.getAssets(), null, null);

        // Convert the string to a byte array for parsing by the SeeScore library
        byte[] musicXmlBytes = musicXmlScore.getBytes();
        SScoreKey key = SeeScoreLicence.SeeScoreLibKey;
        LoadOptions options = new LoadOptions(key, true);


        // And attempt a parse...
        try {
            score = SScore.loadXMLData(musicXmlBytes, options);

        } catch (ScoreException sE) {
            // Do not continue loading.
            // TODO: Show error
            Log.d("Playback error", sE.toString());

            return;
        }

        seeScoreView.setScore(score, 1.0f);


        ScrollView sv = (ScrollView) findViewById(R.id.scrollView1);
        sv.addView(seeScoreView);

        sv.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                return seeScoreView.onTouchEvent(event);
            }
        });

        FloatingActionButton playPauseButton = (FloatingActionButton) findViewById(R.id.playPausePlaybackButton);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Toggle playback
            }
        });

        FloatingActionButton stopPlaybackButton = (FloatingActionButton) findViewById(R.id.stopPlaybackButton);
        stopPlaybackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Stop playback
            }
        });

        FloatingActionButton sharePlaybackButton = (FloatingActionButton) findViewById(R.id.sharePlaybackButton);
        sharePlaybackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Share playback
            }
        });

        FloatingActionButton donePlaybackButton = (FloatingActionButton) findViewById(R.id.donePlaybackButton);
        sharePlaybackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss current activity and go back to main menu
                // (I looked up FLAG_ACTIVITY_CLEAR_TOP at https://stackoverflow.com/questions/11460896/button-to-go-back-to-mainactivity)
                Intent intent = new Intent(PlaybackActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }

}
