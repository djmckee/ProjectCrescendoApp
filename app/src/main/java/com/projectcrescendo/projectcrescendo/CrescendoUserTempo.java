package com.projectcrescendo.projectcrescendo;

import uk.co.dolphin_com.sscore.playdata.UserTempo;

/**
 * A concrete implementation of the SeeScore SDK's UserTemp interface.
 * As per the SeeScore SDK implementation example code, provided to use by Dolphin Computing (Cambridge) Ltd.
 * <p>
 * <p>
 * Created by Dylan McKee on 01/03/16.
 */
public class CrescendoUserTempo implements UserTempo {

    /**
     * Returns the current tempo in beats per minute.
     *
     * @return the current tempo for playback, in beats per minute.
     */
    @Override
    public int getUserTempo() {
        /** TODO: we could make user tempo variable eventually, but for now just keeping it at a
         mid piano tempo; I looked up tempo values at
         http://www.enjoy-your-piano.com/how-to-read-music-tempo-dynamics.html */
        return 80;
    }

    /**
     * Returns the scale of the tempo (i.e. a tempo multiplier), as a float
     *
     * @return a float containing the tempo multiplier.
     */
    @Override
    public float getUserTempoScaling() {
        // 1.0 = use standard tempo scaling.
        return 1.0f;
    }

}
