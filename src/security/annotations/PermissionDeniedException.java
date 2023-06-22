package security.annotations;

public class PermissionDeniedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PermissionDeniedException() {
System.out.println("User forbidden from accessing the functionality");
	}

	
}
