package br.com.simpleunit.exceptions;

public class DatabaseInconsistenceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DatabaseInconsistenceException(String message) {
		super(message);
	} 
}
