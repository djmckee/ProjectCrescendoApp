/**
 * SeeScore Android API
 * Dolphin Computing http://www.dolphin-com.co.uk
 */

package uk.co.dolphin_com.sscore;

/**
 * Define options for the score layout
 */
public class LayoutOptions {

	/**
	 * Define options for layout
	 * 
	 * @param hidePartNames if true no part names are to be displayed at left of score
	 * @param hideBarNumbers if true no bar numbers are to be displayed in the score
	 * @param simplifyHarmonyEnharmonicSpelling if true harmonies are displayed in the simplest way (eg F-sharp instead of E double-sharp) 
	 */
	public LayoutOptions(boolean hidePartNames,
			boolean hideBarNumbers,
			boolean simplifyHarmonyEnharmonicSpelling)// set this flag so F-double-sharp appears in a harmony as G
	{
		this.hidePartNames = hidePartNames;
		this.hideBarNumbers = hideBarNumbers;
		this.simplifyHarmonyEnharmonicSpelling = simplifyHarmonyEnharmonicSpelling;		
	}
	
	/**
	 * construct with default options
	 */
	public LayoutOptions()
	{
		this.hidePartNames = false;
		this.hideBarNumbers = false;
		this.simplifyHarmonyEnharmonicSpelling = false;		
	}

	final boolean hidePartNames;
	final boolean hideBarNumbers;
	final boolean simplifyHarmonyEnharmonicSpelling;
}
