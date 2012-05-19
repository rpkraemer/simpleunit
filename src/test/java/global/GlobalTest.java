package global;

import br.com.simpleunit.Runner;
import br.com.simpleunit.reports.ReportType;

public class GlobalTest {

	public static void main(String[] args) {
		
		String [] packages = {"classes"};
		Runner.run(packages, ReportType.HTML);

	}
}