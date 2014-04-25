package nju.moon.lhc.reconstruction.classloader;

import java.util.ArrayList;
import java.util.Date;


import nju.moon.lhc.reconstruction.main.MiddleWareMain;

public class ExtReconstructionClassLoader extends ReconstructionClassLoader {
	public ExtReconstructionClassLoader(String rootDir, ArrayList<ClassLoader> parents, String classLoaderName){
		super(rootDir, parents, classLoaderName);
	}
	
	public ExtReconstructionClassLoader(String rootDir, ClassLoader parent, String classLoaderName){
		super(rootDir, parent, classLoaderName);
	}
	
	public ExtReconstructionClassLoader(String rootDir, String classLoaderName){
		super(rootDir, classLoaderName);
	}
	
	public ExtReconstructionClassLoader(ReconstructionClassLoader cl){
		super(cl);
	}
	
	public ExtReconstructionClassLoader(){
		super();
	
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException{
		Class<?> c = findLoadedClass(name);
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
			if (c == null){
				c = findClass(name);  // parent classloader cannot load class
			}
		}	
		return c;
	}
	
	@Override
	public Class<?> findClass(String name){  // XX.XX.XXXX  format
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
