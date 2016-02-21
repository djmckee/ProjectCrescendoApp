/**
 * SeeScore Android API
 * Dolphin Computing http://www.dolphin-com.co.uk
 */

package uk.co.dolphin_com.sscore.playdata;

import java.util.Iterator;

/**
 * Iterator type which iterates through the score returning each Bar in correct play sequence accounting for repeats
 */
public class BarIterator implements Iterator<Bar>
{
    /**
	 * @return true if this is not the last bar in the score
	 */
	public native boolean hasNext();
	
	/**
	 * @return the next Bar in the score and update this iterator
	 */
	public native Bar next();

	/**
	 * unsupported for immutable list
	 */
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	private BarIterator(PlayData playData, long nativePointer, int idx, boolean ci) {
        this.playData = playData;
        this.nativePointer = nativePointer;
		this.idx = idx;
        this.ci = ci;
	}
    private void update(int idx, boolean ci)
    {
        assert(idx >= 0);
        this.idx = idx;
        this.ci = ci;
    }
    private final PlayData playData;
    private final long nativePointer;
	private int idx;
    private boolean ci;
}