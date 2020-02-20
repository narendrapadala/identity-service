package corp.asbp.platform.is.exception;

public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message, Throwable cause, int errorCode) {
		this(message, cause);
	}
	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFoundException(String message) {
		super(message, null);
	}
}
