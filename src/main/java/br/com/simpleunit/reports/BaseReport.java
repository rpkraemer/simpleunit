package br.com.simpleunit.reports;

import br.com.simpleunit.CoreWrapper;
import br.com.simpleunit.exceptions.ReportGenerationException;

abstract class BaseReport implements Report {

	protected CoreWrapper core;
	
	public BaseReport(CoreWrapper core) {
		this.core = core;
	}
	
	public abstract void generateHeader() throws ReportGenerationException;
	public abstract void generateBody() throws ReportGenerationException;
	
	public void generateFooter() throws ReportGenerationException {
		core.sinalizeEndExecution();
	}

	public void makeReport() throws ReportGenerationException {
		generateHeader(); 
		generateBody(); 
		generateFooter();
	}
}
