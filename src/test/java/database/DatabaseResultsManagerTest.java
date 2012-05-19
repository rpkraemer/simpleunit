package database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import br.com.simpleunit.CoreWrapper;
import br.com.simpleunit.database.DatabaseResultsManager;
import br.com.simpleunit.exceptions.InvalidPropertiesFile;

public class DatabaseResultsManagerTest {

	private DatabaseResultsManager drm;
	
	@Before
	public void setUp() {
		drm = new DatabaseResultsManager(new CoreWrapper(null));
	}
	
	@Test
	public void shouldReturnFalseWhenConfigurationIsNotSetOnPropertiesFile() {
		Assert.assertFalse(drm.isSetConfigurationToRecordInDatabase());
	}
	
	@Test(expected = InvalidPropertiesFile.class)
	public void shouldRiseExceptionWhenTryToRecordAtDatabaseAndConfigurationIsNotSetOnPropertiesFile() {
		drm.insertResultsOnDatabase();
	}
}
