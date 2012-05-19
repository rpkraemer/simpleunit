package br.com.simpleunit.objects;

public class CurrentUnitMethodExecutionAssertion {

	private String assertionType;
	private String message;
	private boolean passed;
	
	public String getAssertionType() {
		return assertionType;
	}
	public void setAssertionType(String assertionType) {
		this.assertionType = assertionType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isPassed() {
		return passed;
	}
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
}
