/**
 * SeeScore Android API
 * Dolphin Computing http://www.dolphin-com.co.uk
 */

package uk.co.dolphin_com.sscore.ex;

@SuppressWarnings("serial")
public class InternalFaultException extends ScoreException
{
	public InternalFaultException(String detail)
	{
		super(detail);
	}
}