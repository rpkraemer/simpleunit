package builder;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import br.com.simpleunit.builders.Builder;
import br.com.simpleunit.builders.TestCaseBuilder;
import br.com.simpleunit.objects.TestCase;

public class TestCaseBuilderTest {

	private List<Class<?>> testCaseClassesLoadedByTestCaseLoader;
	private Builder<TestCase> testCaseBuilder;
	
	@Before
	public void setUp() throws ClassNotFoundException {
		testCaseClassesLoadedByTestCaseLoader = new ArrayList<Class<?>>();
		testCaseClassesLoadedByTestCaseLoader.add(Class.forName("classes.Bar"));
		testCaseClassesLoadedByTestCaseLoader.add(Class.forName("classes.Foo"));
		
		testCaseBuilder = new TestCaseBuilder(testCaseClassesLoadedByTestCaseLoader);
	}
	
	@Test
	public void shouldGenerateTwoTestCasesWithLoadedClass() {
		testCaseBuilder.build();
		Assert.assertEquals(2, testCaseBuilder.getBuildedClasses().size());
	}
	
	@Test
	public void shouldRetrieveOneUnitMethod() {
		testCaseBuilder.build();
		TestCase testCase = testCaseBuilder.getBuildedClasses().get(1);
		Assert.assertEquals(1, testCase.getUnitMethods().size());
	}
}
