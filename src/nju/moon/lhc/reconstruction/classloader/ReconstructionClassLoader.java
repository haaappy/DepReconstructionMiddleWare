package nju.moon.lhc.reconstruction.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.main.MiddleWareMain;

public class ReconstructionClassLoader extends AbstractClassLoader {

	protected ArrayList<ClassLoader> parents;

	
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
			if (MiddleWareMain.application != null){
				MiddleWareMain.application.setLoadedClass(name, new Date());			
			}
			return defineClass(name, classData, 0, classData.length);
		}
	}
	
	protected byte[] getClassData(String className){
		int way = MiddleWareConfig.getInstance().getCurDeploymentWay();
		if (way == MiddleWareConfig.DEPLOY_WAY_NORMAL){
			return getClassDataNormalWay(className);
		}
		else if (way == MiddleWareConfig.DEPLOY_WAY_JAR){
			return getClassDataJarWay(className);
		}
		else{
			return getClassDataNormalWay(className);
		}
		
	}
	
	protected byte[] getClassDataNormalWay(String className){
		String path = classNameToPathNormalWay(className);
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
			//e.printStackTrace();   exception and return null and no print info
		}
		return null;
	}
	
	protected String classNameToPathNormalWay(String className){
		return rootName + File.separator + className.replace('.', File.separatorChar)+".class";
	}
	
	
	protected byte[] getClassDataJarWay(String className){
		String path = classNameToPathJarWay(className);
		try{
			ZipFile zf = new ZipFile(rootDir + classLoaderName);
			ZipEntry ze = zf.getEntry(path);
					
			try{
				if (ze == null){
					return null;
				}				
      			InputStream ins = zf.getInputStream(ze);
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
      			// e.printStackTrace();  // exception and return null and no print info
      		}
		}
		catch(Exception e){ 
			// e.printStackTrace();   // exception and return null and no print info
		}
			
		return null;
	}
	
	protected String classNameToPathJarWay(String className){    //   XX/XX/XXXX.class
		return  className.replace('.', File.separatorChar)+".class";
	}

	
}
