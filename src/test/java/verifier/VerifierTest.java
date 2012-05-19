package verifier;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import br.com.simpleunit.Verifier;
import br.com.simpleunit.builders.Builder;
import br.com.simpleunit.builders.TestCaseBuilder;
import br.com.simpleunit.loaders.Loader;
import br.com.simpleunit.loaders.TestCaseLoader;
import br.com.simpleunit.objects.TestCase;
import br.com.simpleunit.objects.UnitResult;

public class VerifierTest {

	private Verifier verifier;
	private List<TestCase> testCases;

	@Before
	public void setUp() {
		// Monta List de TestCases e instancia verifier
		testCases = generateTestCases();
		verifier = new Verifier();
	}

	private List<TestCase> generateTestCases() {
		Loader loader = new TestCaseLoader(new String[] { "classes" });
		loader.load();
		Builder<TestCase> builder = new TestCaseBuilder(
				loader.getLoadedClasses());
		builder.build();
		return builder.getBuildedClasses();
	}

	@Test
	public void shouldReturnEmptyUnitResultsWhenArgsAreEmpty()
			throws IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException {

		verifier.verify(new ArrayList<TestCase>());
		List<UnitResult> unitResults = verifier.getUnitMethodsResults();
		Assert.assertEquals(0, unitResults.size());

	}

	@Test
	public void shouldReturnTwelveUnitResultsForGivenTestCases()
			throws IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		
		verifier.verify(testCases);
		Assert.assertEquals(12, verifier.getUnitMethodsResults().size());
	}

}
