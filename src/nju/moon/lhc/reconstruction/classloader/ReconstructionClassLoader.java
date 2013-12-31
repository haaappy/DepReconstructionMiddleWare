package nju.moon.lhc.reconstruction.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ReconstructionClassLoader extends ClassLoader {

	protected String classLoaderName;
	protected ArrayList<ClassLoader> parents;
	protected String rootName;
	protected String rootDir = "/home/happy/JBOSS/OSGI-TEST-DEPLOYMENT/";
	public static final String DEFAULT_NAME = "A-JAR";
	
	public ReconstructionClassLoader(String rootDir, ArrayList<ClassLoader> parents, String classLoaderName){
		this.rootDir = rootDir;
		this.parents = new ArrayList<ClassLoader>();
		this.parents.addAll(parents);
		this.setClassLoaderName(classLoaderName);
		this.rootName = rootDir + classLoaderName;
	}
	
	public ReconstructionClassLoader(String rootDir, ClassLoader parent, String classLoaderName){
		this.rootDir = rootDir;
		this.parents = new ArrayList<ClassLoader>();
		this.parents.add(parent);
		this.setClassLoaderName(classLoaderName);
		this.rootName = rootDir + classLoaderName;
	}
	
	public ReconstructionClassLoader(String rootDir, String classLoaderName){
		this.rootDir = rootDir;
		this.parents = new ArrayList<ClassLoader>();
		//this.parents.add(this.getParent());
		this.setClassLoaderName(classLoaderName);
		this.rootName = rootDir + classLoaderName;
	}
	
	public ReconstructionClassLoader(ReconstructionClassLoader cl){
		this.rootDir = cl.rootDir;
		this.parents = new ArrayList<ClassLoader>();
		this.parents.addAll(cl.parents);
		this.setClassLoaderName(cl.getClassLoaderName());
		this.rootName = this.rootDir + classLoaderName;
	}
	
	public ReconstructionClassLoader(){
		this.parents = new ArrayList<ClassLoader>();
		//this.parents.add(this.getParent());
		this.setClassLoaderName(DEFAULT_NAME);
		this.rootName = rootDir + DEFAULT_NAME;
	}
	
	public String getClassLoaderName() {
		return classLoaderName;
	}

	public void setClassLoaderName(String classLoaderName) {
		this.classLoaderName = classLoaderName;
	}
	
//	public ArrayList<ClassLoader> getMyParents(){
//		return parents;
//	}
	
	public void removeParentByName(String name){
		ArrayList<ClassLoader> delList = new ArrayList<ClassLoader>();
		for (ClassLoader cl: parents){
			if ((cl instanceof ReconstructionClassLoader) && 
					((ReconstructionClassLoader)cl).getClassLoaderName().equals(name)){
				delList.add(cl);
			}
		}
		parents.removeAll(delList);
	}
	
	public void addParentByClassLoader(ClassLoader cl){
		parents.add(cl);
	}
	
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException{
		Class<?> c = findLoadedClass(name);
		if (c == null){
			try{
				c = findClass(name);
				if (c == null){
					for (ClassLoader parent: parents){
						try{
							c = parent.loadClass(name);
							if (c != null){
								return c;
							}
						}
						catch(ClassNotFoundException e){   // if one parent cannot found , continue
							continue;
						}		
					}
					c = this.getParent().loadClass(name);   // no classloader can load. load class with AppClassLoader
				}
			}
			catch (ClassNotFoundException e){
				c = this.getParent().loadClass(name);   //no classloader can load. load class with AppClassLoader
			}
		}
		return c;
	}
	
	@Override
	public Class<?> findClass(String name){
		byte[] classData = getClassData(name);
		if (classData == null){
			return null;
		}
		else{
			return defineClass(name, classData, 0, classData.length);
		}
	}
	
	protected byte[] getClassData(String className){
		String path = classNameToPath(className);
		try{
			InputStream ins = new FileInputStream(path);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			int bytesNumRead = 0;
			while ((bytesNumRead = ins.read(buffer))!=-1){
				baos.write(buffer, 0, bytesNumRead);
			}
			ins.close();
			return baos.toByteArray();
		}catch(IOException e){
			//e.printStackTrace();   exception and return null
		}
		return null;
		
	}
	
	protected String classNameToPath(String className){
		return rootName + File.separator + className.replace('.', File.separatorChar)+".class";
	}

	
}
