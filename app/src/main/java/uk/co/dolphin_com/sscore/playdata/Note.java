/**
 * SeeScore Android API
 * Dolphin Computing http://www.dolphin-com.co.uk
 */

package uk.co.dolphin_com.sscore.playdata;

/**
 * A note which contains all information required to play it
 */
public class Note
{
	/**
	 * not grace note type
	 */
	public static final int Grace_No = 0;
	
	/**
	 * appoggiatura grace type
	 */
	public static final int Grace_Appoggiatura = 1;
	
	/**
	 * acciaccatura grace type
	 */
	public static final int Grace_Acciaccatura = 2;

	/**
	 * 60 = C4. 0 = unpitched (ie percussion or metronome)
     * For the metronome part this is the beat index
	 */
	public final int midiPitch;
	
	/**
	 * index of bar in which this note starts (may be tied)
	 */
	public final int startBarIndex;
	
	/**
	 * start time from start of bar (milliseconds)
	 */
	public final int start;
	
	/**
	 * (ms) may be longer than a bar if tied
	 */
	public final int duration;
	
	/**
	 * [0..100+] value of the last dynamic
	 * may exceed 100 for ff - see PlayData.maxSoundDynamic
	 */
	public final int dynamic;
	
	/**
	 * Grace_* set for grace note
	 */
	public final int grace;
	
	/**
	 * item handle used in sscore_contents
	 */
	public final int item_h;

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Note");
		sb.append(" midiPitch:").append(midiPitch);
		sb.append(" startBarIndex:").append(startBarIndex);
		sb.append(" start:").append(start);
		sb.append(" duration:").append(duration);
		sb.append(" dynamic:").append(dynamic);
		if (grace != Grace_No)
		{
			switch (grace)
			{
			case Grace_Appoggiatura: sb.append("appoggiatura grace");break;
			case Grace_Acciaccatura: sb.append("acciaccatura grace");break;
			}
		}
		sb.append(" item_h:").append(item_h);
		return sb.toString();
	}
	private Note(int m, int sbi, int s, int dur, int dyn, int g, int item_h) {
		this.midiPitch = m;
		this.startBarIndex = sbi;
		this.start = s;
		this.duration = dur;
		this.dynamic = dyn;
		this.grace = g;
		this.item_h = item_h;
	}
}