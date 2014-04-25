package nju.moon.lhc.reconstruction.classloader;

import java.util.ArrayList;
import java.util.Date;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.main.MiddleWareMain;

public class AdaptDepClassLoader extends AbstractClassLoader {
	
	protected ArrayList<ClassLoader> depClassLoaders;
	protected ArrayList<ClassLoader> depInverseClassLoaders;
	
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
	
	
	public void removeDepByName(String name){
		ArrayList<ClassLoader> delList = new ArrayList<ClassLoader>();
		for (ClassLoader cl: depClassLoaders){
			if ((cl instanceof AdaptDepClassLoader) && 
					((AdaptDepClassLoader)cl).getClassLoaderName().equals(name)){
				delList.add(cl);
			}
		}
		depClassLoaders.removeAll(delList);
	}
	
	public void addDepByClassLoader(ClassLoader cl){
		depClassLoaders.add(cl);
	}
	
	public void removeDepInverseByName(String name){
		ArrayList<ClassLoader> delList = new ArrayList<ClassLoader>();
		for (ClassLoader cl: depInverseClassLoaders){
			if ((cl instanceof AdaptDepClassLoader) && 
					((AdaptDepClassLoader)cl).getClassLoaderName().equals(name)){
				delList.add(cl);
			}
		}
		depInverseClassLoaders.removeAll(delList);
	}
	
	public void addDepInverseByClassLoader(ClassLoader cl){
		depInverseClassLoaders.add(cl);
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException{
		Class<?> c = findLoadedClass(name);
		if (c == null){
			try{
				if (name.startsWith("java.")){
					c = ClassLoader.getSystemClassLoader().loadClass(name);
					if (c != null){
						return c;
					}
				}
				else{
					// loadby itself
					c = findClass(name);
					if (c == null){
						for (ClassLoader depClassLoader: depClassLoaders){
							try{
								c = depClassLoader.loadClass(name);
								if (c != null){
									return c;
								}
							}
							catch(ClassNotFoundException e){   // if one depClassLoader cannot found , continue
								continue;
							}		
						}
						// TODO to finish depManager
						/*
						ArrayList<ClassLoader> repositoryList = MiddleWareConfig.getInstance().getDepManager().getAllRepositoryClassLoaders();
						for (ClassLoader repClassLoader: repositoryList){
							try{
								c = repClassLoader.loadClass(name);
								if (c != null){
									return c;
								}
							}
							catch(ClassNotFoundException e){   // if one repClassLoader cannot found , continue
								continue;
							}		
						}*/
						c = this.getParent().loadClass(name);   // no class loader can load. load class with AppClassLoader				
					}					
				}			
			}
			catch (ClassNotFoundException e){
				c = ClassLoader.getSystemClassLoader().loadClass(name);   //no classloader can load. load class with AppClassLoader
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
	
}
