package nju.moon.lhc.reconstruction.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;

public abstract class AbstractClassLoader extends ClassLoader {

	protected String classLoaderName;
	protected String rootName;
	protected String rootDir = "/home/happy/JBOSS/OSGI-TEST-DEPLOYMENT/";
	public static final String DEFAULT_NAME = "A-JAR";
	
	
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
			//e.printStackTrace();   exception and return null
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
      			//e.printStackTrace();  // exception and return null
      		}
		}
		catch(Exception e){
			//e.printStackTrace();  // exception and return null
		}
			
		return null;
	}
	
	protected String classNameToPathJarWay(String className){    //   XX/XX/XXXX.class
		return  className.replace('.', File.separatorChar)+".class";
	} 
}
