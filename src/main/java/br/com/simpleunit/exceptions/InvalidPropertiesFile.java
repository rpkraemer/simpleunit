package br.com.simpleunit.exceptions;

public class InvalidPropertiesFile extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidPropertiesFile(String message) {
		super(message);
	}
}
