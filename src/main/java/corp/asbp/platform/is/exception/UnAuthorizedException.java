/**
 * 
 */
package corp.asbp.platform.is.exception;

/**
 * @author Narendra
 *
 */
public class UnAuthorizedException extends Exception{


	private static final long serialVersionUID = 1L;
	
	public UnAuthorizedException(String message, Throwable cause, int errorCode) {
		this(message, cause);
	}
	public UnAuthorizedException(String message, Throwable cause) {
		super(message, cause);
	}
	public UnAuthorizedException(String message) {
		super(message, null);
	}
}
