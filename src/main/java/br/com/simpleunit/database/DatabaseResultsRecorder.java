package br.com.simpleunit.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import br.com.simpleunit.CoreWrapper;
import br.com.simpleunit.exceptions.DatabaseInconsistenceException;
import br.com.simpleunit.exceptions.DatabaseInteractionException;
import br.com.simpleunit.exceptions.ExecutionException;
import br.com.simpleunit.objects.AssertionResult;
import br.com.simpleunit.objects.UnitResult;

class DatabaseResultsRecorder {
	
	private final String finalResultsTablename = "SIMPLE_UNIT_FINAL_RESULTS";
	private final String unitResultsTablename = "SIMPLE_UNIT_UNIT_RESULTS";
	private final String unitAssertionsTablename = "SIMPLE_UNIT_UNIT_ASSERTIONS";
	
	/*
	 * Variáveis que armazenam os códigos das execuções atuais
	 */
	private int currentUnitFinalResultsCode; //Armazena o código recém inserido na tabela finalResultsTablename
	private int currentUnitResultsCode; //Armazena o código recém inserido na tabela unitResultsTablename
	private String projectName;
	
	private Connection connection;
	private CoreWrapper core;
	
	public DatabaseResultsRecorder(ConnectionProvider connectionProvider, CoreWrapper core) {
		
		//Recebe informações da conexão e projeto setado pelo usuário na implementação da interface
		connection = connectionProvider.getConnection();
		projectName = connectionProvider.yourProjectName();
		
		//Recebe informações da execução na variável core
		this.core = core;
	}
	
	public void insertResultsOnDatabase() {
		createTablesIfNotExists();
		try {
			//Inicia uma transação para a inserção dos resultados
			connection.setAutoCommit(false);
			
			insertFinalResult();
			insertUnitsResult();
			
			//Commita os resultados se tudo ocorreu bem
			connection.commit();
		} catch (SQLException e) {
			try {
				//Se houve erro durante a inserção dos resultados, realiza rollback
				connection.rollback();
				throw new DatabaseInteractionException("Unable to insert the results into the database: " +
						e.getMessage());
			} catch (SQLException e1) {}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {}
		}
	}

	private void insertUnitsAssertions(UnitResult unitResult) throws SQLException {
		String sql = String.format("INSERT INTO %1$s VALUES (?,?,?,?,?)", unitAssertionsTablename);
		PreparedStatement statement = connection.prepareStatement(sql);
		
		//Recupera do banco o próximo número da Assertion a ser inserida
		int assertionCode = getNextUnitAssertionsCode();
	
		for (AssertionResult assertion : unitResult.getAssertions()) {
			statement.setInt(1, assertionCode);
			statement.setInt(2, currentUnitResultsCode); //Código da UnitResult que chamou este método (RUNTIME)
			statement.setString(3, assertion.getAssertionType());
			statement.setString(4, assertion.getMessage());
			statement.setString(5, assertion.isPassed() ? "Y" : "N");
			
			statement.addBatch();
			
			//Como a execução será realizada em BATCH, 
			//a cada nova inserção adicionada incrementa-se o próximo código em memória
			assertionCode++;
		}
		statement.executeBatch();
		statement.close();
	}

	private int getNextUnitAssertionsCode() throws SQLException {
		String sql = String.
				format("SELECT MAX(ID_UNIT_ASSERTION) + 1 as NEXT_CODE FROM %1$s", 
					   unitAssertionsTablename);
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		int nextCode = resultSet.getInt("NEXT_CODE");
		resultSet.close();
		statement.close();
		return nextCode;
	}

	private void insertUnitsResult() throws SQLException {
		String sql = String.format("INSERT INTO %1$s VALUES (?,?,?,?,?,?,?,?,?,?)", unitResultsTablename);
		PreparedStatement statement = connection.prepareStatement(sql);

		//Para cada UnitResult gerado, insere o mesmo no banco de dados
		for (UnitResult unitResult : core.getFinalResults().getUnitResults()) {
			//Recupera do banco o próximo número a unitResult a ser inserido
			currentUnitResultsCode = getNextUnitResultsCode();
			statement.setInt(1, currentUnitResultsCode);
			
			//FK
			statement.setInt(2, currentUnitFinalResultsCode);
			
			statement.setString(3, unitResult.getTestCase().getTestClass().getName());
			statement.setString(4, unitResult.getUnitMethod().getName());
			statement.setString(5, unitResult.isPassed() ? "Y" : "N");
			statement.setShort(6, (short) unitResult.getNumberOfAssertions());
			statement.setShort(7, (short) unitResult.getNumberOfPassedAssertions());
			statement.setShort(8, (short) unitResult.getNumberOfFailedAssertions());
			statement.setString(9, (unitResult.getExpectedException() != null) ? unitResult.getExpectedException().getName() : "");
			statement.setString(10, (unitResult.getExceptionOccurred() != null) ? unitResult.getExceptionOccurred().getName() : "");
			
			//Insere os resultados do UNIT_RESULT no banco
			statement.execute();
			
			//Insere as asserções da unitResult corrente
			insertUnitsAssertions(unitResult);
		}
		statement.close();
	}

