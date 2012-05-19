package br.com.simpleunit.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.com.simpleunit.exceptions.InvalidPropertiesFile;

public class SimpleUnitPropertiesReader {

	private Properties props;
	private String propsFilename = "simpleunit.properties";
	private boolean configuredSucessfully;
	
	public boolean loadedSucessfully() {
		return this.configuredSucessfully;
	}
	
	public void loadPropertiesFile() {
		try {
			this.props = new Properties();
			InputStream propertiesFileStream = Thread.currentThread().
												      getContextClassLoader().
												      getResourceAsStream(propsFilename);
			if (propertiesFileStream != null) {
				this.props.load(propertiesFileStream);
				this.configuredSucessfully = true;
			} else {
				this.configuredSucessfully = false;
			}
		} catch (IOException e) {
			this.configuredSucessfully = false;
		}
	}

	public String getProperty(String property) {
		String prop = props.getProperty(property);
		if (prop == null) {
			throw new InvalidPropertiesFile("Property " + property + 
					" is not defined in the properties file. Check and correct formatting of the file");
		}
		return prop;
	}
}
