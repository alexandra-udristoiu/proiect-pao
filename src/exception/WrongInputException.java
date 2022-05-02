package exception;

public class WrongInputException extends Exception {

	private static final long serialVersionUID = 123423256788765432L;

	public WrongInputException(String message) {
		super(message);
	}

}
