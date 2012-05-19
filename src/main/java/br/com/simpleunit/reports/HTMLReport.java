package br.com.simpleunit.reports;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import br.com.simpleunit.CoreWrapper;
import br.com.simpleunit.exceptions.InvalidPropertiesFile;
import br.com.simpleunit.exceptions.ReportGenerationException;
import br.com.simpleunit.objects.AssertionResult;
import br.com.simpleunit.objects.FinalResults;
import br.com.simpleunit.objects.UnitResult;
import br.com.simpleunit.util.SimpleUnitPropertiesReader;

public class HTMLReport extends BaseReport {

	/*
	 * Objeto responsável por ler as configurações de output do relatório no arquivo properties do framework
	 */
	private SimpleUnitPropertiesReader fwPropsReader;

	private File reportFile, reportDirectory;
	private String reportFilename;
	private Formatter output;
	private HTMLFormatter htmlFormatter;
	
	public HTMLReport(CoreWrapper core) {
		super(core);
		
		//Instancia o objeto e carrega o properties em memória
		fwPropsReader = new SimpleUnitPropertiesReader();
		fwPropsReader.loadPropertiesFile();
		
		//Instancia o formatador HTML
		htmlFormatter = new HTMLFormatter();
		
		//Define o nome do relatório a ser gerado
		this.reportFilename = "SIMPLE_UNIT_" + 
				new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").
						format(new Date(System.currentTimeMillis())) + ".HTML";
	}
	
	private void createAndOpenReportFile() throws IOException {
		if (this.fwPropsReader.loadedSucessfully()) {
			String reportGenerationDirectory = fwPropsReader.getProperty("html_dir");
			
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
			htmlFormatter.insertElement("HTML", null, false);
			htmlFormatter.insertElement("HEAD", null, false);
			htmlFormatter.insertElement("TITLE", "Execution Results - SimpleUnit 0.1");

			// Inclui o arquivo CSS definido no classpath do projeto
			htmlFormatter.includeCSS();
			
			// Insere título do relatório
			htmlFormatter.insertElement("H3", reportFilename);
			
			htmlFormatter.insertElement("HEAD", null, true);
		} catch (IOException e) {
			throw new ReportGenerationException("Failure in generating HTML report: " + e.getMessage());
		}

	}

	@Override
	public void generateBody() throws ReportGenerationException {
		htmlFormatter.insertElement("BODY", null, false);
		
		String [] columnNames = {"Method (Unit)", "Status"};
		
		List<UnitResult> unitResults = core.getFinalResults().getUnitResults();
		// Inicia o array de dados de cada UnitResult de acordo com a quantidade da lista unitResults
		// Quantidade de unitResults : 1, pois cada iteração irá comportar um único UnitResult e o array será resetado
		// Colunas na tabela: columnNames.length()
		String [][] data = new String[1][columnNames.length];
		
		for (int idx = 0; idx < unitResults.size(); idx++) {
			UnitResult unitResult = unitResults.get(idx);
			data[0][0] = unitResult.getUnitMethod().getName();
			data[0][1] = unitResult.isPassed() ? "PASSED" : "FAILED";
				
			//Insere o título com o nome da Classe @Test
			
			//Se for o primeiro item mostra, senão
			//Verifica se o elemento atual foi diferente do anterior
			//Se sim, mostra
			if (idx == 0) {
				Class<?> currentTestClass = unitResult.getTestCase().getTestClass();
				htmlFormatter.insertElement("HR", null);
				htmlFormatter.insertElement("H2", currentTestClass.getName());
			} else {
				Class<?> currentTestClass = unitResult.getTestCase().getTestClass();
				Class<?> previousTestClass = unitResults.get(idx-1).getTestCase().getTestClass();
				if (currentTestClass != previousTestClass) {
					htmlFormatter.insertElement("HR", null);
					htmlFormatter.insertElement("H2", currentTestClass.getName());
				}
			}
				
			//Insere a tabela com as informações pré-asserções do Unit
			htmlFormatter.insertTable(columnNames, data, null);
			
			//Se é esperado exceções para o Unit, insere uma tabela de informação da mesma
			if (unitResult.getExpectedException() != null) {
				outputExceptionInformationOfUnit(unitResult);
			}
			
			//Insere as asserções do Unit
			outputAssertionsOfUnit(unitResult);
		}
	}
		
