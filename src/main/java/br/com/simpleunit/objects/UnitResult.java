package br.com.simpleunit.objects;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class UnitResult {

	private TestCase testCase;
	private Method unitMethod;
	private boolean passed;
	private List<AssertionResult> assertions;
	private int numberOfAssertions;
	private int numberOfPassedAssertions;
	private int numberOfFailedAssertions;
	private Class<?> expectedException;
	private Class<?> exceptionOccurred;
	
	public UnitResult() { 
		this.assertions = new ArrayList<AssertionResult>();
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}
	
	public Method getUnitMethod() {
		return unitMethod;
	}

	public void setUnitMethod(Method unitMethod) {
		this.unitMethod = unitMethod;
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public int getNumberOfAssertions() {
		return numberOfAssertions;
	}

	public void setNumberOfAssertions(int numberOfAssertions) {
		this.numberOfAssertions = numberOfAssertions;
	}

	public int getNumberOfPassedAssertions() {
		return numberOfPassedAssertions;
	}

	public void setNumberOfPassedAssertions(int numberOfPassedAssertions) {
		this.numberOfPassedAssertions = numberOfPassedAssertions;
	}

	public int getNumberOfFailedAssertions() {
		return numberOfFailedAssertions;
	}

	public void setNumberOfFailedAssertions(int numberOfFailedAssertions) {
		this.numberOfFailedAssertions = numberOfFailedAssertions;
	}

	public Class<?> getExpectedException() {
		return expectedException;
	}

	public void setExpectedException(Class<?> expectedException) {
		this.expectedException = expectedException;
	}

	public Class<?> getExceptionOccurred() {
		return exceptionOccurred;
	}

	public void setExceptionOccurred(Class<?> exceptionOccurred) {
		this.exceptionOccurred = exceptionOccurred;
	}

	public List<AssertionResult> getAssertions() {
		return assertions;
	}

	public void addAssertion(AssertionResult assertion) {
		this.assertions.add(assertion);
	}
}
