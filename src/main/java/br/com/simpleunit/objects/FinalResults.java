package br.com.simpleunit.objects;

import java.util.List;

public class FinalResults {

	private final List<UnitResult> unitResults;
	private int totalNumberOfAssertions;
	private int totalNumberOfPassedAssertions;
	private int totalNumberOfFailedAssertions;
	
	private int totalNumberOfUnits;
	private int totalNumberOfPassedUnits;
	private int totalNumberOfFailedUnits;
	
	public FinalResults(List<UnitResult> unitResults) {
		this.unitResults = unitResults;
		
		calculateTotals();
	}

	private void calculateTotals() {
		for (UnitResult unitResult : unitResults) {
			totalNumberOfAssertions += unitResult.getNumberOfAssertions();
			totalNumberOfPassedAssertions += unitResult.getNumberOfPassedAssertions();
			totalNumberOfFailedAssertions += unitResult.getNumberOfFailedAssertions();
			totalNumberOfUnits++;
			if (unitResult.isPassed())
				totalNumberOfPassedUnits++;
			else
				totalNumberOfFailedUnits++;
		}
	}

	public int getTotalNumberOfPassedUnits() {
		return totalNumberOfPassedUnits;
	}

	public int getTotalNumberOfFailedUnits() {
		return totalNumberOfFailedUnits;
	}

	public List<UnitResult> getUnitResults() {
		return unitResults;
	}

	public int getTotalNumberOfAssertions() {
		return totalNumberOfAssertions;
	}

	public int getTotalNumberOfPassedAssertions() {
		return totalNumberOfPassedAssertions;
	}

	public int getTotalNumberOfFailedAssertions() {
		return totalNumberOfFailedAssertions;
	}

	public int getTotalNumberOfUnits() {
		return totalNumberOfUnits;
	}
	
}
