package br.com.simpleunit.exceptions;

public class ReportGenerationException extends RuntimeException {
	
	/**
	 * 
	 * @author Edipo Federle
	 * @author Robson Paulo Kraemer (rpkraemer@gmail.com) 
	 *
	 *
	 */
	private static final long serialVersionUID = 842345592639170251L;

	public ReportGenerationException(String message) {
		super (message);
	}

}
