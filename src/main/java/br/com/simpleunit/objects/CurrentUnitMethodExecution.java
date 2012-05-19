package br.com.simpleunit.objects;

import java.util.ArrayList;
import java.util.List;

public class CurrentUnitMethodExecution {

	private TestCase testCase;
	private List<CurrentUnitMethodExecutionAssertion> assertions = new ArrayList<CurrentUnitMethodExecutionAssertion>();
	private int numberOfPassedAssertions;
	private int numberOfFailedAssertions;
	private Class<?> expectedException;
	private Class<?> exceptionOccurred;
	
	
	private void incrementPassedAssertions() {
		this.numberOfPassedAssertions += 1;
	}
	
	private void incrementFailedAssertions() {
		this.numberOfFailedAssertions += 1;
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public int getNumberOfPassedAssertions() {
		return numberOfPassedAssertions;
	}

	public int getNumberOfFailedAssertions() {
		return numberOfFailedAssertions;
	}
	
	public int totalNumberOfAssertions() {
		return numberOfFailedAssertions + numberOfPassedAssertions;
	}
	
	public boolean isPassed() {
		//Se há uma exceção esperada para o Unit o teste só irá passar se esta mesma exceção ocorreu
		//e as asserções passarem
		//Caso contrário basta apenas o número de asserções OK ser igual ao total (todas)
		if (expectedException != null) {
			return (expectedException == exceptionOccurred) && 
				   (numberOfPassedAssertions == totalNumberOfAssertions());
		} else {
			return numberOfPassedAssertions == totalNumberOfAssertions();
		}
	}

	public Class<?> getExceptionOccurred() {
		return exceptionOccurred;
	}

	public void setExceptionOccurred(Class<?> exceptionOccurred) {
		this.exceptionOccurred = exceptionOccurred;
	}

	public Class<?> getExpectedException() {
		return expectedException;
	}

	public void setExpectedException(Class<?> expectedException) {
		this.expectedException = expectedException;
	}
	
	public void addAssertion(CurrentUnitMethodExecutionAssertion assertion) {
		assertions.add(assertion);
		
		if (assertion.isPassed())
			incrementPassedAssertions();
		else
			incrementFailedAssertions();
	}
	
	public List<CurrentUnitMethodExecutionAssertion> getAssertions() {
		return assertions;
	}
}
