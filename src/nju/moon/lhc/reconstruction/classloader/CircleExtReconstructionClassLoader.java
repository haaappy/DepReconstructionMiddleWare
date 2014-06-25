package nju.moon.lhc.reconstruction.classloader;

import java.util.ArrayList;
import java.util.Date;

import nju.moon.lhc.reconstruction.main.MiddleWareMain;

public class CircleExtReconstructionClassLoader extends ExtReconstructionClassLoader {
	
	private long visitTime = 0;  //  to record the visit time
	
	public CircleExtReconstructionClassLoader(String rootDir, ArrayList<ClassLoader> parents, String classLoaderName){
		super(rootDir, parents, classLoaderName);
	}
	
	public CircleExtReconstructionClassLoader(String rootDir, ClassLoader parent, String classLoaderName){
		super(rootDir, parent, classLoaderName);
	}
	
	public CircleExtReconstructionClassLoader(String rootDir, String classLoaderName){
		super(rootDir, classLoaderName);
	}
	
	public CircleExtReconstructionClassLoader(ReconstructionClassLoader cl){
		super(cl);
	}
	
	public CircleExtReconstructionClassLoader(){
		super();
	
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException{
		Class<?> c = findLoadedClass(name);
		if (c == null){
			visitTime = System.nanoTime();
			for (ClassLoader parent: parents){
				try{
					if (parent instanceof CircleExtReconstructionClassLoader){
						c = ((CircleExtReconstructionClassLoader) parent).loadClassForParent(name, visitTime);
					}
					else{
						c = parent.loadClass(name);
					}
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
	
	public Class<?> loadClassForParent(String name, long firstLoadTime) throws ClassNotFoundException{
		Class<?> c = findLoadedClass(name);
		if (c == null){
			if (visitTime < firstLoadTime){
				visitTime = System.nanoTime();
				for (ClassLoader parent: parents){
					try{
						if (parent instanceof CircleExtReconstructionClassLoader){
							c = ((CircleExtReconstructionClassLoader) parent).loadClassForParent(name, firstLoadTime);
						}
						else{
							c = parent.loadClass(name);
						}
						if (c != null){
							return c;
						}
					}
					catch(ClassNotFoundException e){   // if one parent cannot found , continue
						continue;
					}		
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
