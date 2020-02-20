/**
 * 
 */
package corp.asbp.platform.is.exception;

/**
 * @author Narendra
 *
 */
public class AccessForbiddenException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public AccessForbiddenException(String message, Throwable cause, int errorCode) {
		this(message, cause);
	}
	public AccessForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessForbiddenException(String message) {
		super(message, null);
	}
}
