package com.projectcrescendo.projectcrescendo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import uk.co.dolphin_com.seescoreandroid.SeeScoreView;

public class PlaybackActivity extends ActionBarActivity {

    /**
     * the View which displays the score
     */
    private SeeScoreView seeScoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        seeScoreView = new SeeScoreView(this, this.getAssets(), null, null);



    }

}
