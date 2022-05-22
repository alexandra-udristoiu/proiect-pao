package exception;

public class DuplicateIdException extends Exception {
	
	public DuplicateIdException() {
		super("ID is already used");
	}

}
