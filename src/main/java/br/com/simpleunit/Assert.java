package br.com.simpleunit;

import java.util.Arrays;

import br.com.simpleunit.objects.CurrentUnitMethodExecutionAssertion;

/**
 * This class provide a set of assertion methods for writing tests.
 * These methods can be used directly (Assert.isEquals(...)) or be referenced by
 * static imports.
 * 
 * @author Robson Paulo Kraemer (rpkraemer@gmail.com)
 * 
 */
public class Assert {
	
	private static CurrentUnitMethodExecutionAssertion currentAssertion;

	/**
	 * Asserts that passed byte arguments are equal
	 * @param expected
	 * @param actual
	 */
	public static void isEquals(byte expected, byte actual) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isEquals");
		
		if (expected == actual) {
			currentAssertion.setMessage(String.format("Expected *%1$s* and was *%2$s*", expected, actual));
			currentAssertion.setPassed(true);
		}
		else {
			currentAssertion.setMessage(String.format("Expected *%1$s* but was *%2$s*", expected, actual));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed char arguments are equal
	 * @param expected
	 * @param actual
	 */
	public static void isEquals(char expected, char actual) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isEquals");
		
		if (expected == actual) {
			currentAssertion.setMessage(String.format("Expected *%1$s* and was *%2$s*", expected, actual));
			currentAssertion.setPassed(true);
		}
		else {
			currentAssertion.setMessage(String.format("Expected *%1$s* but was *%2$s*", expected, actual));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed short arguments are equal
	 * @param expected
	 * @param actual
	 */
	public static void isEquals(short expected, short actual) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isEquals");
		
		if (expected == actual) {
			currentAssertion.setMessage(String.format("Expected *%1$s* and was *%2$s*", expected, actual));
			currentAssertion.setPassed(true);
		}
		else {
			currentAssertion.setMessage(String.format("Expected *%1$s* but was *%2$s*", expected, actual));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed int arguments are equal
	 * @param expected
	 * @param actual
	 */
	public static void isEquals(int expected, int actual) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isEquals");
		
		if (expected == actual) {
			currentAssertion.setMessage(String.format("Expected *%1$s* and was *%2$s*", expected, actual));
			currentAssertion.setPassed(true);
		}
		else {
			currentAssertion.setMessage(String.format("Expected *%1$s* but was *%2$s*", expected, actual));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed long arguments are equal
	 * @param expected
	 * @param actual
	 */
	public static void isEquals(long expected, long actual) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isEquals");
		
		if (expected == actual) {
			currentAssertion.setMessage(String.format("Expected *%1$s* and was *%2$s*", expected, actual));
			currentAssertion.setPassed(true);
		}
		else {
			currentAssertion.setMessage(String.format("Expected *%1$s* but was *%2$s*", expected, actual));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed float arguments are equal
	 * @param expected
	 * @param actual
	 */
	public static void isEquals(float expected, float actual) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isEquals");
		
		if (expected == actual) {
			currentAssertion.setMessage(String.format("Expected *%1$s* and was *%2$s*", expected, actual));
			currentAssertion.setPassed(true);
		}
		else {
			currentAssertion.setMessage(String.format("Expected *%1$s* but was *%2$s*", expected, actual));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed double arguments are equal
	 * @param expected
	 * @param actual
	 */
	public static void isEquals(double expected, double actual) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isEquals");
		
		if (expected == actual) {
			currentAssertion.setMessage(String.format("Expected *%1$s* and was *%2$s*", expected, actual));
			currentAssertion.setPassed(true);
		}
		else {
			currentAssertion.setMessage(String.format("Expected *%1$s* but was *%2$s*", expected, actual));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed Object arguments are equal
	 * @param expected
	 * @param actual
	 */
	public static void isEquals(Object expected, Object actual) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isEquals");
		
		if (expected.equals(actual)) {
			currentAssertion.setMessage(String.format("Expected *%1$s* and was *%2$s*", expected, actual));
			currentAssertion.setPassed(true);
		}
		else {
			currentAssertion.setMessage(String.format("Expected *%1$s* but was *%2$s*", expected, actual));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed byte arrays are equal
	 * @param expectedByteArray
	 * @param actualByteArray
	 */
	public static void isArrayEquals(byte[] expectedByteArray, byte[] actualByteArray) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isArrayEquals");
		
		if (Arrays.equals(expectedByteArray, actualByteArray)) {
			currentAssertion.setMessage(String.format("Byte arrays *%1$s* and *%2$s* are equals", 
													  expectedByteArray, actualByteArray));
			currentAssertion.setPassed(true);
		} else {
			currentAssertion.setMessage(String.format("Byte arrays *%1$s* and *%2$s* are not equals", 
													  expectedByteArray, actualByteArray));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed char arrays are equal
	 * @param expectedCharArray
	 * @param actualCharArray
	 */
	public static void isArrayEquals(char[] expectedCharArray, char[] actualCharArray) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isArrayEquals");
		
		if (Arrays.equals(expectedCharArray, actualCharArray)) {
			currentAssertion.setMessage(String.format("Char arrays *%1$s* and *%2$s* are equals", 
													  expectedCharArray, actualCharArray));
			currentAssertion.setPassed(true);
		} else {
			currentAssertion.setMessage(String.format("Char arrays *%1$s* and *%2$s* are not equals", 
													  expectedCharArray, actualCharArray));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed short arrays are equal
	 * @param expectedShortArray
	 * @param actualShortArray
	 */
	public static void isArrayEquals(short[] expectedShortArray, short[] actualShortArray) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isArrayEquals");
		
		if (Arrays.equals(expectedShortArray, actualShortArray)) {
			currentAssertion.setMessage(String.format("Short arrays *%1$s* and *%2$s* are equals", 
													  expectedShortArray, actualShortArray));
			currentAssertion.setPassed(true);
		} else {
			currentAssertion.setMessage(String.format("Short arrays *%1$s* and *%2$s* are not equals", 
													  expectedShortArray, actualShortArray));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed int arrays are equal
	 * @param expectedIntArray
	 * @param actualIntArray
	 */
	public static void isArrayEquals(int[] expectedIntArray, int[] actualIntArray) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isArrayEquals");
		
		if (Arrays.equals(expectedIntArray, actualIntArray)) {
			currentAssertion.setMessage(String.format("Int arrays *%1$s* and *%2$s* are equals", 
													  expectedIntArray, actualIntArray));
			currentAssertion.setPassed(true);
		} else {
			currentAssertion.setMessage(String.format("Int arrays *%1$s* and *%2$s* are not equals", 
													  expectedIntArray, actualIntArray));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed long arrays are equal
	 * @param expectedLongArray
	 * @param actualLongArray
	 */
	public static void isArrayEquals(long[] expectedLongArray, long[] actualLongArray) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isArrayEquals");
		
		if (Arrays.equals(expectedLongArray, actualLongArray)) {
			currentAssertion.setMessage(String.format("Long arrays *%1$s* and *%2$s* are equals", 
													  expectedLongArray, actualLongArray));
			currentAssertion.setPassed(true);
		} else {
			currentAssertion.setMessage(String.format("Long arrays *%1$s* and *%2$s* are not equals", 
													  expectedLongArray, actualLongArray));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed float arrays are equal
	 * @param expectedFloatArray
	 * @param actualFloatArray
	 */
	public static void isArrayEquals(float[] expectedFloatArray, float[] actualFloatArray) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isArrayEquals");
		
		if (Arrays.equals(expectedFloatArray, actualFloatArray)) {
			currentAssertion.setMessage(String.format("Float arrays *%1$s* and *%2$s* are equals", 
													  expectedFloatArray, actualFloatArray));
			currentAssertion.setPassed(true);
		} else {
			currentAssertion.setMessage(String.format("Float arrays *%1$s* and *%2$s* are not equals", 
													  expectedFloatArray, actualFloatArray));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed double arrays are equal
	 * @param expectedDoubleArray
	 * @param actualDoubleArray
	 */
	public static void isArrayEquals(double[] expectedDoubleArray, double[] actualDoubleArray) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isArrayEquals");
		
		if (Arrays.equals(expectedDoubleArray, actualDoubleArray)) {
			currentAssertion.setMessage(String.format("Double arrays *%1$s* and *%2$s* are equals", 
													  expectedDoubleArray, actualDoubleArray));
			currentAssertion.setPassed(true);
		} else {
			currentAssertion.setMessage(String.format("Double arrays *%1$s* and *%2$s* are not equals", 
													  expectedDoubleArray, actualDoubleArray));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed Object arrays are equal
	 * @param expectedObjectArray
	 * @param actualObjectArray
	 */
	public static void isArrayEquals(Object[] expectedObjectArray, Object[] actualObjectArray) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isArrayEquals");
		
		if (Arrays.equals(expectedObjectArray, actualObjectArray)) {
			currentAssertion.setMessage(String.format("Object arrays *%1$s* and *%2$s* are equals", 
													  expectedObjectArray, actualObjectArray));
			currentAssertion.setPassed(true);
		} else {
			currentAssertion.setMessage(String.format("Object arrays *%1$s* and *%2$s* are not equals", 
													  expectedObjectArray, actualObjectArray));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed condition is true
	 * @param condition
	 */
	public static void isTrue(boolean condition) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isTrue");
		
		if (condition) {
			currentAssertion.setMessage("Expected *true* and was *true*");
			currentAssertion.setPassed(true);
		} else {
			currentAssertion.setMessage("Expected *true* but was *false*");
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed condition is false
	 * @param condition
	 */
	public static void isFalse(boolean condition) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isFalse");
		
		if (condition == false) {
			currentAssertion.setMessage("Expected *false* and was *false*");
			currentAssertion.setPassed(true);
		} else {
			currentAssertion.setMessage("Expected *false* but was *true*");
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed object is null
	 * @param object
	 */
	public static void isNull(Object object) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isNull");
		
		if (object == null) {
			currentAssertion.setMessage(String.format("Expected null and was null: %1$s", object));
			currentAssertion.setPassed(true);
		}
		else {
			currentAssertion.setMessage(String.format("Expected null but was not null: %1$s", object));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed object is not null
	 * @param object
	 */
	public static void isNotNull(Object object) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isNotNull");
		
		if (object != null) {
			currentAssertion.setMessage(String.format("Expected not null and was not null: %1$s", object));
			currentAssertion.setPassed(true);
		}
		else {
			currentAssertion.setMessage(String.format("Expected not null but was null: %1$s", object));
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed arguments points to same object
	 * @param expected
	 * @param actual
	 */
	public static void isSame(Object expected, Object actual) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isSame");
		
		if (expected == actual || expected.equals(actual)) {
			currentAssertion.setMessage("Variables refer to same object");
			currentAssertion.setPassed(true);
		} else {
			currentAssertion.setMessage("Variables not refer to same object");
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	/**
	 * Asserts that passed arguments not points to same object
	 * @param expected
	 * @param actual
	 */
	public static void isNotSame(Object expected, Object actual) {
		currentAssertion = new CurrentUnitMethodExecutionAssertion();
		currentAssertion.setAssertionType("isNotSame");
		
		if (expected != actual || !expected.equals(actual)) {
			currentAssertion.setMessage("Variables not refer to same object");
			currentAssertion.setPassed(true);
		} else {
			currentAssertion.setMessage("Variables refer to same object");
			currentAssertion.setPassed(false);
		}
		addAssertionToCurrentUnitMethod(currentAssertion);
	}
	
	private static void addAssertionToCurrentUnitMethod(CurrentUnitMethodExecutionAssertion assertion) {
		Verifier.currentUnitMethodExecution.addAssertion(assertion);
	}
}