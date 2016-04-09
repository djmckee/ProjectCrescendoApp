package com.projectcrescendo.projectcrescendo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import uk.co.dolphin_com.seescoreandroid.Dispatcher;
import uk.co.dolphin_com.seescoreandroid.Player;
import uk.co.dolphin_com.seescoreandroid.SeeScoreView;
import uk.co.dolphin_com.sscore.Component;
import uk.co.dolphin_com.sscore.LoadOptions;
import uk.co.dolphin_com.sscore.LoadWarning;
import uk.co.dolphin_com.sscore.SScore;
import uk.co.dolphin_com.sscore.SScoreKey;
import uk.co.dolphin_com.sscore.ex.ScoreException;

/**
 * This class uses the SeeScore Library, provided under an educational non-profit only licence and
 * originally authored by Dolphin Computing (Cambridge) Ltd. - downloaded from
 * http://www.seescore.co.uk/developers/ on 19th February 2016.
 */


/**
 * An activity that uses the SeeScore SDK (downloaded from http://www.seescore.co.uk/developers/ on
 * 19th February 2016, originally authored by Dolphin Computing (Cambridge) Ltd. and used under an
 * education license) to display the MusicXML string passed to this activity, and to allow for
 * audio playback and sharing of the MusicXML file through our hosted API hosting the file, and the
 * link to the hosted file being shared on Twitter/Facebook/other social channels by the user.
 * <p>
 * Created by Dylan McKee on 22/02/2016.
 */
public class PlaybackActivity extends ActionBarActivity {

    /**
     * A key for the String containing the MusicXML encoded score as it is passed between activities.
     */
    public static final String SCORE_STRING_KEY = "projectcrescendo.MusicXMLScoreString";
    /**
     * A music player instance so that our composition can be played back.
     */
    Player player;
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
    /**
     * Is the music player playing?
     */
    private boolean playerIsPlaying = false;

    /**
     * A placeholder in which to store the player's current playback bar, initialised to 0.
     */
    private int currentBar = 0;

    /**
     * This method is ran on view creation and sets up the current score from the MusicXML
     * passed to this activity through the Intent pushing it, and instantiates the SeeScore
     * SDK for viewing of the musical score and for playback.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the MusicXML string that we've been passed in the transition to this activity...
        Intent intent = getIntent();
        musicXmlScore = intent.getExtras().getString(SCORE_STRING_KEY);

        // Assert that there's some MusicXML to display
        if (musicXmlScore == null) {
            // Nothing to display; throw exception
            throw new NullPointerException("MusicXML is null - ensure that the Intent pushing the PlaybackActivity is passing in MusicXML!");

        }


        // Create our score view
        seeScoreView = new SeeScoreView(this, getAssets(), new SeeScoreView.ZoomNotification() {
            @Override
            public void zoom(float scale) {

            }
        }, new SeeScoreView.TapNotification() {
            @Override
            public void tap(int systemIndex, int partIndex, int barIndex, Component[] components) {
                // This method implementation is based off of the SeeScore examples provide to us by
                //  Dolphin Computing (Cambridge) Ltd.

                currentBar = barIndex;

                if (player != null) {
                    boolean isPlaying = (player.state() == Player.State.Started);
                    if (isPlaying) {
                        player.pause();
                        seeScoreView.setCursorAtBar(barIndex, SeeScoreView.CursorType.line, 200);
                    }

                    if (isPlaying) {
                        player.startAt(barIndex, false);
                    }

                } else {
                    seeScoreView.setCursorAtBar(barIndex, SeeScoreView.CursorType.box, 200);
                }

            }
        });

        seeScoreView.setLayoutCompletionHandler(new Runnable() {
            public void run() {
                // Fired when the score has finished loading in the SeeScore viewer.
            }
        });

        setContentView(R.layout.activity_playback);

        scrollView = (ScrollView) findViewById(R.id.scrollView1);
        scrollView.addView(seeScoreView);

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                return seeScoreView.onTouchEvent(event);
            }
        });


        // Convert the string to a byte array for parsing by the SeeScore library
        byte[] musicXmlBytes = musicXmlScore.getBytes();
        SScoreKey key = SeeScoreLicence.SeeScoreLibKey;
        LoadOptions options = new LoadOptions(key, true);


        // And attempt a parse...
        try {
            score = SScore.loadXMLData(musicXmlBytes, options);

        } catch (ScoreException sE) {
            // Do not continue loading.
            // Show error report to user

            new AlertDialog.Builder(PlaybackActivity.this)
                    .setTitle("Playback error")
                    .setMessage("Sorry, it looks like your composition could not be played back. Please check it over and try again...")
                    .setPositiveButton("Okay", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            Log.d("Playback error", sE.toString());

            return;
        }

        if (score == null) {
            Log.d("Playback score", "failed");

        } else {
            Log.d("Playback score", "exists");

            String warnings = "";

            for (LoadWarning warning : score.getLoadWarnings()) {
                Log.d("Playback score", "warning: " + warning.toString());

                warnings += warning.toString();
            }

            Log.d("Playback score", "warnings: " + warnings);

        }


        // Instantiate the player with our current composition too to allow for playback
        // (The player creation code is based off of the SeeScore examples provide to us by
        //  Dolphin Computing (Cambridge) Ltd.)
        try {
            player = new Player(score, new CrescendoUserTempo(), this, true);


            // When the player stops playing, we need to be notified
            player.setEndHandler(new Dispatcher.EventHandler() {
                @Override
                public void event(int index, boolean countIn) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            // Reset play button to show play rather than pause
                            playerIsPlaying = false;

                            // Player needs to start at start again...
                            currentBar = 0;
                            seeScoreView.setCursorAtBar(currentBar, SeeScoreView.CursorType.line, 0);
                            player.startAt(currentBar, true);

                            updatePlayButtonUI();

                        }
                    });
                }
            }, 0);

            seeScoreView.setCursorAtBar(currentBar, SeeScoreView.CursorType.line, 0);
            player.startAt(currentBar, true);

            Log.d("PlaybackActivity", "Instantiated player with score; player = " + player);

        } catch (Player.PlayerException e) {
            e.printStackTrace();
            // Unable to create player...
            player = null;
            Log.d("PlaybackActivity", "unable to instantiate player!");

        }

        final FloatingActionButton playPauseButton = (FloatingActionButton) findViewById(R.id.playPausePlaybackButton);
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle playback
                playPauseButtonPressed();
            }
        });

        // Set initial 'play' image programmatically.
        updatePlayButtonUI();

        final FloatingActionButton stopPlaybackButton = (FloatingActionButton) findViewById(R.id.stopPlaybackButton);
        stopPlaybackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop playback
                stopButtonPressed();
            }
        });

        final FloatingActionButton sharePlaybackButton = (FloatingActionButton) findViewById(R.id.sharePlaybackButton);
        sharePlaybackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Share playback
                shareComposition();
            }
        });

        final FloatingActionButton donePlaybackButton = (FloatingActionButton) findViewById(R.id.donePlaybackButton);
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

        // Ensure SeeScore displays the score that's been passed into this activity.
        setupScore();

    }

    /**
     * This method is fired whenever this screen is loaded.
     * It refreshes the SeeScore view.
     */
    @Override
    protected void onResume() {
        super.onResume();

        setupScore();

    }

