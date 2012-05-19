package classes;

import br.com.simpleunit.Assert;
import br.com.simpleunit.annotations.Test;
import br.com.simpleunit.annotations.Unit;


@Test
public class Bar {

	@Unit
	public void shouldSumOf15And30Be45() {
		int sum = 15 + 30;
		Assert.isEquals(46, sum);
	}
	
	@Unit
	public void shouldObjectBeNull() {
		Object obj = null;
		Assert.isNull(obj);
	}
	
	@Unit
	public void shouldObjectBeNotNull() {
		Object obj = new Object();
		Assert.isNotNull(obj);
	}
	
	@Unit
	public void shouldByteArraysOfStringsAreEquals() {
		String s1, s2;
		s1 = "teste";
		s2 = "teste";
		
		Assert.isArrayEquals(s1.getBytes(), s2.getBytes());
	}
	
	@Unit
	public void shouldTwoReferencesApointToSameObject() {
		String reference1 = "abc";
		String reference2 = reference1;
		String reference3 = reference2;
		
		Assert.isSame(reference1, reference3);
	}
	
	@Unit
	public void shouldNotTwoReferencesApointToSameObject() {
		String reference1 = "abc";
		String reference2 = "abc".concat("de");
		
		Assert.isNotSame(reference1, reference2);
	}
	
	@Unit
	public void shouldTwoStringsLengthBeTheSame() {
		String s1 = "O tamanho dessas duas strings DEVERÁ ser o mesmo";
		String s2 = "O tamanho dessas DUAS stringsDEVERÁ ser o mesmo ";
		
		Assert.isTrue(s1.length() == s2.length());
	}
	
	@Unit
	public void shouldBeTrue() {
		Assert.isTrue("123".equals("123"));
	}
	
	@Unit
	public void shouldTwoCharArraysLengthNotBeEquals() {
		char[] charArray1 = {'t','e','s','t','e'};
		char[] charArray2 = {'t','e','s','t','e','s'};
		
		Assert.isFalse(charArray1.length == charArray2.length);
		
	}
	
	@Unit
	public void shouldTwoCharArraysBeEquals() {
		char[] charArray1 = {'t','e','s','t','e'};
		char[] charArray2 = {'t','e','s','t','e'};
		
		Assert.isArrayEquals(charArray1, charArray2);
	}
	
	@Unit (shouldRise = NullPointerException.class)
	public void expectedNullPointerWhenAccessMethodOfNullableInstance() {
		Object instance = null;
		System.out.println(instance.getClass().getName());
	}
}
