package br.com.simpleunit.reports;

import br.com.simpleunit.exceptions.ReportGenerationException;

public interface Report {
	
	public abstract void makeReport() throws ReportGenerationException;
	
}
