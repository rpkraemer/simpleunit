package br.com.simpleunit;

import br.com.simpleunit.reports.ReportType;

/**
 * Public Main Class
 * Used to fire a SimpleUnit Execution
 * 
 * @author Robson Paulo Kraemer (rpkraemer@gmail.com)
 *
 */
public class Runner {
	
	private static SimpleUnit core;
	
	/**
	 * Use this method to fire the execution of your TestCases.<br/>
	 * It receives two arguments
	 * 
	 * @param packages - your TestCases package(s) name(s)
	 * @param reportType - report type to output the execution results.<br/>
	 * Use ReportType enum
	 */
	public static void run(String [] packages, ReportType reportType) {
		core = new SimpleUnit();
		
		core.forPackages(packages).
			 reportIn(reportType).
			 execute();
	}
}