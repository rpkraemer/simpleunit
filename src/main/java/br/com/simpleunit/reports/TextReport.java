package br.com.simpleunit.reports;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import br.com.simpleunit.CoreWrapper;
import br.com.simpleunit.exceptions.InvalidPropertiesFile;
import br.com.simpleunit.exceptions.ReportGenerationException;
import br.com.simpleunit.objects.AssertionResult;
import br.com.simpleunit.objects.FinalResults;
import br.com.simpleunit.objects.UnitResult;
import br.com.simpleunit.util.SimpleUnitPropertiesReader;

public class TextReport extends BaseReport {

	/*
	 * Objeto responsável por ler as configurações de output do relatório no arquivo properties do framework
	 */
	private SimpleUnitPropertiesReader fwPropsReader;

	private File reportFile, reportDirectory;
	private String reportFilename;
	private Formatter output;
	
	/*
	 * Atributos que definem os formatos de escrita no relatório - Início
	 */
	private static final String HEADER_FORMAT = "%n\t\t\t\t\t*** %1$s **** %n%n";
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
	private final String LINE         = "*******************************************************************" + 
										"*******************************************************************%n%n";
	private final String UNIT_DIVISOR = "%n-------------------------------------------------------------------"+
								 		"-------------------------------------------------------------------%n";
	/*
	 * Atributos que definem os formatos de escrita no relatório - Final
	 */
		
	public TextReport(CoreWrapper core) {
		super(core);
		
		//Instancia o objeto e carrega o properties em memória
		fwPropsReader = new SimpleUnitPropertiesReader();
		fwPropsReader.loadPropertiesFile();
		
		//Define o nome do relatório a ser gerado
		this.reportFilename = "SIMPLE_UNIT_" + 
				new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").
						format(new Date(System.currentTimeMillis())) + ".TXT";
	}
	
	private void createAndOpenReportFile() throws IOException {
		if (this.fwPropsReader.loadedSucessfully()) {
			String reportGenerationDirectory = fwPropsReader.getProperty("text_dir");
			
			reportDirectory = new File(reportGenerationDirectory);
			reportFile = new File(reportGenerationDirectory, reportFilename);

			if (!reportDirectory.exists())
				reportDirectory.mkdirs();
			
			output = new Formatter(reportFile);
		} else {
			throw new InvalidPropertiesFile("Could not load the properties file of the framework.\n" +
					"Check if it is created in the project classpath");
		}
	}

	@Override
	public void generateHeader() throws ReportGenerationException {
		try {
			createAndOpenReportFile();
			output.format(HEADER_FORMAT, "Execution Results - SimpleUnit 0.1");
			output.format(LINE);
		} catch (IOException e) {
			throw new ReportGenerationException("Failure in generating TEXT report: " + e.getMessage());
		}
	}

	@Override
	public void generateBody() throws ReportGenerationException {
		for (UnitResult unitResult : core.getFinalResults().getUnitResults()) {
			//Se o Unit deste resultado não esperava exceção
			if (unitResult.getExpectedException() == null) {
				//Monta a parte do body pré-listagem de asserções
				output.format(BODY_UNIT_FORMAT_WITHOUT_EXCEPTION_PRE_ASSERTIONS,
							  unitResult.getTestCase().getTestClass().getName(),
							  unitResult.getUnitMethod().getName());
				
				//Monta as asserções do Unit
				printAssertionsOfUnit(unitResult);
				//Monta as informações do Unit a serem exibidas após as asserções
				printPosAssertionsUnitInformations(unitResult);
			} else {
				//Monta a parte do body pré-listagem de asserções
				output.format(BODY_UNIT_FORMAT_WITH_EXCEPTION_PRE_ASSERTIONS,
							  unitResult.getTestCase().getTestClass().getName(),
							  unitResult.getUnitMethod().getName(),
							  unitResult.getExpectedException().getName(),
							  unitResult.getExceptionOccurred() != null ? 
							    		unitResult.getExceptionOccurred().getName() : 
							    		"Did Not Occur Exception");
				
				//Monta as asserções do Unit
				printAssertionsOfUnit(unitResult);
				//Monta as informações do Unit a serem exibidas após as asserções
				printPosAssertionsUnitInformations(unitResult);
			}
			output.format(UNIT_DIVISOR);
		}
	}

	private void printPosAssertionsUnitInformations(UnitResult unitResult) {
		output.format(BODY_UNIT_FORMAT_POS_ASSERTIONS, 
				unitResult.getNumberOfPassedAssertions(),
				unitResult.getNumberOfFailedAssertions(),
				unitResult.getNumberOfAssertions(),
				unitResult.isPassed() ? "PASSED" : "FAILED");
	}

	private void printAssertionsOfUnit(UnitResult unitResult) {
		for (AssertionResult assertion : unitResult.getAssertions()) {
			output.format(BODY_UNIT_ASSERTION_FORMAT, assertion.getAssertionType(), 
												 assertion.getMessage() != null ? assertion.getMessage() : "", 
												 assertion.isPassed() ? "PASSED" : "FAILED");
		}
	}

	@Override
	public void generateFooter() {
		super.generateFooter();
		
		FinalResults finalResults = core.getFinalResults();
		
		//Informações contabilizadas do relatório
		output.format(FOOTER_FORMAT,
					  finalResults.getTotalNumberOfUnits(),
					  finalResults.getTotalNumberOfPassedUnits(),
					  finalResults.getTotalNumberOfFailedUnits(),
					  finalResults.getTotalNumberOfAssertions(),
					  finalResults.getTotalNumberOfPassedAssertions(),
					  finalResults.getTotalNumberOfFailedAssertions(),
					  core.timeElapsed());
		output.flush();
		output.close();
	}
}