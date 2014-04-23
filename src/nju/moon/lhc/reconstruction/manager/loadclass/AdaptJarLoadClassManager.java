package nju.moon.lhc.reconstruction.manager.loadclass;

import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import nju.moon.lhc.reconstruction.classloader.AdaptDepClassLoader;
import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.manager.dependency.DeploymentNode;

public class AdaptJarLoadClassManager extends AdaptLoadClassManager {

	@Override
	public void loadAllClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> loadClassByDeployment(String deploymentName,
			String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadClassByDeploymentNameSet(HashSet<String> nameSet) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void prepareQueue(HashSet<File> deploymentSet) {
		for (File file: deploymentSet){
			try{
				ZipFile zf = new ZipFile(file.getPath());
				DeploymentNode node = new DeploymentNode(file.getName());
				node.setClassLoader(new AdaptDepClassLoader(MiddleWareConfig.getInstance().getCurMiddleWareHome(), file.getName()));
				for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) { 
					ZipEntry ze = (ZipEntry)entries.nextElement();
					String zipEntryName = ze.getName();
					if (!ze.isDirectory() && zipEntryName.indexOf(".class")>0){			
						addALoadingClass(node, changeClassNameFormat(zipEntryName));
					}  
				} 
			}
			catch(Exception e){
				System.out.println("Exception in AdaptJarLoadClassManager in prepareQueue");
				e.printStackTrace();
			}
		}	
	}

	@Override
	protected String changeClassNameFormat(String className) {
		return className.replace(File.separatorChar, '.');
	}

}
