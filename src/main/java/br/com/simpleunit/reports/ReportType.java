package br.com.simpleunit.reports;

import br.com.simpleunit.CoreWrapper;

public enum ReportType {

	CONSOLE {
		@Override
		public Report getReport(CoreWrapper core) {
			return new ConsoleReport(core);
		}
	},
	TEXT {
		@Override
		public Report getReport(CoreWrapper core) {
			return new TextReport(core);
		}
	},
	HTML {
		@Override
		public Report getReport(CoreWrapper core) {
			return new HTMLReport(core);
		}
	};
	
	public abstract Report getReport(CoreWrapper core); 
}
