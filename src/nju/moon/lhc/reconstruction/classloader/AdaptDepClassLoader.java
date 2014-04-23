package nju.moon.lhc.reconstruction.classloader;

import java.util.ArrayList;

public class AdaptDepClassLoader extends ClassLoader {
	
	protected String classLoaderName;
	protected ArrayList<ClassLoader> depClassLoaders;
	protected ArrayList<ClassLoader> depInverseClassLoaders;
	protected String rootName;
	protected String rootDir = "/home/happy/JBOSS/OSGI-TEST-DEPLOYMENT/";
	public static final String DEFAULT_NAME = "A-JAR";
	
	
	public AdaptDepClassLoader(String rootDir, String classLoaderName){
		this.rootDir = rootDir;
		this.depClassLoaders = new ArrayList<ClassLoader>();
		this.depInverseClassLoaders = new ArrayList<ClassLoader>();
		this.setClassLoaderName(classLoaderName);
		this.rootName = rootDir + classLoaderName;
	}
	
//	public AdaptDepClassLoader(ReconstructionClassLoader cl){
//		this.rootDir = cl.rootDir;
//		this.parents = new ArrayList<ClassLoader>();
//		this.parents.addAll(cl.parents);
//		this.setClassLoaderName(cl.getClassLoaderName());
//		this.rootName = this.rootDir + classLoaderName;
//	}
	
	public AdaptDepClassLoader(){
		this.depClassLoaders = new ArrayList<ClassLoader>();
		this.depInverseClassLoaders = new ArrayList<ClassLoader>();
		this.setClassLoaderName(DEFAULT_NAME);
		this.rootName = rootDir + DEFAULT_NAME;
	}
	
	public String getClassLoaderName() {
		return classLoaderName;
	}
	
	public void setClassLoaderName(String classLoaderName) {
		this.classLoaderName = classLoaderName;
	}
	
	
	
}
