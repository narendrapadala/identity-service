/**
 * 
 */
package corp.asbp.platform.is.exception;

/**
 * @author Narendra
 *
 */
public class ClientVersionUpgradeException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ClientVersionUpgradeException(String message, Throwable cause, int errorCode) {
		this(message, cause);
	}
	public ClientVersionUpgradeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientVersionUpgradeException(String message) {
		super(message, null);
	}
}
