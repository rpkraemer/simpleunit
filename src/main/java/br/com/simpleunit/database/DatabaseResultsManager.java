package br.com.simpleunit.database;

import br.com.simpleunit.CoreWrapper;
import br.com.simpleunit.exceptions.InvalidPropertiesFile;
import br.com.simpleunit.exceptions.LoaderException;
import br.com.simpleunit.util.SimpleUnitPropertiesReader;

public class DatabaseResultsManager {

	private SimpleUnitPropertiesReader fwPropsReader;
	private DatabaseResultsRecorder recorder;
	private CoreWrapper core;
	
	public DatabaseResultsManager(CoreWrapper core) {
		this.core = core;
		fwPropsReader = new SimpleUnitPropertiesReader();
		fwPropsReader.loadPropertiesFile();
	}
	
	public boolean isSetConfigurationToRecordInDatabase() {
		if (fwPropsReader.loadedSucessfully()) {
			try {
				//Tenta ler a propriedade no arquivo, se não existir ocorrerá uma exceção
				//O que significa que o usuário não definiu gravação de resultados em banco de dados
				fwPropsReader.getProperty("database_connection_provider");
			} catch (InvalidPropertiesFile e) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
	
	public void insertResultsOnDatabase() {
		ConnectionProvider connectionProvider = getConnectionProviderInformedInPropsFile();
		recorder = new DatabaseResultsRecorder(connectionProvider, core);
		recorder.insertResultsOnDatabase();
	}

	private ConnectionProvider getConnectionProviderInformedInPropsFile() {
		if (fwPropsReader.loadedSucessfully()) {
			String connectionProviderClassname = fwPropsReader.getProperty("database_connection_provider");
			
			//Tenta registrar a classe e retorná-la
			try {
				Class<?> connectionProviderClass = Class.forName(connectionProviderClassname);
				Object instance = connectionProviderClass.newInstance();
				if (instance instanceof ConnectionProvider)
					return (ConnectionProvider) instance;
				else
					throw new LoaderException("The connection class informed in simpleunit.properties " + 
										  "does not implement ConnectionProvider interface. Adjust and then try again.");
			} catch (Exception e) {
				throw new LoaderException("Unable to register the class ConnectionProvider informed in " +
										  "simpleunit.properties file: " + e.getMessage());
			}
		}
		throw new InvalidPropertiesFile("Could not load the properties file of the framework.\n" +
				"Check if it is created in the project classpath");
	}
}