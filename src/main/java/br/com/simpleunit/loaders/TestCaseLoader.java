package br.com.simpleunit.loaders;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import br.com.simpleunit.annotations.Test;
import br.com.simpleunit.exceptions.LoaderException;

public class TestCaseLoader implements Loader {
	
	private String [] packages;
	private List<Class<?>> loadedClasses;
	private ClassLoader classLoader;
	
	public TestCaseLoader(String [] packages) {
		this.packages = packages;
		this.classLoader = Thread.currentThread().getContextClassLoader();
		this.loadedClasses = new ArrayList<Class<?>>();
	}
	
	private void registerTestCasesClasses(String pkg, File[] classes) {
		for (int i = 0; i < classes.length; i++) {
			Class<?> clazz;
			try {
				clazz = Class.forName(resolveQualifiedClassName(pkg, classes[i].getName()), false, classLoader);
				if (clazz.isAnnotationPresent(Test.class)) {
					loadedClasses.add(clazz);
				}
			} catch (ClassNotFoundException e) {
				throw new LoaderException("Class to be registered was not found: " + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * @param packageName
	 * @return todos os arquivos .class para um pacote
	 * @throws URISyntaxException 
	 * @throws IOException
	 */
	private File[] getPackageClasses(String packageName) throws URISyntaxException {
		List<File> list = new ArrayList<File>();
		packageName = packageName.replace('.', '/');
		Enumeration<URL> urls = null;
		
		try {
			urls = this.classLoader.getResources(packageName);
		} catch (IOException e) {
			throw new LoaderException("Resources could not be retrieved: " + e.getMessage());
		}
			
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			File dir = new File(url.toURI());
			for (File f : dir.listFiles(new FileFilter() {
												public boolean accept(File file) {
													return file.getName().endsWith(".class");
												}
											}
										)) {
				list.add(f);
			}
		}
		return list.toArray(new File[] {});
	}
	
	/**
	 * 
	 * @param pkg - Nome do pacote informado para ser escaneado
	 * @param className - retorno do método urlResource.getFile()
	 * @return o qualified name para registrar a classe na aplicação
	 */
	private String resolveQualifiedClassName(String pkg, String className) {
		return pkg + "." + className.substring(0, className.length() - 6);
	}
	
	public List<Class<?>> getLoadedClasses() {
		return loadedClasses;
	}	

	public void load() {
		for (int pkg = 0; pkg < packages.length; pkg++) {
			String currentPackage = packages[pkg];
			File[] classes;
			try {
				classes = getPackageClasses(currentPackage);
				registerTestCasesClasses(currentPackage, classes);
			} catch (URISyntaxException e) {
				throw new LoaderException("Error while reading package classes: " + e.getMessage());
			}
		}
	}
}