	private int getNextUnitResultsCode() throws SQLException {
		String sql = String.
				format("SELECT MAX(ID_UNIT_RESULT) + 1 as NEXT_CODE FROM %1$s", 
					   unitResultsTablename);
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		int nextCode = resultSet.getInt("NEXT_CODE");
		resultSet.close();
		return nextCode;
	}

	private void insertFinalResult() throws SQLException {
		String sql = String.format("INSERT INTO %1$s VALUES (?,?,?,?,?,?,?,?,?)", finalResultsTablename);
		PreparedStatement statement = connection.prepareStatement(sql);
		
		currentUnitFinalResultsCode = getNextFinalResultsCode(); 
		statement.setInt(1, currentUnitFinalResultsCode);
		
		statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
		statement.setShort(3, (short) core.getFinalResults().getTotalNumberOfAssertions());
		statement.setShort(4, (short) core.getFinalResults().getTotalNumberOfPassedAssertions());
		statement.setShort(5, (short) core.getFinalResults().getTotalNumberOfFailedAssertions());
		statement.setShort(6, (short) core.getFinalResults().getTotalNumberOfUnits());
		statement.setShort(7, (short) core.getFinalResults().getTotalNumberOfPassedUnits());
		statement.setShort(8, (short) core.getFinalResults().getTotalNumberOfFailedUnits());
		statement.setString(9, projectName);
		
		statement.execute();
		statement.close();
	}

	private int getNextFinalResultsCode() throws SQLException {
		String sql = String.
				format("SELECT MAX(ID_FINAL_RESULT) + 1 as NEXT_CODE FROM %1$s", 
					   finalResultsTablename);
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		int nextCode = resultSet.getInt("NEXT_CODE");
		resultSet.close();
		statement.close();
		return nextCode;
	}

	private void createTablesIfNotExists() {
		if (connection == null) {
			throw new DatabaseInteractionException("The connection made available to SimpleUnit is null.");
		}
		
		try {
			DatabaseMetaData metadata = connection.getMetaData();
			ResultSet simpleUnitTables = metadata.getTables(null, null, "simple_unit_%", new String[]{"TABLE"});
			
			//Recupera tabelas SimpleUnit existentes, devem ser 3
			int totalCreatedSimpleUnitTables = 0;
			while (simpleUnitTables.next()) totalCreatedSimpleUnitTables++;
			
			//Se alguma das tabelas estiver faltando, lança exceção, pois está inconsistente
			if (totalCreatedSimpleUnitTables > 0 && totalCreatedSimpleUnitTables < 3)
				throw new DatabaseInconsistenceException("Currently are not available all the tables that " + 
								"SimpleUnit needs to work. Drop all tables SIMPLE_UNIT_* from database. "+
								"So SimpleUnit will rebuild the base");
			
			//Se não existir nenhuma, cria todas
			if (totalCreatedSimpleUnitTables == 0) {
				PreparedStatement statement = null;
				//Recupera cada DDL do arquivo em databaseCommands
				String[] databaseCommands = getSimpleUnitTablesDDLCommands();
				
				//Executa cada DDL no banco de dados
				for (int command = 0; command < databaseCommands.length; command++) {
					statement = connection.prepareStatement(databaseCommands[command]);
					statement.execute();
				}
				//Fecha o statement
				statement.close();
			}
		} catch (SQLException e) {
			throw new DatabaseInteractionException("SimpleUnit could not retrieve the database metadata: " + 
												   e.getMessage());
		} catch (IOException e) {
			throw new ExecutionException("SimpleUnit could not read the script file with the definition of its " +
										 "database: " + e.getMessage());
		}
	}

	private String[] getSimpleUnitTablesDDLCommands() throws IOException {
		InputStream databaseScriptStream = Thread.currentThread().getContextClassLoader().
				getResourceAsStream("simpleunit_database.sql");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(databaseScriptStream));
		String line;
		StringBuilder script = new StringBuilder();
		
		while ((line = reader.readLine()) != null) {
			//Insere cada linha do arquivo sql na variável de resultado script
			script.append(String.format("%1$s%n", line));
		}
		
		reader.close();
		databaseScriptStream.close();
		
		String [] databaseCommands = script.toString().split(";");
		String [] result = new String[databaseCommands.length - 1];
		for (int command = 0; command < databaseCommands.length - 1; command++) 
			result[command] = databaseCommands[command];
		
		return result;
	}
}