	@Override
	public void generateFooter() {
		super.generateFooter();
		
		FinalResults finalResults = core.getFinalResults();
		
		//Insere as informações contabilizadas do relatório
		htmlFormatter.insertElement("DIV", "class=\"final_results\"", false);
		htmlFormatter.insertElement("SPAN", "<strong>Total Number of Units</strong> - " + finalResults.getTotalNumberOfUnits() + "<br>");
		htmlFormatter.insertElement("SPAN", "<strong>Total Number of Passed Units</strong> - " + finalResults.getTotalNumberOfPassedUnits() + "<br>");
		htmlFormatter.insertElement("SPAN", "<strong>Total Number of Failed Units</strong> - " + finalResults.getTotalNumberOfFailedUnits() + "<br><br>");
		htmlFormatter.insertElement("SPAN", "<strong>Total Number of Assertions</strong> - " + finalResults.getTotalNumberOfAssertions() + "<br>");
		htmlFormatter.insertElement("SPAN", "<strong>Total Number of Passed Assertions</strong> - " + finalResults.getTotalNumberOfPassedAssertions() + "<br>");
		htmlFormatter.insertElement("SPAN", "<strong>Total Number of Failed Assertions</strong> - " + finalResults.getTotalNumberOfFailedAssertions() + "<br>");
		htmlFormatter.insertElement("DIV", null, true);
		
		//Insere um grágico ilustrando o status dos Unit Tests - Google Chart API
		htmlFormatter.insertElement("DIV", "class=\"chart\"", false);
		
		int passedUnits, failedUnits;
		passedUnits = finalResults.getTotalNumberOfPassedUnits();
		failedUnits = finalResults.getTotalNumberOfFailedUnits();
				
		htmlFormatter.insertElement("IMG", 
								    "src=\"https://chart.googleapis.com/chart?cht=p3&chs=350x100&chco=08DE08|FF0000&"+
								    "chd=t:"+passedUnits+","+failedUnits+"&"+
								    "chl="+passedUnits+"|"+failedUnits+
								    "&chdl=Passed%20Units|Failed%20Units\"", true);
		
		htmlFormatter.insertElement("DIV", null, true);
		
		htmlFormatter.insertElement("SPAN", "Time elapsed: " + core.timeElapsed() + " ms", "id=\"time_used\"");
		htmlFormatter.insertElement("BODY", null, true);
		htmlFormatter.insertElement("HTML", null, true);
		
		//escreve o conteúdo em disco
		output.flush();
		output.close();
	}
	
	private void outputExceptionInformationOfUnit(UnitResult unitResult) {
		String [] columnNames = {"Expected Exception",  "Occurred Exception"};
		String [][] data = new String[1][columnNames.length];
		
		data[0][0] = unitResult.getExpectedException().getName();
		data[0][1] = unitResult.getExceptionOccurred() != null ? 
	    				unitResult.getExceptionOccurred().getName() : 
	    				"Did Not Occur Exception";
		
		htmlFormatter.insertTable(columnNames, data, "exception");
	}

	private void outputAssertionsOfUnit(UnitResult unitResult) {
		String [] columnNames = {"Assertion",  "Message", "Status"};
		List<AssertionResult> assertions = unitResult.getAssertions();
		String [][] data = new String[assertions.size()][columnNames.length];
		
		for (int idx = 0; idx < assertions.size(); idx++) {
			AssertionResult assertion = assertions.get(idx);
			data[idx][0] = assertion.getAssertionType();
			data[idx][1] = assertion.getMessage();
			data[idx][2] = assertion.isPassed() ? "PASSED" : "FAILED";
		}
		
		//Insere a tabela com as informações das asserções do Unit
		htmlFormatter.insertTable(columnNames, data, "assertions");
	}

	/**
	 * 
	 * @author robsonpk
	 * Classe HELPER utilizada na formatação do relatório HTML
	 */
	private class HTMLFormatter {

		/**
		 * Este método insere um elemento HTML no relatório.
		 * 
		 * @param element - Nome do elemento HTML a ser adicionado ao relatório
		 * @param value - Valor do elemento HTML a ser adicionado ao relatório
		 * Caso <b>value</b> seja null, o elemento html será inserido vazio
		 * 
		 * @see HTMLFormatter#insertElement(String, String, String) Outra forma de inserir elementos
		 */
		public void insertElement(String element, String value) {
			if (value != null)
				output.format("<%1$s> %2$s </%1$s> %n", element, value);
			else
				output.format("<%1$s/> %n", element);
		}
		
