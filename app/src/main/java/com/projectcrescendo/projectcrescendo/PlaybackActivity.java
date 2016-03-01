package com.projectcrescendo.projectcrescendo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

    /**
     * A flag boolean variable to allow uploads of composition sharing to be cancelled.
     */
    private boolean shouldContinueSharing;

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
                // Share playback
                shareComposition();
            }
        });

        FloatingActionButton donePlaybackButton = (FloatingActionButton) findViewById(R.id.donePlaybackButton);
        donePlaybackButton.setOnClickListener(new View.OnClickListener() {
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

    void shareComposition() {
        // Upload to our web API and get a link to share...
        final ProgressDialog loadingDialog = ProgressDialog.show(PlaybackActivity.this, "Uploading Composition", "Uploading composition...");
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setCancelable(true);

        shouldContinueSharing = true;

        loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // Stop upload.
                shouldContinueSharing = false;
            }
        });

        // Upload compostion...
        CrescendoAPIManager.uploadComposition(musicXmlScore, new CrescendoAPIResponseHandler() {
            @Override
            public void uploadSucceeded(int uploadId, String uploadUrl) {
                loadingDialog.dismiss();

                // If we haven't been cancelled, share it...
                if (shouldContinueSharing) {
                    String textToShare = "I'm learning music with Sonata! Check out my latest Sonata composition at " + uploadUrl;

                    // Looked up the android 'share intent' at http://code.tutsplus.com/tutorials/android-sdk-implement-a-share-intent--mobile-8433
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");

                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My latest Sonata Composition");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);

                    startActivity(Intent.createChooser(shareIntent, "Share composition"));

                }

            }

            @Override
            public void uploadFailed() {
                loadingDialog.dismiss();

                // Show alert (if they haven't cancelled the upload)...
                if (shouldContinueSharing) {
                    // Display error...
                    new AlertDialog.Builder(PlaybackActivity.this)
                            .setTitle("Sharing failed")
                            .setMessage("Sorry, it looks like sharing of your compoisiton failed, please check your internet connection and try again...")
                            .setPositiveButton("Okay", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
        });

    }

}
