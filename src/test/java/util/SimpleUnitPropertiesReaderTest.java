package util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.simpleunit.exceptions.InvalidPropertiesFile;
import br.com.simpleunit.util.SimpleUnitPropertiesReader;

public class SimpleUnitPropertiesReaderTest {

	private SimpleUnitPropertiesReader fwPropsReader;
	
	@Before
	public void setUp() {
		fwPropsReader = new SimpleUnitPropertiesReader();
	}
	
	@Test
	public void shouldReturnTrueWhenPropertiesFilenameIsAtClasspath() {
		fwPropsReader.loadPropertiesFile();
		Assert.assertTrue(fwPropsReader.loadedSucessfully());
	}
	
	@Test(expected = InvalidPropertiesFile.class)
	public void shouldFailWhenTryLoadAnInexistentProperty() {
		fwPropsReader.loadPropertiesFile();
		fwPropsReader.getProperty("invalid-property");
	}
	
	@Test
	public void shouldLoadAValidProperty() {
		fwPropsReader.loadPropertiesFile();
		String htmlDir = fwPropsReader.getProperty("html_dir");
		Assert.assertNotNull(htmlDir);
	}
}