		/**
		 * Este método insere no relatório, um elemento HTML aceitando atributos.
		 * 
		 * @param element - Nome do elemento HTML a ser adicionado ao relatório
		 * @param value - Valor do elemento HTML a ser adicionado ao relatório
		 * Caso <b>value</b> seja null, o elemento html será inserido vazio
		 * @param elementAttributes - Atributos do elemento HTML (Ex: class="foo" id="1")
		 * 
		 * @see HTMLFormatter#insertElement(String, String) Outra forma de inserir elementos
		 */
		public void insertElement(String element, String value, String elementAttributes) {
				if (value != null)
					output.format("<%1$s %3$s> %2$s </%1$s> %n", element, value, elementAttributes);
				else
					output.format("<%1$s %2$s/> %n", element, elementAttributes);
		}
		
		/**
		 * Este método insere no relatório um elemento HTML sem valor, levando-se em consideração
		 * o parâmetro <b>closeTag</b>.
		 * 
		 * @param element - Nome do elemento HTML a ser adicionado ao relatório
		 * @param elementAttributes - Caso esteja abrindo uma tag HTML (closeTag == false) e deseja 
		 * passar atributos ao elemento deve-se usar este parâmetro. 
		 * <b>Passar null se deseja um elemento HTML SEM atributos</b>
		 * @param closeTag - Se FALSE, insere a tag de abertura do elemento (Ex: <body>)
		 * Se TRUE, insere a tag de fechamento do elemento (Ex: </body>)
		 * 
		 * @see HTMLFormatter#insertElement(String, String) Outra forma de inserir elementos
		 */
		public void insertElement(String element, String elementAttributes, boolean closeTag) {
			if (closeTag)
				if (elementAttributes != null)
					output.format("<%1$s %2$s/> %n", element, elementAttributes);
				else
					output.format("</%1$s> %n", element);
			else
				if (elementAttributes != null)
					output.format("<%1$s %2$s> %n", element, elementAttributes);
				else
					output.format("<%1$s> %n", element);
		}
		
		/**
		 * Este método insere uma tabela no relatório
		 * @param columnNames - recebe um String[] com o nome das colunas da tabela
		 * @param data - recebe um String[][] identificando as linhas da tabela
		 */
		public void insertTable(String [] columnNames, String [][] data, String tableCSSClass) {

			if (tableCSSClass != null)
				output.format("<table class=\"%1$s\"> %n <thead> %n <tr>", tableCSSClass);
			else
				output.format("<table> %n <thead> %n <tr>");

			//Monta o THEAD da tabela com as colunas passadas
				for (String columnHeader : columnNames) {
					if (columnHeader.equalsIgnoreCase("STATUS"))
						insertElement("th", columnHeader, "style=\"width:15%; text-align:center\"");
					else
						insertElement("th", columnHeader);
				}
			output.format("</tr> %n </thead> %n <tbody>");
			
			//Monta o TBODY da tabela de acordo com o array data passado
			for (String[] row : data) {
				output.format("<tr> %n");
				for (String columnValue : row) {
					// Se a coluna for a de status seta a classe CSS correspondente
					if ("PASSED".equalsIgnoreCase(columnValue) ||
						"FAILED".equalsIgnoreCase(columnValue))
						insertElement("td", columnValue, "class=\"" + columnValue + "\"");
					else //Caso contrário, insere a coluna normalmente 
						insertElement("td", columnValue);
				}
				output.format("</tr> %n");
			}
			output.format("</tbody> %n </table>");
		}
		
		public void includeCSS() throws IOException {
			insertElement("STYLE", null, false);
			InputStream cssFileStream = Thread.currentThread().getContextClassLoader().
					getResourceAsStream("html_report.css");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(cssFileStream));
			String line;
			while ((line = br.readLine()) != null) {
				//Insere cada linha do arquivo de css no arquivo html
				output.format("%1$s%n", line);
			}
			insertElement("STYLE", null, true);

			br.close();
			cssFileStream.close();
		}
	}
}