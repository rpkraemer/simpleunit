package br.com.simpleunit.database;

import java.sql.Connection;

public interface ConnectionProvider {

	public abstract Connection getConnection();
	public abstract String yourProjectName();
	
}
