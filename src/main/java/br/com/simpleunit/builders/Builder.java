package br.com.simpleunit.builders;

import java.util.List;

public interface Builder<T> {

	public abstract void build();
	public abstract List<T> getBuildedClasses();
	
}
