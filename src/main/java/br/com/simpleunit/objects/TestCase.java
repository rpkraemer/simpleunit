package br.com.simpleunit.objects;

import java.lang.reflect.Method;
import java.util.List;

public class TestCase {

	private final Class<?> testClass;
	private final List<Method> unitMethods;
	private final List<Method> beforeUnitMethods;
	
	public TestCase(Class<?> testClass, List<Method> unitMethods, List<Method> beforeUnitMethods) {
		this.testClass = testClass;
		this.unitMethods = unitMethods;
		this.beforeUnitMethods = beforeUnitMethods;
	}

	public Class<?> getTestClass() {
		return testClass;
	}

	public List<Method> getUnitMethods() {
		return unitMethods;
	}

	public List<Method> getBeforeUnitMethods() {
		return beforeUnitMethods;
	}
}
