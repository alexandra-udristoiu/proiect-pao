package exception;

public class DuplicateIdException extends Exception {

	private static final long serialVersionUID = 8733124770961829692L;
	
	public DuplicateIdException() {
		super("ID is already used");
	}

}
