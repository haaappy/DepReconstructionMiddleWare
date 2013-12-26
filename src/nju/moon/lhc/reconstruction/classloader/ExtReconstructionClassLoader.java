package nju.moon.lhc.reconstruction.classloader;

import java.util.ArrayList;

public class ExtReconstructionClassLoader extends ReconstructionClassLoader {
	public ExtReconstructionClassLoader(ArrayList<ClassLoader> parents, String classLoaderName){
		super(parents, classLoaderName);
	}
	
	public ExtReconstructionClassLoader(ClassLoader parent, String classLoaderName){
		super(parent, classLoaderName);
	}
	
	public ExtReconstructionClassLoader(String classLoaderName){
		super(classLoaderName);
	}
	
	public ExtReconstructionClassLoader(ExtReconstructionClassLoader cl){
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
}
