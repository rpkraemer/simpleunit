package loader;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.simpleunit.loaders.Loader;
import br.com.simpleunit.loaders.TestCaseLoader;

public class TestCaseLoaderTest {

	private Loader loader;
	private Loader invalidLoader;
	
	@Before
	public void setUp() {
		String [] packagesToScan = {"classes"};
		loader = new TestCaseLoader(packagesToScan);
		invalidLoader = new TestCaseLoader(new String[] {"non-existent-package"});
	}
	
	@Test
	public void shouldScanTwoClassesInPackage() throws IOException, ClassNotFoundException {
		loader.load();
		Assert.assertEquals(2, loader.getLoadedClasses().size());
	}
	
	@Test
	public void allLoadedClassesShouldContainTestAnnotation() throws IOException, ClassNotFoundException {
		loader.load();
		int totalClassesWithTestAnnotation = 0;
		
		for (Class<?> loadedClass : loader.getLoadedClasses()) {
			if (loadedClass.isAnnotationPresent(br.com.simpleunit.annotations.Test.class))
				totalClassesWithTestAnnotation++;
		}
		Assert.assertEquals(loader.getLoadedClasses().size(), totalClassesWithTestAnnotation);
	}
	
	@Test
	public void shouldReturnEmptyListWhenScanInexistentDirectory() throws IOException, ClassNotFoundException {
		invalidLoader.load();
		Assert.assertTrue(invalidLoader.getLoadedClasses().isEmpty());
	}
	
}
