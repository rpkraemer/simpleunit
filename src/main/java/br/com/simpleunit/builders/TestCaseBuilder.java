package br.com.simpleunit.builders;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.simpleunit.annotations.BeforeUnit;
import br.com.simpleunit.annotations.Unit;
import br.com.simpleunit.objects.TestCase;

public class TestCaseBuilder implements Builder<TestCase> {

	private List<Class<?>> classesLoadedByClassLoader;
	private List<TestCase> buildedTestCases;
	
	public TestCaseBuilder(List<Class<?>> classesLoadedByClassLoader) {
		this.classesLoadedByClassLoader = classesLoadedByClassLoader;
		this.buildedTestCases = new ArrayList<TestCase>();
	}
	
	public void build() {
		for (int i = 0; i < classesLoadedByClassLoader.size(); i++) {
			Class <?> clazz = classesLoadedByClassLoader.get(i);
			List<Method> testCaseUnitMethods = getUnitMethodsFor(clazz);
			List<Method> testCaseBeforeUnitMethods = getBeforeUnitMethodsFor(clazz);
			if (!testCaseUnitMethods.isEmpty())
				buildedTestCases.add(new TestCase(clazz, testCaseUnitMethods, testCaseBeforeUnitMethods));
		}
	}

	private List<Method> getUnitMethodsFor(Class<?> clazz) {
		List<Method> unitMethods = new ArrayList<Method>();
		
		for (Method method : clazz.getMethods())
			if (method.isAnnotationPresent(Unit.class))
				unitMethods.add(method);
		
		return unitMethods;
	}
	
	private List<Method> getBeforeUnitMethodsFor(Class<?> clazz) {
		List<Method> beforeUnitMethods = new ArrayList<Method>();
		
		for (Method method : clazz.getMethods())
			if (method.isAnnotationPresent(BeforeUnit.class))
				beforeUnitMethods.add(method);
		
		return beforeUnitMethods;
	}

	public List<TestCase> getBuildedClasses() {
		return buildedTestCases;
	}
}