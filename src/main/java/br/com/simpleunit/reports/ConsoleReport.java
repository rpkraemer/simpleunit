package br.com.simpleunit.reports;

import br.com.simpleunit.CoreWrapper;
import br.com.simpleunit.exceptions.ReportGenerationException;
import br.com.simpleunit.objects.AssertionResult;
import br.com.simpleunit.objects.FinalResults;
import br.com.simpleunit.objects.UnitResult;

class ConsoleReport extends BaseReport {

	public ConsoleReport(CoreWrapper core) {
		super(core);
		output = new StringBuilder();
	}

	private StringBuilder output;
	private static final String HEADER_FORMAT = "\t\t\t\t\t*** %1$s **** %n%n";
	private static final String BODY_UNIT_FORMAT_WITHOUT_EXCEPTION_PRE_ASSERTIONS = "Class:\t\t%1$s %nMethod (Unit):\t%2$s %n%nAssertions%n";
	private static final String BODY_UNIT_FORMAT_WITH_EXCEPTION_PRE_ASSERTIONS    = "Class:\t\t%1$s %nMethod (Unit):\t%2$s %n%n" +
																			 		"Expected Exception:\t%3$s%nOccurred Exception:\t%4$s %n%nAssertions%n";
	private static final String BODY_UNIT_FORMAT_POS_ASSERTIONS 				  = "%nPassed Assertions:\t%1$s %nFailed Assertions:\t%2$s %n" +
															  				 		"Number of Assertions:\t%3$s %n\t\t\t\tUnit Status:\t%4$s";
	private static final String BODY_UNIT_ASSERTION_FORMAT 							  = "\t%1$s (%3$s)\t%2$s%n";
	private static final String FOOTER_FORMAT                                     = "%n%nTotal Number of Units:\t\t\t%1$s %nTotal Number of Passed Units:\t\t%2$s %n" +
										 									 		"Total Number of Failed Units:\t\t%3$s %n%nTotal Number of Assertions:\t\t%4$s %n" +
										 									 		"Total Number of Passed Assertions:\t%5$s %nTotal Number of Failed Assertions:\t%6$s" +
										 									 		"%n%nTime elapsed: %7$s ms";
	private static final String LINE         = "*******************************************************************" + 
											   "*******************************************************************%n%n";
	private static final String UNIT_DIVISOR = "%n-------------------------------------------------------------------"+
								 			   "-------------------------------------------------------------------%n";
	
	@Override
	public void generateHeader() throws ReportGenerationException {
		output.append(String.format(HEADER_FORMAT, "Execution Results - SimpleUnit 0.1"));
		output.append(String.format(LINE));
	}

	@Override
	public void generateBody() throws ReportGenerationException {
		for (UnitResult unitResult : core.getFinalResults().getUnitResults()) {
			//Se o Unit deste resultado não esperava exceção
			if (unitResult.getExpectedException() == null) {
				output.append(String.format(BODY_UNIT_FORMAT_WITHOUT_EXCEPTION_PRE_ASSERTIONS,
											unitResult.getTestCase().getTestClass().getName(),
											unitResult.getUnitMethod().getName()));
				
				printAssertionsOfUnit(unitResult);
				printPosAssertionsUnitInformations(unitResult);
			} else {
				output.append(String.format(BODY_UNIT_FORMAT_WITH_EXCEPTION_PRE_ASSERTIONS,
										    unitResult.getTestCase().getTestClass().getName(),
										    unitResult.getUnitMethod().getName(),
										    unitResult.getExpectedException().getName(),
										    unitResult.getExceptionOccurred() != null ? 
										    		unitResult.getExceptionOccurred().getName() : 
										    		"Did Not Occur Exception"));
				
				printAssertionsOfUnit(unitResult);
				printPosAssertionsUnitInformations(unitResult);
			}
			output.append(String.format(UNIT_DIVISOR));
		}
		
	}

	@Override
	public void generateFooter() throws ReportGenerationException {
		super.generateFooter();

		FinalResults finalResults = core.getFinalResults();
		
		//Informações contabilizadas do relatório
		output.append(String.format(FOOTER_FORMAT,
								    finalResults.getTotalNumberOfUnits(),
								    finalResults.getTotalNumberOfPassedUnits(),
								    finalResults.getTotalNumberOfFailedUnits(),
								    finalResults.getTotalNumberOfAssertions(),
								    finalResults.getTotalNumberOfPassedAssertions(),
								    finalResults.getTotalNumberOfFailedAssertions(),
								    core.timeElapsed()));
		//Printa o resultado no console
		System.out.print(output.toString());
	}
	
	private void printAssertionsOfUnit(UnitResult unitResult) {
		for (AssertionResult assertion : unitResult.getAssertions()) {
			output.append(String.format(BODY_UNIT_ASSERTION_FORMAT, assertion.getAssertionType(), 
										assertion.getMessage() != null ? assertion.getMessage() : "", 
										assertion.isPassed() ? "PASSED" : "FAILED"));
		}
	}
	
	private void printPosAssertionsUnitInformations(UnitResult unitResult) {
		output.append(String.format(BODY_UNIT_FORMAT_POS_ASSERTIONS, 
									unitResult.getNumberOfPassedAssertions(),
									unitResult.getNumberOfFailedAssertions(),
									unitResult.getNumberOfAssertions(),
									unitResult.isPassed() ? "PASSED" : "FAILED"));
	}

}
