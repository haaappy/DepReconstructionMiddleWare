package nju.moon.lhc.reconstruction.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
			return defineClass(name, classData, 0, classData.length);
		}
	}
	
	@Override
	protected byte[] getClassData(String className){
		String path = classNameToPath(className);
		try{
			ZipFile zf = new ZipFile(rootDir + classLoaderName + ".jar");
			ZipEntry ze = zf.getEntry(path);
					
			try{
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
      			e.printStackTrace();  // exception and return null
      		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
			
		return null;
	}
	
	@Override
	protected String classNameToPath(String className){    //   XX/XX/XXXX.class
		return  className.replace('.', File.separatorChar)+".class";
	}
}
