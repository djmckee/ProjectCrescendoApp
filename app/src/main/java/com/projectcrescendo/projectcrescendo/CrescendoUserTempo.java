package com.projectcrescendo.projectcrescendo;

import uk.co.dolphin_com.sscore.playdata.UserTempo;

/**
 * A concrete implementation of the SeeScore SDK's UserTemp interface.
 * As per the SeeScore SDK implementation example code, provided to use by Dolphin Computing (Cambridge) Ltd.
 * <p>
 * <p>
 * Created by Dylan McKee on 01/03/16.
 */
class CrescendoUserTempo implements UserTempo {

    // TODO: Document.
    /**
     *
     */
    private int userTempo = 120;

    // TODO: Document.
    /**
     *
     */
    private float userTempoScaling = 1.0f;

    /**
     * Returns the current tempo in beats per minute. Returns the tempo the user has set, or
     * defaults to 120BPM (a mid piano tempo, looked up at
     * http://www.enjoy-your-piano.com/how-to-read-music-tempo-dynamics.html)
     *
     * @return the current tempo for playback, in beats per minute.
     */
    @Override
    public int getUserTempo() {
        /* Default tempo is a mid piano tempo; I looked up tempo values at
         http://www.enjoy-your-piano.com/how-to-read-music-tempo-dynamics.html */
        return userTempo;
    }

    // TODO: Document
    public void setUserTempo(int userTempo) {
        this.userTempo = userTempo;
    }

    /**
     * Returns the scale of the tempo (i.e. a tempo multiplier), as a float
     *
     * @return a float containing the tempo multiplier.
     */
    @Override
    public float getUserTempoScaling() {
        // 1.0 = use standard tempo scaling.
        return userTempoScaling;
    }

    // TODO: Document
    public void setUserTempoScaling(float userTempoScaling) {
        this.userTempoScaling = userTempoScaling;
    }


}