    /**
     * This method sets up the SeeScore score viewer to display the current score.
     */
    void setupScore() {
        seeScoreView.setScore(score, 1.0f);

    }

    /**
     * This method toggles playback of the current score via the SeeScore SDK and updates the
     * UI in this activity accordingly.
     */
    void playPauseButtonPressed() {
        Log.d("PlaybackActivity", "Play pause pressed");
        // Invert current playback status
        playerIsPlaying = !playerIsPlaying;

        if (player != null) {

            if (playerIsPlaying) {
                // Play

                // Has the player already played up to the end of the composition?
                if (player.state() == Player.State.Completed) {
                    // Reset then play
                    player.reset();

                    // Reset player to start
                    currentBar = 0;
                    seeScoreView.setCursorAtBar(currentBar, SeeScoreView.CursorType.line, 0);
                    player.startAt(currentBar, true);

                    Log.d("PlaybackActivity", "Player reset");

                }

                seeScoreView.setCursorAtBar(currentBar, SeeScoreView.CursorType.line, 0);
                player.startAt(currentBar, true);

                player.resume();
                Log.d("PlaybackActivity", "Player resumed");


            } else {
                // Save current bar...
                currentBar = player.currentBar();

                // Pause
                player.pause();
                Log.d("PlaybackActivity", "Player paused");

            }
        } else {
            Log.d("PlaybackActivity", "Player is null");

        }

        updatePlayButtonUI();

    }

    /**
     * Stops the playback of the current score in SeeScore, and resets the playback UI.
     */
    void stopButtonPressed() {
        Log.d("PlaybackActivity", "Playback stopped");

        // Stop playing.
        playerIsPlaying = false;

        // Reset player to start
        currentBar = 0;
        seeScoreView.setCursorAtBar(currentBar, SeeScoreView.CursorType.line, 0);
        player.startAt(currentBar, true);

        if (player != null) {
            player.reset();
        }

        updatePlayButtonUI();

    }

    /**
     * Updates the play/pause button to reflect the current state of playback.
     */
    void updatePlayButtonUI() {
        final FloatingActionButton playPauseButton = (FloatingActionButton) findViewById(R.id.playPausePlaybackButton);

        if (playerIsPlaying) {
            // Show pause UI
            playPauseButton.setImageResource(R.drawable.pause);

        } else {
            // Show play UI
            playPauseButton.setImageResource(R.drawable.play);

        }


    }

    /**
     * Shares the current score by first uploading it to our web API, and then presenting a standard
     * Android share intent to allow users to share the link to the MusicXML version of the
     * composition on Facebook, Twitter, and other social media.
     */
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

        // Upload composition...
        CrescendoAPIManager.uploadComposition(musicXmlScore, new CrescendoAPIResponseHandler() {
            /**
             * Uploaded to our API - show a share sheet so that the URL to the upload can be shared.
             * @param uploadId  an integer containing the ID of that upload.
             * @param uploadUrl a String containing the URL to that upload, so the user can download their
             */
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

            /**
             * Upload to our API failed; show a generic failure warning.
             */
            @Override
            public void uploadFailed() {
                loadingDialog.dismiss();

                // Show alert (if they haven't cancelled the upload)...
                if (shouldContinueSharing) {
                    // Display error...
                    new AlertDialog.Builder(PlaybackActivity.this)
                            .setTitle("Sharing failed")
                            .setMessage("Sorry, it looks like sharing of your composition failed, please check your internet connection and try again...")
                            .setPositiveButton("Okay", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
        });

    }

}
