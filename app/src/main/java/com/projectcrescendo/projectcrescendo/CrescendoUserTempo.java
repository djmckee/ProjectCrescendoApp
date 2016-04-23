package com.projectcrescendo.projectcrescendo;

import uk.co.dolphin_com.sscore.playdata.UserTempo;

/**
 * A concrete implementation of the SeeScore SDK's UserTemp interface.
 * As per the SeeScore SDK implementation example code, provided to use by Dolphin Computing (Cambridge) Ltd.
 * <p/>
 * <p/>
 * Created by Dylan McKee on 01/03/16.
 */
class CrescendoUserTempo implements UserTempo {

    /**
     * The default tempo - a moderate piano tempo value of 120BPM, looked up at
     * http://www.enjoy-your-piano.com/how-to-read-music-tempo-dynamics.html
     */
    public static final int DEFAULT_TEMPO_BPM = 120;

    /**
     * The default tempo scale - defaults to a scale of 1.0 meaning the tempo is not multiplied by
     * default.
     */
    public static final float DEFAULT_TEMPO_SCALE = 1.0f;

    /**
     * The current value for user tempo. This can be set by the user using the slider UI within the
     * PlaybackActivity, but defaults to a moderate piano tempo value of 120BPM, looked up at
     * http://www.enjoy-your-piano.com/how-to-read-music-tempo-dynamics.html
     */
    private int userTempo = DEFAULT_TEMPO_BPM;

    /**
     * The current tempo scaling. This can be set by the user using the slider UI within the
     * PlaybackActivity, but defaults to a scale of 1.0 meaning the tempo is not multiplied by
     * default.
     */
    private float userTempoScaling = DEFAULT_TEMPO_SCALE;

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

    /**
     * Sets the tempo stored within the current CrescendoUserTempo instance to whatever value has
     * been passed to this method as a parameter.
     *
     * @param userTempo the new tempo value, in BPM.
     */
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

    /**
     * Sets the scale of the tempo (i.e. a tempo multiplier)
     *
     * @param userTempoScaling a float containing the tempo multiplier.
     */
    public void setUserTempoScaling(float userTempoScaling) {
        this.userTempoScaling = userTempoScaling;
    }


}
