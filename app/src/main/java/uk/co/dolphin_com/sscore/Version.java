package uk.co.dolphin_com.sscore;

import java.text.NumberFormat;

/**
 * The library version information from SScore.getVersion()
 */
public class Version
{
	/**
	 * 1..
	 */
	public final int hi;
	
	/**
	 * 0 - 999
	 */
	public final int lo;

	public String toString()
	{
		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setMinimumIntegerDigits(3);
		return Integer.toString(hi) + "." + nf.format(lo);
	}

	private Version(int hi, int lo) {
		this.hi = hi;
		this.lo = lo;
	}
}