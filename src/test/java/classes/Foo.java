package classes;

import br.com.simpleunit.Assert;
import br.com.simpleunit.annotations.BeforeUnit;
import br.com.simpleunit.annotations.Test;
import br.com.simpleunit.annotations.Unit;


@Test
public class Foo {

	private Object myObject;
	private Object otherObject;
	
	@BeforeUnit
	public void setUp() {
		myObject = "myObject";
	}
	
	@BeforeUnit
	public void setUp2() {
		otherObject = 10;
		otherObject = null;
	}
	
	@Unit
	public void testWithObjectInitializedByBeforeUnitMethod() {
		Assert.isNotNull(myObject);
		Assert.isNull(otherObject);
	}
}
