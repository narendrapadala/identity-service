/**
 * 
 */
package corp.asbp.platform.is.exception;

/**
 * @author Narendra
 *
 */
public class ASBPException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ASBPException(String message, Throwable cause, int errorCode) {
		this(message, cause);
	}
	public ASBPException(String message, Throwable cause) {
		super(message, cause);
	}
	public ASBPException(String message) {
		this(message,null);
	}
}
