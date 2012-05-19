package br.com.simpleunit.exceptions;

public class DatabaseInteractionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DatabaseInteractionException(String message) {
		super(message);
	}
}
