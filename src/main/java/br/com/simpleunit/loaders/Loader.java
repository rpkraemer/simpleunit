package br.com.simpleunit.loaders;

import java.util.List;

public interface Loader {

	public abstract void load();
	public abstract List<Class<?>> getLoadedClasses();
	
}
